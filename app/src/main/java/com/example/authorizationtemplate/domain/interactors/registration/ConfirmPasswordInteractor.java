package com.example.authorizationtemplate.domain.interactors.registration;

import com.example.authorizationtemplate.domain.interactors.base.BaseInteractor;

public interface ConfirmPasswordInteractor extends BaseInteractor {

    void execute(String password, String confirmPassword);

    void subscribeToCallback(Callback callback);

    interface Callback{
        void onPasswordsMatch();
        void onPasswordsNotMatch();
    }
}
