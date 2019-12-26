package com.example.authorizationtemplate.di.module.network;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.authorizationtemplate.data.Settings;
import com.example.authorizationtemplate.domain.models.Info;
import com.example.authorizationtemplate.domain.models.LoginRequest;
import com.example.authorizationtemplate.domain.models.RegistrationRequest;
import com.google.gson.Gson;

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
    private final Map<String, String> successResponsesMap = new HashMap<>();
    private final Map<String, String> errorResponsesMap = new HashMap<>();

    private final String LOGIN_REQUEST_PATH = "users/login/";
    private final String REGISTER_REQUEST_PATH = "users/registr/";
    private final String GET_INFO_REQUEST_PATH = "users/getinfo/";
    private final Integer ERROR_RESPONSE_CODE = 500;
    /*
     * Реализовано для возможности тестирования неверных запросов на сервер.
     * */
    public static final String TEST_ERROR = "error";


    private Context context;

    public RequestsHandler(Context context) {
        this.context = context;
        successResponsesMap.put(LOGIN_REQUEST_PATH, "mock_auth.json");
        successResponsesMap.put(REGISTER_REQUEST_PATH, "mock_register.json");
        successResponsesMap.put(GET_INFO_REQUEST_PATH, "mock_getinfo.json");

        errorResponsesMap.put(LOGIN_REQUEST_PATH, "Authenticate error");
        errorResponsesMap.put(REGISTER_REQUEST_PATH, "Registration error");
        errorResponsesMap.put(GET_INFO_REQUEST_PATH, "Get information error");
    }

    /**
     * Метод, проверяющий необходимость перехвата запроса для подмены ответа
     * Проверяет совпадение пути запроса с имеющимися в successResponsesMap.
     * */
    public boolean shouldIntercept(@NonNull String path) {
        Set<String> keys = successResponsesMap.keySet();
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
    public Response proceed(@NonNull Request request, @NonNull String path) {
        /*
         * Проверка на хранение в преференсах поля token со значением error.
         * Реализовано для возможности тестирования неверных запросов на сервер.
         * */
        if(Settings.getInstance(context).getAccessToken() != null
                && Settings.getInstance(context).getAccessToken().equals(TEST_ERROR))
            return OkHttpResponse.error(request, ERROR_RESPONSE_CODE, TEST_ERROR);

        Set<String> keys = successResponsesMap.keySet();
        for (String interceptUrl : keys) {
            if (path.contains(interceptUrl)) {
                    String mockResponsePath = successResponsesMap.get(interceptUrl);
                    return createResponseFromAssets(request, mockResponsePath);
            }
        }
        return OkHttpResponse
                .error(request,
                ERROR_RESPONSE_CODE,
                "Incorrectly intercepted request");
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
