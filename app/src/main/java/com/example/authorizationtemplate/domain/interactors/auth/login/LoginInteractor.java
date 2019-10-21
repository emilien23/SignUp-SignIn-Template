package com.example.authorizationtemplate.domain.interactors.auth.login;


import com.example.authorizationtemplate.domain.interactors.base.BaseInteractor;

public interface LoginInteractor extends BaseInteractor {

    void execute(String email, String password);

    void subscribeToCallback(Callback callback);

    interface Callback{
        void onLoginSucceeded();
    }
}
