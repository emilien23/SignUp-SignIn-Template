package com.example.authorizationtemplate.data.network;


import com.example.authorizationtemplate.domain.models.Info;
import com.example.authorizationtemplate.domain.models.LoginRequest;
import com.example.authorizationtemplate.domain.models.TokenResponse;
import com.example.authorizationtemplate.domain.models.RegistrationRequest;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetworkService {

    /**
     * Неавторизированные запросы
     * */
    interface CommonApi{

        @POST("users/login/")
        Observable<Response<TokenResponse>> login(@Body LoginRequest login);

        @POST("users/registr/")
        Observable<Response<TokenResponse>> registration(@Body RegistrationRequest registration);
    }

    /**
     * Авторизированные запросы
     * */
    interface AuthApi{
        @POST("getinfo/")
        Observable<Response<Info>> getString();
    }
}
