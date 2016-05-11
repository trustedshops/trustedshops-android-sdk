package com.trustedshops.trustbadgeexample;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Spinner;

import com.trustedshops.androidsdk.trustbadge.TrustbadgeOrder;

import java.util.Arrays;
import java.util.List;

/**
 * Created by trumga2 on 5/9/16.
 */
public class TrustbadgeExtractor {
    private Activity activity;

    public TrustbadgeExtractor() {

    }

    public TrustbadgeExtractor(Activity activity) {
        this.activity = activity;
    }

    public TrustbadgeOrder extractTrustbadgeOrder() {
        TrustbadgeOrder trustbadgeOrder = new TrustbadgeOrder();
        trustbadgeOrder.setTsId(getTsId());
        trustbadgeOrder.setTsCheckoutOrderNr(getOrderNumber());
        trustbadgeOrder.setTsCheckoutBuyerEmail(getConsumerEmail());
        trustbadgeOrder.setTsCheckoutOrderAmount(getOrderAmount());
        trustbadgeOrder.setTsCheckoutOrderCurrency(getOrderCurrency());
        trustbadgeOrder.setTsCheckoutOrderPaymentType(getPaymentMethodKey());
        trustbadgeOrder.setTsCheckoutOrderEstDeliveryDate(getEstimatedDeliveryDate());

        return trustbadgeOrder;
    }

    private String getTsId() {
        return ((Spinner) activity.findViewById(R.id.tsId_id)).getSelectedItem().toString();
    }

    private String getOrderNumber() {
        return ((EditText) activity.findViewById(R.id.order_number_id)).getText().toString();
    }

    private String getConsumerEmail() {
        return ((EditText) activity.findViewById(R.id.consumer_email_id)).getText().toString();
    }

    private String getOrderAmount() {
        return ((EditText) activity.findViewById(R.id.order_amount_id)).getText().toString();
    }

    private String getOrderCurrency() {
        return ((Spinner) activity.findViewById(R.id.currency_id)).getSelectedItem().toString();
    }

    private String getPaymentMethodKey() {
        List<String> paymentMethodKeys = Arrays.asList(activity.getResources().getStringArray(R.array.payment_method_Key_list));
        int selectedPaymentPosition = ((Spinner) activity.findViewById(R.id.paymentType_id)).getSelectedItemPosition();
        return paymentMethodKeys.get(selectedPaymentPosition);
    }

    /**
     * Deliver the date as string in this format 2016-06-30
     *
     * @return
     */
    private String getEstimatedDeliveryDate() {
        String day = ((EditText) activity.findViewById(R.id.deliveryDate_day_id)).getText().toString();
        String month = ((EditText) activity.findViewById(R.id.deliveryDate_month_id)).getText().toString();
        String year = ((EditText) activity.findViewById(R.id.deliveryDate_year_id)).getText().toString();

        StringBuilder dateConc = new StringBuilder();
        dateConc.append(year).append("-").append(month).append("-").append(day);
        return dateConc.toString();
    }
}
