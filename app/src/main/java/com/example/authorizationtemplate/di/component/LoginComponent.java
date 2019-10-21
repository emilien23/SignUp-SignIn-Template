package com.example.authorizationtemplate.di.component;

import com.example.authorizationtemplate.di.module.login.LoginModule;
import com.example.authorizationtemplate.di.scope.LoginScope;
import com.example.authorizationtemplate.presentation.login.LoginActivity;

import dagger.Subcomponent;

@LoginScope
@Subcomponent(modules = LoginModule.class)
public interface LoginComponent  {
    void inject(LoginActivity loginActivity);
}

