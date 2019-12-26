package com.example.authorizationtemplate.di.module.network;

import android.content.Context;
import android.os.SystemClock;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interceptor используется в mockDebug и mockRelease сборках для тестирования запросов
 * без сервера. Перехватывает все запросы на сервер и подменяет ответ
 * */
public class MockingInterceptor implements Interceptor {

    private final RequestsHandler handler;

    private final Random random;

    public MockingInterceptor(Context context) {
        handler = new RequestsHandler(context);
        random = new SecureRandom();
    }

    /**
     * Метод перехвата запроса. Проверяет на необходимость перехвата и возвращает ответ,
     * создавая при этом небольшую задержку для иммитации работы сервера
     * */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String path = request.url().encodedPath();
        if (handler.shouldIntercept(path)) {
            Response response = handler.proceed(request, path);
            int delay = 500 + random.nextInt(100);
            SystemClock.sleep(delay);
            return response;
        }
        return chain.proceed(request);
    }
}
