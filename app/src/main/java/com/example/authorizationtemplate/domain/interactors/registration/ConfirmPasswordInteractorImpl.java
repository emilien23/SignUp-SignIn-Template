package com.example.authorizationtemplate.domain.interactors.registration;


import com.example.authorizationtemplate.domain.entities.PasswordUtils;

public class ConfirmPasswordInteractorImpl implements ConfirmPasswordInteractor {

    private ConfirmPasswordInteractor.Callback callback;

    @Override
    public void execute(String password, String confirmPassword) {
        if(PasswordUtils.equalsPasswords(password, confirmPassword))
            callback.onPasswordsMatch();
        else
            callback.onPasswordsNotMatch();
    }

    @Override
    public void subscribeToCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void unsubscribe() { this.callback = null; }
}
