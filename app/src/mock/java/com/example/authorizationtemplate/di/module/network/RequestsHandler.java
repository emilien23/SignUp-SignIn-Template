package com.example.authorizationtemplate.di.module.network;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Request;
import okhttp3.Response;
/**
 * Класс для обработки запросов на сервер и предоставления замоканных ответов
 * */
public class RequestsHandler {
    /**
     * Структура, хранящая url запросов и соответствующие им json-ответы
     * */
    private final Map<String, String> responsesMap = new HashMap<>();
    private Context context;

    public RequestsHandler(Context context) {
        this.context = context;
        responsesMap.put("users/login/", "mock_auth.json");
        responsesMap.put("users/registr/", "mock_register.json");
        responsesMap.put("getinfo/", "mock_getinfo.json");
    }

    /**
     * Метод, проверяющий необходимость перехвата запроса для подмены ответа
     * Проверяет совпадение пути запроса с имеющимися в responsesMap.
     * */
    public boolean shouldIntercept(@NonNull String path) {
        Set<String> keys = responsesMap.keySet();
        for (String interceptUrl : keys) {
            if (path.contains(interceptUrl))
                return true;
        }
        return false;
    }

    /**
     * Метод, формирующий ответ исходя из url в запросе
     * */
    @NonNull
    public Response proceed(@NonNull Request request, @NonNull String path) { //TODO Организовать проверку запроса для генереации ответа с ошибкой
        Set<String> keys = responsesMap.keySet();
        for (String interceptUrl : keys) {
            if (path.contains(interceptUrl)) {
                String mockResponsePath = responsesMap.get(interceptUrl);
                return createResponseFromAssets(request, mockResponsePath);
            }
        }

        return OkHttpResponse.error(request, 500, "Incorrectly intercepted request");
    }

    /**
     * Метод создания ответа из json-файла, который хранится в assets
     * */
    @NonNull
    private Response createResponseFromAssets(@NonNull Request request, @NonNull String assetPath) {
        try {
            final InputStream stream = context.getAssets().open(assetPath);
            //noinspection TryFinallyCanBeTryWithResources
            try {
                return OkHttpResponse.success(request, stream);
            } finally {
                stream.close();
            }
        } catch (IOException e) {
            return OkHttpResponse.error(request, 500, e.getMessage());
        }
    }
}
