package com.example.authorizationtemplate.data.network.auth;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Objects;

/**
 * Класс проверки, есть ли доступ в сеть на устройстве
 * */
public class NetworkUtils {

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();

        return network != null && network.isConnectedOrConnecting();
    }
}
