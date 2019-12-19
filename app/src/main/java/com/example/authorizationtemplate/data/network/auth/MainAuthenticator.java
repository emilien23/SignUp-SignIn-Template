package com.example.authorizationtemplate.data.network.auth;

import androidx.annotation.Nullable;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

import static com.example.authorizationtemplate.data.network.auth.AuthInterceptor.ACCESS_TOKEN_HEADER;


/**
 * Класс для проверки аутентификации пользователя
 * */
public class MainAuthenticator implements Authenticator {

    private AuthHolder authHolder;

    public MainAuthenticator(AuthHolder authHolder) {
        this.authHolder = authHolder;
    }

    /**
     * При несовпадении токена - обновляем его
     * */
    @Nullable
    @Override
    public synchronized Request authenticate(Route route, Response response) {
        String storedToken = authHolder.getToken();
        String requestToken = response.request().header(ACCESS_TOKEN_HEADER);

        Request.Builder requestBuilder = response.request().newBuilder();

        if (storedToken.equals(requestToken))
            authHolder.refresh();

        return buildRequest(requestBuilder);
    }

    private Request buildRequest(Request.Builder requestBuilder) {
        return requestBuilder.header(ACCESS_TOKEN_HEADER, authHolder.getToken()).build();
    }
}
