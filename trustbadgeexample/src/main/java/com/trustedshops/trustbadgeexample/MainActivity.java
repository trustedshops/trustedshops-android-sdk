package com.trustedshops.trustbadgeexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.trustedshops.androidsdk.trustbadge.Trustbadge;
import com.trustedshops.androidsdk.trustbadge.TrustbadgeException;

import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fillTsIdSpinner();
        fillCurrencySpinner();
        fillPaymentMethodSpinner();
        fillOrderNumberText();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new BuyButtonClickListener(this));

        showTrustbadge();
        showReviews();
        addTsIdChangeListener();
    }

    private void showTrustbadge() {
        ImageView testImageView = (ImageView) findViewById(R.id.trustbadgeTestImageView);
        testImageView.setImageResource(0);
        /* Please insert your Trusted Shops ID */
        String tsId = getSelectedTsId();

        Trustbadge trustbadge = new Trustbadge(tsId);
        try {
            //trustbadge.setIconColor("#F98222");
            trustbadge.setLoggingActive(true);
            trustbadge.getTrustbadge(testImageView, this);
        } catch (IllegalArgumentException exception) {
            Log.d("TSDEBUG", exception.getMessage());
        } catch (TrustbadgeException exception) {
            Log.d("TSDEBUG", exception.getMessage());
        }
    }

    private void showReviews() {
        RatingBar reviewStarsBar = (RatingBar) findViewById(R.id.trustedShopReviewStars);
        TextView trustedShopReviewStarsMarkDescription = (TextView) findViewById(R.id.trustedShopReviewStarsMarkDescription);
        TextView trustedShopReviewMark = (TextView) findViewById(R.id.trustedShopReviewMark);
        TextView trustedShopReviewCountLong = (TextView) findViewById(R.id.trustedShopsReviewCountLong);
        TextView trustedShopsReviewCount = (TextView) findViewById(R.id.trustedShopsReviewCount);
        String tsId = getSelectedTsId();
        Trustbadge trustbadge =new Trustbadge(tsId);
        try {
            //trustbadge.setIconColor("#F98222");
            trustbadge.setLoggingActive(true);
            trustbadge.getTsCustomerReviews(this, reviewStarsBar, trustedShopReviewMark, trustedShopReviewStarsMarkDescription, trustedShopsReviewCount, trustedShopReviewCountLong);
        } catch (IllegalArgumentException exception) {
            Log.d("TSDEBUG", exception.getMessage());
        } catch (TrustbadgeException exception) {
            Log.d("TSDEBUG", exception.getMessage());
        }

    }

    private void addTsIdChangeListener() {
        Spinner spinner = (Spinner) findViewById(R.id.tsId_id);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showTrustbadge();
                showReviews();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private String getSelectedTsId() {
        return ((Spinner) findViewById(R.id.tsId_id)).getSelectedItem().toString();
    }

    private void fillOrderNumberText() {
        ((EditText) findViewById(R.id.order_number_id)).setText("SDK-" + Long.toHexString(Double.doubleToLongBits(Math.random())));
    }

    private void fillTsIdSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.tsId_id);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tsId_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void fillCurrencySpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.currency_id);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void fillPaymentMethodSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.paymentType_id);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.payment_method_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

//    private PaymentMethodData[] getDefaultPaymentMethodData() {
//        List<String> paymentMethods = Arrays.asList(getResources().getStringArray(R.array.payment_method_Key_list));
//
//
//        final PaymentMethodData items[] = new PaymentMethodData[19];
//        items[0] = new PaymentMethodData( "key1","value1" );
//        items[1] = new PaymentMethodData( "key2","value2" );
//        items[2] = new PaymentMethodData( "key3","value3" );
//    }
}
