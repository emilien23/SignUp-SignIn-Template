package com.example.authorizationtemplate.di.module.login;



import com.example.authorizationtemplate.di.scope.LoginScope;
import com.example.authorizationtemplate.presentation.login.LoginContract;
import com.example.authorizationtemplate.presentation.login.LoginPresenter;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class LoginContractModule {

    @LoginScope
    @Binds
    public abstract LoginContract.Presenter bindLoginPresenter(LoginPresenter loginPresenter);
}
