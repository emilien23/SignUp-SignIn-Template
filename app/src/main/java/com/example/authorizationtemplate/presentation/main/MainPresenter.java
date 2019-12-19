package com.example.authorizationtemplate.presentation.main;

import com.example.authorizationtemplate.GlobalNavigator;
import com.example.authorizationtemplate.domain.interactors.auth.logout.LogoutInteractor;
import com.example.authorizationtemplate.domain.interactors.get_string.GetStringInteractor;
import com.example.authorizationtemplate.domain.interactors.token_expired.TokenExpiredInteractor;

import javax.inject.Inject;

public class MainPresenter implements MainContract.Presenter, GetStringInteractor.Callback, LogoutInteractor.Callback {

    private MainContract.View view;
    private GetStringInteractor getStringInteractor;
    private TokenExpiredInteractor tokenExpiredInteractor;
    private LogoutInteractor logoutInteractor;
    private GlobalNavigator navigator;

    @Inject
    MainPresenter(LogoutInteractor logoutInteractor,
                  GetStringInteractor getStringInteractor,
                  TokenExpiredInteractor tokenExpiredInteractor,
                  GlobalNavigator navigator,
                  MainContract.View view) {
        this.tokenExpiredInteractor = tokenExpiredInteractor;
        this.logoutInteractor = logoutInteractor;
        this.navigator = navigator;
        this.getStringInteractor = getStringInteractor;
        this.view = view;
    }

    @Override
    public void create() { }

    @Override
    public void resume() {
        logoutInteractor.subscribeToCallback(this);
        getStringInteractor.subscribeToCallback(this);
        tokenExpiredInteractor.execute();
        getStringFromServer();
    }

    @Override
    public void pause() {
        getStringInteractor.unsubscribe();
        logoutInteractor.unsubscribe();
        tokenExpiredInteractor.unsubscribe();
    }

    @Override
    public void stop() { }

    @Override
    public void destroy() {
        view = null;
    }

    private void getStringFromServer() {
        getStringInteractor.execute();
    }

    @Override
    public void onStringDelivered(String msg) {
        view.showString(msg);
    }

    @Override
    public void logoutButtonClicked() {
        logoutInteractor.execute();
    }

    @Override
    public void onLogout() {
        navigator.navigateTo(GlobalNavigator.ACTION_LOGOUT);
    }
}
