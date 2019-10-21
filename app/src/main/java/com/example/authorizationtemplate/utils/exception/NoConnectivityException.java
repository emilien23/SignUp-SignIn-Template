package com.example.authorizationtemplate.utils.exception;

import java.io.IOException;

public class NoConnectivityException extends IOException {
    @Override
    public String getMessage() {
        return "Нет подключения к интернету";
    }
}
