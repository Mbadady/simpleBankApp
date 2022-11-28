package com.mbadady.simpleBankApp.dto.response;

public class JWTResponse {

    private String accessKey;

    private String tokenType = "Bearer";

    public JWTResponse(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
