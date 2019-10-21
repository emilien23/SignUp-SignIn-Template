package com.example.authorizationtemplate.data.network.wrapper;

import com.example.authorizationtemplate.utils.exception.NoConnectivityException;
import com.example.authorizationtemplate.utils.resolution.Resolution;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Класс-обертка для проверки ответов сервера на наличие ошибок
 * */
public abstract class ResolverCallbackWrapper<T> extends DisposableObserver<Response<T>> {

    private Resolution resolution;

    public ResolverCallbackWrapper(Resolution resolution){
        this.resolution = resolution;
    }

    protected abstract void onSuccess(T response);

    @Override
    public void onNext(Response<T> baseResponse) {
        if(baseResponse.isSuccessful())
            onSuccess(baseResponse.body());
        else
            resolution.onHttpException(baseResponse.code());
    }

    @Override
    public void onComplete() { }

    @Override
    public void onError(Throwable e) {
        if (e instanceof NoConnectivityException)
            resolution.onConnectivityUnavailable();
        else
            if (e instanceof HttpException)
                resolution.onHttpException(((HttpException) e).code());
            else
                resolution.onError(e);
    }
}
