package com.example.authorizationtemplate.di.module.context;

import android.content.Context;


import com.example.authorizationtemplate.di.scope.AppScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class AppContextModule {

    private final Context context;

    public AppContextModule(Context context){
        this.context = context;
    }

    @AppScope
    @Provides
    @Named("app_context")
    public Context provideContext() {
        return context;
    }
}
