# FROM ubuntu:latest

# RUN apt-get update && apt-get install -y findutils

# FROM openjdk:17-jdk as builder

# COPY gradlew .
# COPY gradle gradle
# COPY build.gradle .
# COPY settings.gradle .
# COPY src src
# RUN chmod +x ./gradlew
# RUN ./gradlew bootJar

# FROM openjdk:17
# COPY --from=builder build/libs/*.jar app.jar
# EXPOSE 8080

# ARG SERVER_MODE
# RUN echo "$SERVER_MODE"
# ENV SERVER_MODE=$SERVER_MODE

# ENTRYPOINT ["java", "-Dspring.profiles.active=${SERVER_MODE}", "-Duser.timezone=Asia/Seoul", "-jar", "/app.jar"]
FROM openjdk:17-jdk as builder
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]