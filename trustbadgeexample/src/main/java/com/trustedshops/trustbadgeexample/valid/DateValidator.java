package com.trustedshops.trustbadgeexample.valid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by trumga2 on 5/9/16.
 */
public class DateValidator {
    private static final String dateFormat = "dd.MM.yyyy";

    public static boolean isValidDate(String dateToValidate) {

        if (dateToValidate == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {

            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            if (year < 1970) {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
