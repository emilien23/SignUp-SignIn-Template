package com.example.authorizationtemplate.di.component;

import com.example.authorizationtemplate.di.module.registration.RegistrationModule;
import com.example.authorizationtemplate.di.scope.RegistrationScope;
import com.example.authorizationtemplate.presentation.registration.RegistrationActivity;

import dagger.Subcomponent;

@RegistrationScope
@Subcomponent(modules = RegistrationModule.class)
public interface RegistrationComponent  {
    void inject(RegistrationActivity registrationActivity);
}
