package com.esfp.sapaier.domain.collection.repository.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HttpMethodType {

    DELETE("HttpMethodType_DELETE"),
    PATCH("HttpMethodType_PATCH"),
    POST("HttpMethodType_POST"),
    GET("HttpMethodType_GET"),
    PUT("HttpMethodType_PUT"),
    OPTIONS("HttpMethodType_OPTIONS"),
    HEAD("HttpMethodType_HEAD");

    private final String httpMethodType;
}
