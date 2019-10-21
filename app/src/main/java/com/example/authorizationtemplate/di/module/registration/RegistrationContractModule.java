package com.example.authorizationtemplate.di.module.registration;


import com.example.authorizationtemplate.di.scope.RegistrationScope;
import com.example.authorizationtemplate.presentation.registration.RegistrationContract;
import com.example.authorizationtemplate.presentation.registration.RegistrationPresenter;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RegistrationContractModule {

    @RegistrationScope
    @Binds
    public abstract RegistrationContract.Presenter bindRegistrationPresenter(RegistrationPresenter registrationPresenter);
}
