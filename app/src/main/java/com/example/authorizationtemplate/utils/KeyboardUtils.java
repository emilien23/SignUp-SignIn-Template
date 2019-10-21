package com.example.authorizationtemplate.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Objects;

public class KeyboardUtils {

    /**
     * Метод скрытия клавиатуры
     * */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null)
            view = new View(activity);

        Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
