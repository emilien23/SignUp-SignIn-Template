package com.example.authorizationtemplate.domain.interactors.token_expired;


import com.example.authorizationtemplate.data.repositories.auth.AuthRepository;
import com.example.authorizationtemplate.domain.interactors.base.ReactiveInteractor;

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
    public void checkTokenExpired() {
        callback.isCheckTokenExpired(authRepository.isTokenDateExpired());
    }

    @Override
    public void unsubscribe() {
        callback = null;
    }
}
