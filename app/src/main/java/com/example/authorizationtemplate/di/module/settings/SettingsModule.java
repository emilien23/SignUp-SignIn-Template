package com.example.authorizationtemplate.di.module.settings;

import android.content.Context;


import com.example.authorizationtemplate.data.Settings;
import com.example.authorizationtemplate.di.module.context.AppContextModule;
import com.example.authorizationtemplate.di.scope.AppScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module(includes = AppContextModule.class)
public class SettingsModule {

    @AppScope
    @Provides
    public Settings provideSettings(@Named("app_context")Context context) {
        return Settings.getInstance(context);
    }
}
