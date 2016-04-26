package com.trustedshops.trustbadgeexample;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.trustedshops.androidsdk.trustbadge.Product;
import com.trustedshops.androidsdk.trustbadge.TrustbadgeException;
import com.trustedshops.androidsdk.trustbadge.TrustbadgeOrder;
import com.trustedshops.androidsdk.trustbadge.TrustedShopsCheckout;

import java.util.Random;

public class CheckoutPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_page);

        TrustbadgeOrder tsCheckoutTrustbadgeOrder = new TrustbadgeOrder();

        /* Set your Trusted Shops ID here */
        tsCheckoutTrustbadgeOrder.setTsId("X330A2E7D449E31E467D2F53A55DDD070");

        //@TODO - remove random order id generation after testing
        int orderNumber = new Random().nextInt((3000 - 1) + 1) + 1;
        String checkoutOrderNumber = "SDKTEST1000" + orderNumber;

        tsCheckoutTrustbadgeOrder.setTsCheckoutOrderNr(checkoutOrderNumber);
        tsCheckoutTrustbadgeOrder.setTsCheckoutBuyerEmail("tester@example.com");
        tsCheckoutTrustbadgeOrder.setTsCheckoutOrderAmount("150");
        tsCheckoutTrustbadgeOrder.setTsCheckoutOrderCurrency("EUR");
        tsCheckoutTrustbadgeOrder.setTsCheckoutOrderPaymentType("PAYPAL");

        Product checkoutProduct1 = new Product();
        checkoutProduct1.setTsCheckoutProductName("Brother TN-241C");
        checkoutProduct1.setTsCheckoutProductSKU("4123123");
        checkoutProduct1.setTsCheckoutProductUrl("http://www.brother.de/verbrauchsmaterial/laser/toner/tn/tn241c");
        checkoutProduct1.setTsCheckoutProductBrand("Brother");
        checkoutProduct1.setTsCheckoutProductGTIN("4977766718400");
        checkoutProduct1.setTsCheckoutProductImageUrl("http://www.brother.de/~/media/Product%20Images/Supplies/Laser/Toner/TN/TN241C/TN241C_main.png");
        checkoutProduct1.setTsCheckoutProductMPN("TN241C");

        tsCheckoutTrustbadgeOrder.addCheckoutProductItem(checkoutProduct1);


        Product checkoutProduct2 = new Product();
        checkoutProduct2.setTsCheckoutProductName("Brother TN-261C");
        checkoutProduct2.setTsCheckoutProductSKU("41231661");
        checkoutProduct2.setTsCheckoutProductUrl("http://www.brother.de/verbrauchsmaterial/laser/toner/tn/tn241c");
        tsCheckoutTrustbadgeOrder.addCheckoutProductItem(checkoutProduct2);

        TrustedShopsCheckout tsCheckout = new TrustedShopsCheckout(tsCheckoutTrustbadgeOrder);


        /* Add callback for dialog dismiss */
        Handler.Callback dialogClosedCallback = new Handler.Callback() {
            @Override
            public boolean handleMessage(Message callBackResponse) {

                switch (callBackResponse.what) {
                    case TrustedShopsCheckout._dismissCallNumber:
                        //Card closed
                        Log.d("TSDEBUG","Case 1 called");

                        break;
                    case TrustedShopsCheckout._errorCallNumber:
                        //Failure
                        Log.d("TSDEBUG","Case 2 called");
                        break;
                }
                return true;
            }
        };

        try {

            /*
            @TODO - remove this before going live!
             */
            tsCheckout.enableDebugmode();
            tsCheckout.init(CheckoutPageActivity.this, dialogClosedCallback);
        } catch (TrustbadgeException exception) {
            Log.d("TSDEBUG", "Checkout Exception: " + exception.getMessage());
        }
    }
}
