package com.example.authorizationtemplate.di;

import android.content.Context;

import com.example.authorizationtemplate.di.component.AppComponent;
import com.example.authorizationtemplate.di.component.DaggerAppComponent;
import com.example.authorizationtemplate.di.component.LoginComponent;
import com.example.authorizationtemplate.di.component.MainComponent;
import com.example.authorizationtemplate.di.component.RegistrationComponent;
import com.example.authorizationtemplate.di.module.context.AppContextModule;
import com.example.authorizationtemplate.di.module.login.LoginModule;
import com.example.authorizationtemplate.di.module.main.MainModule;
import com.example.authorizationtemplate.di.module.navigation.NavigationModule;
import com.example.authorizationtemplate.di.module.registration.RegistrationModule;
import com.example.authorizationtemplate.presentation.login.LoginContract;
import com.example.authorizationtemplate.presentation.main.MainContract;
import com.example.authorizationtemplate.presentation.registration.RegistrationContract;


/**
 * Менеджер для получения доступа к DI-компонентам
 * */
public class ComponentManager {

    private static volatile ComponentManager instance;
    private AppComponent appComponent;
    private LoginComponent loginComponent;
    private RegistrationComponent registrationComponent;
    private MainComponent mainComponent;

    public static ComponentManager getInstance() {
        if (instance == null) {
            synchronized (ComponentManager.class) {
                if (instance == null)
                    instance = new ComponentManager();
            }
        }
        return instance;
    }

    private ComponentManager() {}

    public void addLoginComponent(LoginContract.View view, Context context) {
        if(loginComponent == null)
            loginComponent = appComponent
                    .addLoginComponent(new LoginModule(view), new NavigationModule(context));
    }

    public void addRegistrationComponent(RegistrationContract.View view, Context context) {
        if(registrationComponent == null)
            registrationComponent = appComponent
                            .addRegistrationComponent(new RegistrationModule(view), new NavigationModule(context));
    }

    public void addMainComponent(MainContract.View view) {
        if(mainComponent == null)
            mainComponent = appComponent
                    .addMainComponent(new MainModule(view));
    }

    public void initAppComponent(Context context) {
        appComponent = DaggerAppComponent.builder()
                .appContextModule((new AppContextModule(context)))
                        .build();
    }

    public LoginComponent getLoginComponent() {
        return loginComponent;
    }

    public MainComponent getMainComponent() {
        return mainComponent;
    }

    public RegistrationComponent getRegistrationComponent() {
        return registrationComponent;
    }

    public void clearMainComponent() {
        mainComponent = null;
    }

    public void clearRegistrationConpoment() {
        registrationComponent = null;
    }

    public void clearLoginComponent() {
        loginComponent = null;
    }

}
