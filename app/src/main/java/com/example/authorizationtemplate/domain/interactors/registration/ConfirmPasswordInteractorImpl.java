package com.example.authorizationtemplate.domain.interactors.registration;


import com.example.authorizationtemplate.domain.entities.PasswordUtils;
import com.example.authorizationtemplate.domain.interactors.base.ReactiveInteractor;

import io.reactivex.Scheduler;

public class ConfirmPasswordInteractorImpl extends ReactiveInteractor implements ConfirmPasswordInteractor {

    private ConfirmPasswordInteractor.Callback callback;

    public ConfirmPasswordInteractorImpl(Scheduler threadExecutor, Scheduler postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

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
