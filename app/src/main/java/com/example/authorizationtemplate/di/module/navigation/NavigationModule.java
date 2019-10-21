package com.example.authorizationtemplate.di.module.navigation;

import android.content.Context;


import com.example.authorizationtemplate.GlobalNavigator;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;


@Module
public class NavigationModule {

    private Context context;

    public NavigationModule(@Named("local")Context context){
       this.context = context;
    }

    @Provides
    GlobalNavigator provideGlobalNavigator(){
        return new GlobalNavigator(context);
    }
}
