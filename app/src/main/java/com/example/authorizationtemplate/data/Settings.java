package com.example.authorizationtemplate.data;

import android.content.Context;
import android.content.SharedPreferences;

public class Settings {

    private static final String APP_PREFERENCES_NAME = "settings";
    private static final String APP_PREFERENCES_TOKEN_DATE_EXPIRED = "token_date_expired";
    private static final String APP_PREFERENCES_ACCESS_TOKEN = "access";
    private static final String APP_PREFERENCES_REFRESH_TOKEN = "refresh";

    private static SharedPreferences preferences;

    private static Settings settings;

    public static Settings getInstance(Context context){
        if(settings == null)
            synchronized (Settings.class){
                if(settings == null)
                    settings = new Settings(context);
            }

        return settings;
    }

    private Settings(Context context) {
        preferences = context
                .getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void clearSettings(){
        preferences.edit().clear().apply();
    }

    public void setAccessToken(String token){
        preferences.edit().putString(APP_PREFERENCES_ACCESS_TOKEN, token).apply();
    }

    public String getAccessToken(){
        return preferences.getString(APP_PREFERENCES_ACCESS_TOKEN, null);
    }

    public void setRefreshToken(String token){
        preferences.edit().putString(APP_PREFERENCES_REFRESH_TOKEN, token).apply();
    }

    public String getRefreshToken(){
        return preferences.getString(APP_PREFERENCES_REFRESH_TOKEN, null);
    }


    public void setTokenDateExpired(String token){
        preferences.edit().putString(APP_PREFERENCES_TOKEN_DATE_EXPIRED, token).apply();
    }

    public String getTokenDateExpired(){
        return preferences.getString(APP_PREFERENCES_TOKEN_DATE_EXPIRED, null);
    }
}
