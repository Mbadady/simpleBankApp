package com.mbadady.simpleBankApp.security;

public interface SecurityConstant {
    String JWT_SECRET_KEY = "Mbadady1_";
    int JWT_SECRET_EXPIRATION_IN_MILLISECONDS = 604800000;
    String JWT_HEADER = "Authorization";
}
