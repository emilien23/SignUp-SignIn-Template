package com.example.authorizationtemplate.di.component;



import com.example.authorizationtemplate.di.module.main.MainModule;
import com.example.authorizationtemplate.di.scope.MainScope;
import com.example.authorizationtemplate.presentation.main.MainActivity;

import dagger.Subcomponent;

@MainScope
@Subcomponent(modules = MainModule.class)
public interface MainComponent  {
    void inject(MainActivity mainActivity);
}