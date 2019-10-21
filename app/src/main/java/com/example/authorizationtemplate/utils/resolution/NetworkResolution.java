package com.example.authorizationtemplate.utils.resolution;

public abstract class NetworkResolution implements Resolution {

    @Override
    public void onHttpException(Integer code) {
        switch(code){
            case 401:
                onUnauthorized();
                break;
            case 403:
                onForbidden();
                break;
                default:
                    onServiceUnavailable();
        }
    }

    public abstract void onUnauthorized();

    public abstract void onForbidden();

    public abstract void onServiceUnavailable();
}
