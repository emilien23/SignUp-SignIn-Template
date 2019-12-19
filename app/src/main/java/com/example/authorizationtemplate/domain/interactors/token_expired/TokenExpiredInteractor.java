package com.example.authorizationtemplate.domain.interactors.token_expired;


import com.example.authorizationtemplate.domain.interactors.base.BaseInteractor;

public interface TokenExpiredInteractor extends BaseInteractor {

    void subscribeToCallback(Callback callback);

    void execute();

    interface Callback {
        void isCheckTokenExpired(Boolean isExpired);
    }
}
