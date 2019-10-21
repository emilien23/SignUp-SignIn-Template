package com.example.authorizationtemplate.di.module.main;


import com.example.authorizationtemplate.di.scope.MainScope;
import com.example.authorizationtemplate.presentation.main.MainContract;
import com.example.authorizationtemplate.presentation.main.MainPresenter;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MainContractModule {

    @MainScope
    @Binds
    public abstract MainContract.Presenter bindMainPresenter(MainPresenter mainPresenter);
}
