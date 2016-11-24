package com.trustedshops.androidsdk.trustbadge;


import java.util.ArrayList;
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

    public static boolean isValidEmailAddress(String email) {
        if (email == null || email == "") {return true;} // allow missing email
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static ArrayList<Error> validateTrustbadgeOrder(TrustbadgeOrder order) {

        ArrayList<Error> errorList = new ArrayList<Error>();

        /* check if minimum required fields are set */
        if (order.getTsId() == null || order.getTsCheckoutOrderAmount() == null || order.getTsCheckoutOrderPaymentType() == null || order.getTsCheckoutOrderNr() == null || order.getTsCheckoutBuyerEmail() == null) {
            errorList.add(new Error("Required parameter are no set, you have to provide: tsCheckoutOrderAmont, tsCheckoutOrderPaymentType, tsCheckoutOrderNr, tsCheckoutBuyerEmail"));
        }

        /* check if tsid is valid */
        if (!validateTsId(order.getTsId())) {
            errorList.add(new Error("TSID is invalid"));
        }

        if (!isValidEmailAddress(order.getTsCheckoutBuyerEmail())) {
            errorList.add(new Error("BuyerEmail is invalid"));
        }

        return errorList;
    }
}
