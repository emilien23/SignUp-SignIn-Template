package com.example.authorizationtemplate.domain.repositories.main;


import com.example.authorizationtemplate.domain.models.Info;

import io.reactivex.Observable;
import retrofit2.Response;

public interface MainRepository {
    Observable<Response<Info>> getString();
}
