package com.example.authorizationtemplate.domain.models;

import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("data")
    private String data;

    public Info(String data){
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
