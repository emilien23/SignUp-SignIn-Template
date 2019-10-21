package com.example.authorizationtemplate.domain.interactors.token_expired;


import com.example.authorizationtemplate.data.repositories.auth.AuthRepository;

/**
 * Класс проверки токена на истечение даты
 * */
public class TokenExpiredInteractorImpl implements TokenExpiredInteractor {

    private TokenExpiredInteractor.Callback callback;
    private AuthRepository authRepository;


    public TokenExpiredInteractorImpl(AuthRepository authRepository){
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
