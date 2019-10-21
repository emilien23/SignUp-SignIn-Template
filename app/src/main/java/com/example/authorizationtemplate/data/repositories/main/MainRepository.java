package com.example.authorizationtemplate.data.repositories.main;


import io.reactivex.Observable;
import retrofit2.Response;

public interface MainRepository {
    Observable<Response<String>> getString();
}
