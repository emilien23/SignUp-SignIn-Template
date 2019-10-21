package com.example.authorizationtemplate.di.module.app;


import android.content.Context;

import com.example.authorizationtemplate.ResourceProvider;
import com.example.authorizationtemplate.data.network.NetworkService;
import com.example.authorizationtemplate.data.network.auth.AuthHolder;
import com.example.authorizationtemplate.data.repositories.auth.AuthRepository;
import com.example.authorizationtemplate.data.repositories.auth.AuthRepositoryImpl;
import com.example.authorizationtemplate.di.scope.AppScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @AppScope
    @Provides
    public ResourceProvider provideResourceProvider(@Named("app_context") Context context){
        return new ResourceProvider(context);
    }

    @AppScope
    @Provides
    public AuthRepository provideAuthRepository(NetworkService.CommonApi commonApi, AuthHolder authHolder) {
        return new AuthRepositoryImpl(commonApi, authHolder);
    }
}
