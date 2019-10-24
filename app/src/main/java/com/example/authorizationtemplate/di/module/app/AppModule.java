package com.example.authorizationtemplate.di.module.app;


import android.content.Context;

import com.example.authorizationtemplate.ResourceProvider;
import com.example.authorizationtemplate.data.network.NetworkService;
import com.example.authorizationtemplate.data.network.auth.AuthHolder;
import com.example.authorizationtemplate.data.repositories.auth.AuthRepositoryImpl;
import com.example.authorizationtemplate.di.scope.AppScope;
import com.example.authorizationtemplate.domain.repositories.auth.AuthRepository;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class AppModule {

    @AppScope
    @Provides
    @Named("executor")
    public Scheduler provideThreadExecutor() {
        return Schedulers.io();
    }

    @AppScope
    @Provides
    @Named("post_execution")
    public Scheduler providePostExecutionThread() {
        return AndroidSchedulers.mainThread();
    }

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
