package com.example.authorizationtemplate.presentation.registration;

import com.example.authorizationtemplate.ResourceProvider;
import com.example.authorizationtemplate.utils.resolution.NetworkResolution;

public class RegistrationResolution extends NetworkResolution {

    private RegistrationContract.View baseView;
    private ResourceProvider resourceProvider;


    public RegistrationResolution(RegistrationContract.View baseView, ResourceProvider resourceProvider){
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
