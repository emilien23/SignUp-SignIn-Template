package com.example.authorizationtemplate.domain.models;

import com.google.gson.annotations.SerializedName;

public class TokenResponse {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("datetime_expired")
    private String datetimeExpired;

    @SerializedName("refresh_token")
    private String refreshToken;

    public TokenResponse(String accessToken, String datetimeExpired, String refreshToken) {
        this.accessToken = accessToken;
        this.datetimeExpired = datetimeExpired;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getDatetimeExpired() {
        return datetimeExpired;
    }

    public void setDatetimeExpired(String datetimeExpired) {
        this.datetimeExpired = datetimeExpired;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
