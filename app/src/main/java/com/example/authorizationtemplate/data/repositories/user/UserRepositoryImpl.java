package com.example.authorizationtemplate.data.repositories.user;

import com.example.authorizationtemplate.data.network.NetworkService;
import com.example.authorizationtemplate.domain.mapper.BaseObjectsMapper;
import com.example.authorizationtemplate.domain.models.AddUserData;
import com.example.authorizationtemplate.domain.models.RegistrationRequest;
import com.example.authorizationtemplate.domain.models.TokenResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Response;

public class UserRepositoryImpl implements UserRepository {

    private NetworkService.CommonApi commonApi;
    private BaseObjectsMapper mapper;

    @Inject
    public UserRepositoryImpl(NetworkService.CommonApi commonApi, BaseObjectsMapper mapper) {
        this.commonApi = commonApi;
        this.mapper = mapper;
    }

    @Override
    public Observable<Response<TokenResponse>> addUser(AddUserData user) {
        return commonApi.registration((RegistrationRequest)mapper.map(user));
    }
}
