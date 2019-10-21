package com.example.authorizationtemplate.data.network.auth;

import android.content.Context;


import com.example.authorizationtemplate.utils.exception.NoConnectivityException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Перехват запросов при отсутствии интернета для вывода предупреждающего сообщения
 * */
public class ConnectivityInterceptor implements Interceptor{
    private Context context;

    public ConnectivityInterceptor(Context context){
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!NetworkUtils.isOnline(context))
            throw new NoConnectivityException();

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}
