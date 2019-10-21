package com.example.authorizationtemplate.presentation.login;


import com.example.authorizationtemplate.ResourceProvider;
import com.example.authorizationtemplate.utils.resolution.NetworkResolution;

public class LoginResolution extends NetworkResolution {

    private LoginContract.View baseView;
    private ResourceProvider resourceProvider;


    public LoginResolution(LoginContract.View baseView, ResourceProvider resourceProvider){
        this.resourceProvider = resourceProvider;
        this.baseView = baseView;
    }

    @Override
    public void onUnauthorized() { baseView.showErrorMessage(resourceProvider.getStrings().getWrongLoginPassword()); }

    @Override
    public void onForbidden() {
        baseView.showToastMessage(resourceProvider.getStrings().getAccessDenied());
    }

    @Override
    public void onServiceUnavailable() {
        baseView.showErrorMessage(resourceProvider.getStrings().getServiceIsUnavailable());
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        baseView.showToastMessage(resourceProvider.getStrings().getServiceIsUnavailable());
    }

    @Override
    public void onConnectivityUnavailable() {
        baseView.showToastMessage(resourceProvider.getStrings().getNoInternetConnection());
    }
}
