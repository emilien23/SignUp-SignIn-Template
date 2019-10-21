package com.example.authorizationtemplate.di.component;


import com.example.authorizationtemplate.di.module.app.AppModule;
import com.example.authorizationtemplate.di.module.login.LoginModule;
import com.example.authorizationtemplate.di.module.main.MainModule;
import com.example.authorizationtemplate.di.module.navigation.NavigationModule;
import com.example.authorizationtemplate.di.module.network.AuthModule;
import com.example.authorizationtemplate.di.module.network.AuthNetworkModule;
import com.example.authorizationtemplate.di.module.network.CommonNetworkModule;
import com.example.authorizationtemplate.di.module.registration.RegistrationModule;
import com.example.authorizationtemplate.di.scope.AppScope;

import dagger.Component;

@Component(modules = {AuthModule.class, AuthNetworkModule.class,
        CommonNetworkModule.class, AppModule.class})
@AppScope
public interface AppComponent {
    LoginComponent addLoginComponent(LoginModule loginModule, NavigationModule navigationModule);
    RegistrationComponent addRegistrationComponent(RegistrationModule registrationModule, NavigationModule navigationModule);
    MainComponent addMainComponent(MainModule mainModule);
}
