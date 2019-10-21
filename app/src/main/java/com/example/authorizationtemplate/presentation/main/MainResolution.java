package com.example.authorizationtemplate.presentation.main;

import com.example.authorizationtemplate.ResourceProvider;
import com.example.authorizationtemplate.utils.resolution.NetworkResolution;

public class MainResolution extends NetworkResolution {

    private MainContract.View baseView;
    private ResourceProvider resourceProvider;


    public MainResolution(MainContract.View baseView, ResourceProvider resourceProvider){
        this.resourceProvider = resourceProvider;
        this.baseView = baseView;
    }

    @Override
    public void onUnauthorized() { baseView.showError(resourceProvider.getStrings().getWrongLoginPassword()); }

    @Override
    public void onForbidden() {
        baseView.showError(resourceProvider.getStrings().getAccessDenied());
    }

    @Override
    public void onServiceUnavailable() {
        baseView.showError(resourceProvider.getStrings().getServiceIsUnavailable());
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        baseView.showError(resourceProvider.getStrings().getServiceIsUnavailable());
    }

    @Override
    public void onConnectivityUnavailable() {
        baseView.showError(resourceProvider.getStrings().getNoInternetConnection());
    }
}
