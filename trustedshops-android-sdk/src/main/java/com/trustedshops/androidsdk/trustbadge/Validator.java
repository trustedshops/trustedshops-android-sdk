package com.trustedshops.androidsdk.trustbadge;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validator {

    /**
     * @param tsId
     * @return boolean
     */
    public static boolean validateTsId(String tsId) {
        String tsIdPattern = "^[xX][a-z0-9A-Z]{32}$";
        Pattern pattern = Pattern.compile(tsIdPattern);
        Matcher matcher = pattern.matcher(tsId);
        return matcher.matches();
    }
}
