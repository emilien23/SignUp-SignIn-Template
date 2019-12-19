package com.example.authorizationtemplate.domain.interactors.token_expired;


import com.example.authorizationtemplate.domain.interactors.base.ReactiveInteractor;
import com.example.authorizationtemplate.domain.repositories.auth.AuthRepository;

import io.reactivex.Scheduler;

/**
 * Класс проверки токена на истечение даты
 * */
public class TokenExpiredInteractorImpl extends ReactiveInteractor implements TokenExpiredInteractor {

    private TokenExpiredInteractor.Callback callback;
    private AuthRepository authRepository;

    public TokenExpiredInteractorImpl(AuthRepository authRepository,
                                      Scheduler threadExecutor,
                                      Scheduler postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.authRepository = authRepository;
    }

    @Override
    public void subscribeToCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void execute() {
        callback.isCheckTokenExpired(authRepository.isTokenDateExpired());
    }

    @Override
    public void unsubscribe() {
        callback = null;
    }
}
