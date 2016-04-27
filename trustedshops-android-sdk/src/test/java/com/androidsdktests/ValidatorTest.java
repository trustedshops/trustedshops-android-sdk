package com.androidsdktests;


import com.trustedshops.androidsdk.trustbadge.Validator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class ValidatorTest {

    protected final ArrayList<String> validTsIds = new ArrayList<String>(Arrays.asList(
            "XC8B181176B92AB62AB07D8AECEB02BF4",
            "XB325209EC059B08F489A0E8B2434A05E"
    ));

    protected final ArrayList<String> invalidTsIds = new ArrayList<String>(Arrays.asList(
            "",
            "XC8B181176B92AB62AB07D8AECEB02BF",
            "XC8B181176B92AB62AB07D8AECEB02BF44",
            "DC8B181176B92AB62AB07D8AECEB02BF4"
    ));

    protected final ArrayList<String> validEmails = new ArrayList<String>(Arrays.asList(
            "user@example.com",
            "user@example.co.uk",
            "user+label@example.com"
    ));

    protected final ArrayList<String> invalidEmails = new ArrayList<String>(Arrays.asList(
            "",
            "userexample.com",
            "userexamplecom",
            "user@example"
    ));



    @Test
    public void testTsIdValidator() {

        for (String validTsId : validTsIds) {
            assert Validator.validateTsId(validTsId) == true;
        }

        for (String invalidTsId : invalidTsIds) {
            assert Validator.validateTsId(invalidTsId) == false;
        }

    }


    @Test
    public void testEmailValidator() {

        for (String validEmail : validEmails) {
            assert Validator.isValidEmailAddress(validEmail) == true;
        }

        for (String invalidEmail : invalidEmails) {
            assert Validator.isValidEmailAddress(invalidEmail) == false;
        }

    }

}
