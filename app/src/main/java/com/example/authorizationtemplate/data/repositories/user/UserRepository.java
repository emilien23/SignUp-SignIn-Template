package com.example.authorizationtemplate.data.repositories.user;

import com.example.authorizationtemplate.domain.models.AddUserData;
import com.example.authorizationtemplate.domain.models.TokenResponse;


import io.reactivex.Observable;
import retrofit2.Response;

public interface UserRepository {

    Observable<Response<TokenResponse>> addUser(AddUserData user);
}
