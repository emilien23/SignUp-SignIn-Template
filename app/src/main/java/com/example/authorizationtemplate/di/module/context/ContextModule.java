package com.example.authorizationtemplate.di.module.context;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context){
        this.context = context;
    }

    @Provides
    @Named("local")
    public Context provideContext() {
        return context;
    }
}
