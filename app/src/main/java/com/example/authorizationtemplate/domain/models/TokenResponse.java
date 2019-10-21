package com.example.authorizationtemplate.domain.models;

import com.google.gson.annotations.SerializedName;

public class TokenResponse {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("datetime_expired")
    private String datetimeExpired;

    @SerializedName("role")
    private Integer role;

    public TokenResponse(String accessToken, String datetimeExpired, Integer role) {
        this.accessToken = accessToken;
        this.datetimeExpired = datetimeExpired;
        this.role = role;
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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
