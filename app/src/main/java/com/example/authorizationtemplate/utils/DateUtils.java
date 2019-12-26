package com.example.authorizationtemplate.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    /**
     * Метод проверки просрочена ли дата
     * */
    public static boolean hasDatePassed(String date, String format) throws ParseException {
        Date d = parseDate(date, format);
        return d != null && d.compareTo(getDateNow()) < 0;
    }

    private static Date getDateNow(){
        return new Date();
    }

    private static Date parseDate(String date, String format) throws ParseException {
        if(date == null)
            throw new ParseException("Date is null", 1);
        SimpleDateFormat sdf
                = new SimpleDateFormat(format, new Locale("ru"));
        return sdf.parse(date);
    }
}
