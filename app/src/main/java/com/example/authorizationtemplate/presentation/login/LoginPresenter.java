package com.example.authorizationtemplate.presentation.login;



import com.example.authorizationtemplate.GlobalNavigator;
import com.example.authorizationtemplate.domain.interactors.auth.login.LoginInteractor;
import com.example.authorizationtemplate.domain.models.LoginRequest;

import javax.inject.Inject;

import static com.example.authorizationtemplate.GlobalNavigator.ACTION_MAIN;
import static com.example.authorizationtemplate.GlobalNavigator.ACTION_REGISTRATION;

public class LoginPresenter implements LoginContract.Presenter, LoginInteractor.Callback{

    private LoginInteractor loginInteractor;
    private GlobalNavigator navigator;
    private LoginContract.View view;

    @Inject
    public LoginPresenter(LoginInteractor loginInteractor, GlobalNavigator navigator, LoginContract.View view){
        this.view = view;
        this.loginInteractor = loginInteractor;
        this.navigator = navigator;
    }

    public void setAuthData(String email, String password){
        LoginRequest loginRequest = new LoginRequest(email, password);
        loginInteractor.execute(loginRequest);
    }

    @Override
    public void registrationButtonClicked() {
        navigator.navigateTo(ACTION_REGISTRATION);
    }

    @Override
    public void onLoginSucceeded() {
        navigator.navigateTo(ACTION_MAIN);
    }

    @Override
    public void create() { }

    @Override
    public void resume() {
        loginInteractor.subscribeToCallback(this);
    }

    @Override
    public void pause() {
        loginInteractor.unsubscribe();
    }

    @Override
    public void stop() { }

    @Override
    public void destroy() {
        view = null;
    }
}
