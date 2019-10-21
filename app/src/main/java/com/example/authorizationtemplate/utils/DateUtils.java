package com.example.authorizationtemplate.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    /**
     * Метод проверки просрочена ли дата
     * */
    public static boolean hasDatePassed(String date, String format)throws ParseException {
        SimpleDateFormat sdf
                = new SimpleDateFormat(format, new Locale("ru"));
        return sdf.parse(date).compareTo(new Date()) < 0;
    }
}
