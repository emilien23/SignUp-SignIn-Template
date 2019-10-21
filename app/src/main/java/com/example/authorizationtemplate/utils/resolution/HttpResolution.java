package com.example.authorizationtemplate.utils.resolution;

public interface HttpResolution {

    void onHttpException(Integer code);

    void onError(Throwable throwable);
}
