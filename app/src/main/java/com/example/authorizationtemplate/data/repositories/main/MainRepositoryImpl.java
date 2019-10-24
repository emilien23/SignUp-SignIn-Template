package com.example.authorizationtemplate.data.repositories.main;

import com.example.authorizationtemplate.data.network.NetworkService;
import com.example.authorizationtemplate.domain.repositories.main.MainRepository;

import io.reactivex.Observable;
import retrofit2.Response;

public class MainRepositoryImpl implements MainRepository {

    private NetworkService.AuthApi authApi;

    public MainRepositoryImpl(NetworkService.AuthApi authApi){
        this.authApi = authApi;
    }

    @Override
    public Observable<Response<String>> getString() {
        return authApi.getString();
    }
}
