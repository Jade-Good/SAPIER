package com.esfp.sapaier.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.esfp.sapaier.global.auth.repository.OAuth2AuthorizationRequestRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

	private final String[] SWAGGER_URI_LIST = {
		"/swagger-ui/**",
		"/api-docs/**"
	};

	private final String[] LOGIN_URI_LIST = {
		"/api/oauth2/authorization",
		"/api/login/oauth2/code/**"
	};

	private final OAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository;


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

		//기본설정 (httpBasic vs formLogin 기억안나면 다시 검색해보기)
		httpSecurity
			.securityMatcher("/**") // 이 설정은 /**아래에서 동작함 (여러개의 SecurityFilterChain을 생성할경우, @Order 사용해야함)
			.httpBasic(AbstractHttpConfigurer::disable) // 로그인 페이지 따로있으면 disable 해도됨 ( Spring 기본 제공 로그인창 사용시 사용)
			.formLogin(AbstractHttpConfigurer::disable) // 로그인 페이지 따로있으면 disable 해도됨 ( Spring 기본 제공 로그인창 사용시 사용)
			.csrf(AbstractHttpConfigurer::disable) // SSR 사용하지 않으면 disable 해도됨
			.cors(c -> c.configurationSource(customCorsConfigurationSource())); //아래 함수에서 리턴받은 cors 설정값을 적용

		//세션
		httpSecurity
			.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //인증정보를 세션에 저장하지 않는다. JWT 토큰으로만 인증할때 사용

		//인가 URL 패턴 설정 세부url --> 포괄 url 순으로 설정할것.
		httpSecurity
			.authorizeHttpRequests(customAuthorization -> {
				customAuthorization
					.requestMatchers(HttpMethod.GET, SWAGGER_URI_LIST).permitAll()//권한 필요없는 GET 허용 리스트 설정
					.requestMatchers(HttpMethod.GET, LOGIN_URI_LIST).permitAll() //권한 필요없는 POST 허용 리스트 설정
					.requestMatchers("/**").hasAnyRole("PARENT", "CHILD", "GUEST")//권한 별 접근 URL 설정
					.anyRequest().authenticated();  //그 외에 대한 URL에 대해서는 Authentication이 필요함을 설정
			});


		//인증 (Oauth2)
		httpSecurity
			.oauth2Login() //Oauth2 인증방식을 사용한다.
			.authorizationEndpoint()
			.baseUri("/api/oauth2/authorization")
			.authorizationRequestRepository(oAuth2AuthorizationRequestRepository)
			.and()
			.redirectionEndpoint()
			.baseUri("/api/login/oauth2/code/*")
			.and()
			.userInfoEndpoint()
			.userService(customOAuth2UserService)
			.and()
			.successHandler(oAuth2AuthenticationSuccessHandler())
			.failureHandler(oAuth2AuthenticationFailureHandler()).permitAll(); //인증 실패시에 대한 handler는 인증/인가 필요X

		// //예외처리
		// httpSecurity
		// 	.exceptionHandling()
		// 	.authenticationEntryPoint(new RestAuthenticationEntryPoint())
		// 	.accessDeniedHandler(tokenAccessDeniedHandler);


		// //커스텀필터
		// httpSecurity
		// 	.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, memberRepository, memberAuthRepository),
		// 		UsernamePasswordAuthenticationFilter.class);


		return httpSecurity.build();
	}

	@Bean
	public UrlBasedCorsConfigurationSource customCorsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.addAllowedOriginPattern("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.setAllowCredentials(true);
		source.registerCorsConfiguration("/**", corsConfiguration);

		return source;
	}

}