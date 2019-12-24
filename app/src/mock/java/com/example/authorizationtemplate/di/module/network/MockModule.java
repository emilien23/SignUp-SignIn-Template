package com.example.authorizationtemplate.di.module.network;


import android.content.Context;

import com.example.authorizationtemplate.di.scope.AppScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class MockModule {

    @AppScope
    @Provides
    public MockingInterceptor provideMockingInterceptor(@Named("app_context") Context context) {
        return new MockingInterceptor(context);
    }
}
