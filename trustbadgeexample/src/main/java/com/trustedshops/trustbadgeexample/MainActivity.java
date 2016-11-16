package com.trustedshops.trustbadgeexample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.trustedshops.androidsdk.trustbadge.OnTsCustomerReviewsFetchCompleted;
import com.trustedshops.androidsdk.trustbadge.Shop;
import com.trustedshops.androidsdk.trustbadge.Trustbadge;
import com.trustedshops.androidsdk.trustbadge.TrustbadgeException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fillTsIdSpinner();
        fillCurrencySpinner();
        fillPaymentMethodSpinner();
        fillOrderNumberText();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new BuyButtonClickListener(this));

        showTrustbadge();
        showReviews();
        showReviewsCustomCallback();
        addTsIdChangeListener();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.drawer_productreviews) {
            Intent intent = new Intent(this, ProductReviewsActivity.class);
            this.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void showTrustbadge() {
        ImageView testImageView = (ImageView) findViewById(R.id.trustbadgeTestImageView);
        testImageView.setImageResource(0);
        /* Please insert your Trusted Shops ID */
        String tsId = getSelectedTsId();

        Trustbadge trustbadge = new Trustbadge(tsId);
        try {
            //trustbadge.setIconColor("#F98222");
//            trustbadge.setLoggingActive(true);
            trustbadge.enableDebugmode();
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
        String tsId = getSelectedTsId();
        Trustbadge trustbadge =new Trustbadge(tsId);
        try {
            trustbadge.setLoggingActive(true);
            trustbadge.getTsCustomerReviews(this, reviewStarsBar, trustedShopReviewMark, trustedShopReviewStarsMarkDescription, null, null);
        } catch (IllegalArgumentException exception) {
            Log.d("TSDEBUG", exception.getMessage());
        } catch (TrustbadgeException exception) {
            Log.d("TSDEBUG", exception.getMessage());
        }

    }

    private void showReviewsCustomCallback() {

        final RatingBar reviewBar = (RatingBar) findViewById(R.id.reviewBadgeGroupRightRatingBar);
        final TextView reviewCountLong = (TextView) findViewById(R.id.trustedShopsReviewCount);

        Log.d("TSDEBUG", "Custom callback call");
        OnTsCustomerReviewsFetchCompleted tsCallBack = new OnTsCustomerReviewsFetchCompleted() {
            @Override
            public void onCustomerReviewsFetchCompleted(Shop shopObject) {
                Log.d("TSDEBUG", "CustomerReviews Fetch Completed");

                if (reviewBar != null && shopObject.getReviewIndicator() != null && shopObject.getReviewIndicator().getOverallMark() > 0) {
                    reviewBar.setRating((float)shopObject.getReviewIndicator().getOverallMark());
                    reviewBar.setStepSize((float)0.1);
                    reviewBar.setNumStars(shopObject.getReviewIndicator().getNumStars());
                    reviewBar.setIsIndicator(true);
                    reviewBar.setVisibility(View.VISIBLE);

                }

                if (reviewCountLong != null && shopObject.getReviewIndicator().getActiveReviewCount() > 0 ) {
                    String reviewCountLongText = MainActivity.this.getResources().getQuantityString(com.trustedshops.androidsdk.R.plurals.reviewDescriptionLong, shopObject.getReviewIndicator().getActiveReviewCount(), shopObject.getReviewIndicator().getActiveReviewCount());
                    reviewCountLong.setText(reviewCountLongText);
                    reviewCountLong.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onCustomerReviewsFetchFailed(Message errorMessage) {
                //do stuff if call failed
            }
        };

        String tsId = getSelectedTsId();
        Trustbadge trustbadge =new Trustbadge(tsId);
        try {
            trustbadge.setLoggingActive(true);
            trustbadge.getTsCustomerReviews(this, tsCallBack);
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
                showReviewsCustomCallback();
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
}
