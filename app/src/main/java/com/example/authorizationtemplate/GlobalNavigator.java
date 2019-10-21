package com.example.authorizationtemplate;

import android.content.Context;
import android.content.Intent;

import com.example.authorizationtemplate.presentation.login.LoginActivity;
import com.example.authorizationtemplate.presentation.main.MainActivity;
import com.example.authorizationtemplate.presentation.registration.RegistrationActivity;


/**
 * Класс для навигации по экранам приложения
 * */

public class GlobalNavigator {
    public static final String ACTION_LOGIN = "login_action";
    public static final String ACTION_LOGOUT = "logout_action";
    public static final String ACTION_MAIN = "main_action";
    public static final String ACTION_REGISTRATION = "registration_action";

    private Context context;

    public GlobalNavigator(Context context){
        this.context = context;
    }

    public void navigateTo(final String action){
        switch (action){
            case ACTION_LOGIN:
                startLogin();
                break;
            case ACTION_MAIN:
                startRegistration();
                break;
            case ACTION_REGISTRATION:
                startMain();
                break;
            case ACTION_LOGOUT:
                logout();
                break;
        }
    }

    private void startRegistration() {
        context.startActivity(new Intent(context, RegistrationActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    private void startMain() {
        context.startActivity(new Intent(context, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    private void startLogin(){
        context.startActivity(new Intent(context, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    private void logout(){
        startLogin();
    }
}
