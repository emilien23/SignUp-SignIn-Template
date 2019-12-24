package com.example.authorizationtemplate.data.network.auth;


import com.example.authorizationtemplate.data.Settings;
import com.example.authorizationtemplate.data.network.NetworkService;
import com.example.authorizationtemplate.domain.models.TokenResponse;
import com.example.authorizationtemplate.utils.DateUtils;

import java.text.ParseException;

/**
 * Класс для работы с аутенфикационными данными(сохранение в перференсах, очистка, обновление, проверка валидной даты)
 * */
public class AuthHolder {

    private NetworkService.CommonApi commonApi;
    private SessionListener sessionListener;
    private Settings settings;
    private String TOKEN_LIFETIME_DATE_FORMAT = "yyyy-MM-dd HH:mm";

    public AuthHolder(NetworkService.CommonApi commonApi, Settings settings) {
        this.commonApi = commonApi;
        this.settings = settings;
    }

    public String getToken() { return settings.getAccessToken(); }

    public void setLoginData(TokenResponse response) {
         settings.setAccessToken(response.getAccessToken());
         settings.setTokenDateExpired(response.getDatetimeExpired());
    }

    public void clearLoginData() {
        settings.clearSettings();
    }

    public boolean isTokenExpired() {
        if(settings.getAccessToken() == null)
            return true;
        try {
            if (isDateExpired(settings.getTokenDateExpired()))
                return true;
        }catch(ParseException e){
            return true;
        }
        return false;
    }

    private boolean isDateExpired(String date) throws ParseException {
        return DateUtils.hasDatePassed(date, TOKEN_LIFETIME_DATE_FORMAT);
    }

    public void refresh() {
        if (sessionListener != null)
            sessionListener.sessionExpired();
    }


    public void subscribeToSessionExpired(SessionListener sessionListener) {
        this.sessionListener = sessionListener;
    }
}
