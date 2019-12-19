package com.example.authorizationtemplate.domain.interactors.auth.login;


import com.example.authorizationtemplate.domain.interactors.base.BaseInteractor;
import com.example.authorizationtemplate.domain.models.LoginRequest;

public interface LoginInteractor extends BaseInteractor {

    void execute(LoginRequest loginRequest);

    void subscribeToCallback(Callback callback);

    interface Callback{
        void onLoginSucceeded();
    }
}
