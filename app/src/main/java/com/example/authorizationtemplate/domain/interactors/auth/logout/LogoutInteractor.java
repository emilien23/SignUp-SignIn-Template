package com.example.authorizationtemplate.domain.interactors.auth.logout;


import com.example.authorizationtemplate.domain.interactors.base.BaseInteractor;

public interface LogoutInteractor extends BaseInteractor {

    void execute();

    void subscribeToCallback(Callback callback);

    interface Callback{
        void onLogout();
    }
}
