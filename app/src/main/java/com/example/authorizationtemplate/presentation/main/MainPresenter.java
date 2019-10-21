package com.example.authorizationtemplate.presentation.main;

import com.example.authorizationtemplate.domain.interactors.get_string.GetStringInteractor;

import javax.inject.Inject;

public class MainPresenter implements MainContract.Presenter, GetStringInteractor.Callback {

    private GetStringInteractor getStringInteractor;
    private MainContract.View view;

    @Inject
    public MainPresenter(GetStringInteractor getStringInteractor, MainContract.View view){
        this.getStringInteractor = getStringInteractor;
        this.view = view;
    }

    @Override
    public void create() { }

    @Override
    public void resume() {
        getStringInteractor.subscribeToCallback(this);
        getStringFromServer();
    }

    @Override
    public void pause() { getStringInteractor.unsubscribe(); }

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
}
