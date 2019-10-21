package com.example.authorizationtemplate.data.repositories.auth;



import com.example.authorizationtemplate.data.network.NetworkService;
import com.example.authorizationtemplate.data.network.auth.AuthHolder;
import com.example.authorizationtemplate.data.network.auth.SessionListener;
import com.example.authorizationtemplate.domain.interactors.auth.AuthListener;
import com.example.authorizationtemplate.domain.models.LoginRequest;
import com.example.authorizationtemplate.domain.models.TokenResponse;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Репозиторий, имплементирующий процессы аутентификации
 * */
public class AuthRepositoryImpl implements AuthRepository, SessionListener {
    private NetworkService.CommonApi commonApi;
    private AuthHolder authHolder;

    private volatile AuthListener authListener;

    @Inject
    public AuthRepositoryImpl(NetworkService.CommonApi commonApi, AuthHolder authHolder) {
        this.authHolder = authHolder;
        this.commonApi = commonApi;
        authHolder.subscribeToSessionExpired(this);
    }

    @Override
    public void subscribeUnauthorized(AuthListener authListener) {
        this.authListener = authListener;
    }

    @Override
    public Observable<Response<TokenResponse>> login(LoginRequest loginData) {
        return commonApi.login(loginData);
    }

    @Override
    public Completable logout() {
        return Completable.fromAction(() -> authHolder.clearLoginData());
    }

    @Override
    public void saveLoginData(TokenResponse loginData) {
        authHolder.setLoginData(loginData);
    }

    @Override
    public boolean isTokenDateExpired() {
        return authHolder.isTokenExpired();
    }

    @Override
    public void sessionExpired() {
        if(authListener != null)
            authListener.onUnauthorized();
    }
}
