package com.androidsdktests;


import android.os.Message;

import com.trustedshops.androidsdk.trustbadge.OnTsCustomerReviewsFetchCompleted;
import com.trustedshops.androidsdk.trustbadge.Shop;
import com.trustedshops.androidsdk.trustbadge.Trustbadge;

import org.junit.Before;
import org.junit.Test;

public class ListenerTest {

    private OnTsCustomerReviewsFetchCompleted listener;



    @Before
    public void setUp() {

        OnTsCustomerReviewsFetchCompleted testListener = new OnTsCustomerReviewsFetchCompleted() {
            @Override
            public void onCustomerReviewsFetchCompleted(Shop shopObject) {

            }

            @Override
            public void onCustomerReviewsFetchFailed(Message errorMessage) {

            }
        };

        this.listener = testListener;

    }


    @Test
    public void testListenerSetterAndGetter() {
        Trustbadge testTrustbadge = new Trustbadge("XB325209EC059B08F489A0E8B2434A05E");
        testTrustbadge.setCustomTsCustomerReviewsFetchListener(listener);
        if (!(testTrustbadge.getCustomTsCustomerReviewsFetchListener() instanceof OnTsCustomerReviewsFetchCompleted)) {
            throw new AssertionError();
        }
    }




}
