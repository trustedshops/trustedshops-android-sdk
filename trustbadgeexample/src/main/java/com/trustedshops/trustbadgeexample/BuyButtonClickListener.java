package com.trustedshops.trustbadgeexample;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.trustedshops.androidsdk.trustbadge.TrustbadgeOrder;
import com.trustedshops.trustbadgeexample.util.JsonUtil;
import com.trustedshops.trustbadgeexample.valid.DateValidator;

/**
 * Created by trumga2 on 5/9/16.
 */
public class BuyButtonClickListener implements View.OnClickListener {

    private Activity activity;

    public BuyButtonClickListener() {

    }

    public BuyButtonClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        boolean validInput = isValidInput();
        if (validInput) {
            TrustbadgeExtractor extractor = new TrustbadgeExtractor(activity);
            TrustbadgeOrder trustbadgeOrder = extractor.extractTrustbadgeOrder();

            Intent intent = new Intent(activity, CheckoutPageActivity.class);
            intent.putExtra("TrustbadgeOrder", JsonUtil.toJson(trustbadgeOrder));
            activity.startActivity(intent);
        }
    }


    private boolean isValidInput() {
        return isValidDeliveryDate();
    }

    private boolean isValidDeliveryDate() {
        String day = ((EditText) activity.findViewById(R.id.deliveryDate_day_id)).getText().toString();
        String month = ((EditText) activity.findViewById(R.id.deliveryDate_month_id)).getText().toString();
        String year = ((EditText) activity.findViewById(R.id.deliveryDate_year_id)).getText().toString();
        if ((day == null || day.length() == 0) && (month == null || month.length() == 0) && (year == null || year.length() == 0)) {
            return true;
        }
        StringBuilder dateConc = new StringBuilder();
        dateConc.append(day).append(".").append(month).append(".").append(year);
        boolean validDate = DateValidator.isValidDate(dateConc.toString());
        if (!validDate) {
            // show error message
            EditText dateBox = (EditText) activity.findViewById(R.id.deliveryDate_year_id);
            dateBox.setError("Invalid date format");
        }
        return validDate;
    }

}
