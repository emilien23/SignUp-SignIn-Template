package com.example.authorizationtemplate;

import android.app.Application;

import com.example.authorizationtemplate.di.ComponentManager;
import com.xfinity.resourceprovider.RpApplication;

@RpApplication(generateColorProvider = false,
        generateDimensionProvider = false,
        generateIdProvider = false)
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ComponentManager.getInstance().initAppComponent(this);
    }
}
