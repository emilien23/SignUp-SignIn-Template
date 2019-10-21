package com.example.authorizationtemplate.di.module.network;


import com.example.authorizationtemplate.data.Settings;
import com.example.authorizationtemplate.data.network.NetworkService;
import com.example.authorizationtemplate.data.network.auth.AuthHolder;
import com.example.authorizationtemplate.di.module.settings.SettingsModule;
import com.example.authorizationtemplate.di.scope.AppScope;

import dagger.Module;
import dagger.Provides;

@Module(includes = {SettingsModule.class})
public class AuthModule {

    @AppScope
    @Provides
    public AuthHolder provideAuthHolder(NetworkService.CommonApi commonApi, Settings settings) {
        return new AuthHolder(commonApi, settings);
    }
}

