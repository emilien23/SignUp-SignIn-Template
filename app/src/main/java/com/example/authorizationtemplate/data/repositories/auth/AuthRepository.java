package com.example.authorizationtemplate.data.repositories.auth;


import com.example.authorizationtemplate.domain.interactors.auth.AuthListener;
import com.example.authorizationtemplate.domain.models.LoginRequest;
import com.example.authorizationtemplate.domain.models.TokenResponse;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.Response;

public interface AuthRepository {

    void subscribeUnauthorized(AuthListener authListener);

    Observable<Response<TokenResponse>> login(LoginRequest loginData);

    Completable logout();

    void saveLoginData(TokenResponse loginData);

    boolean isTokenDateExpired();
}
