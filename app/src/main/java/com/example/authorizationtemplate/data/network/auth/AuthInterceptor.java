package com.example.authorizationtemplate.data.network.auth;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    static final String ACCESS_TOKEN_HEADER = "Authorization";

    private AuthHolder authHolder;

    public AuthInterceptor(AuthHolder authHolder) {
        this.authHolder = authHolder;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder requestBuilder = chain.request().newBuilder();
        if (chain.request().header(ACCESS_TOKEN_HEADER) == null) {
            requestBuilder.addHeader(ACCESS_TOKEN_HEADER, "Token " + authHolder.getToken());
        }

        return chain.proceed(requestBuilder.build());
    }
}
