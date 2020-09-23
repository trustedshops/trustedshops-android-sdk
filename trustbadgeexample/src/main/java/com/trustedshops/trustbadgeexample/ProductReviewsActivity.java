package com.trustedshops.trustbadgeexample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trustedshops.androidsdk.trustbadge.OnTsProductReviewsFetchCompleted;
import com.trustedshops.androidsdk.trustbadge.Product;
import com.trustedshops.androidsdk.trustbadge.Trustbadge;
import com.trustedshops.androidsdk.trustbadge.TrustbadgeException;

public class ProductReviewsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView productReviewsList;
    private Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _context = this;
        setContentView(R.layout.activity_product_reviews);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Picasso.get().load("http://demoshop.trustedshops.com/out/pictures/master/product/1/kommode.jpg").resize(150, 110).centerCrop().into((ImageView) findViewById(R.id.productImage));
        productReviewsList = (ListView) findViewById(R.id.productReviewList);
        getProductReviewsList();
        getProductReviewsSummary();
    }


    private void getProductReviewsList() {


        Log.d("TSDEBUG", "Custom callback call");
        OnTsProductReviewsFetchCompleted tsCallBack = new OnTsProductReviewsFetchCompleted() {
            @Override
            public void onProductReviewsFetchCompleted(Product productObject) {
                Log.d("TSDEBUG", "Product Reviews Fetch Completed");
                ProductReviewsAdapter adapter = new ProductReviewsAdapter(_context, productObject.getProductReviewArrayList());
                productReviewsList.setAdapter(adapter);
            }

            @Override
            public void onProductReviewsFetchFailed(Message errorMessage) {
                //do stuff if call failed
            }
        };

        String tsId="XA2A8D35838AF5F63E5EB0E05847B1CB8";
        String SKU = "1001";
        Trustbadge trustbadge =new Trustbadge(tsId);
        try {
            trustbadge.enableDebugmode();
            trustbadge.getProductReviewsList(this, SKU, tsCallBack);
        } catch (IllegalArgumentException exception) {
            Log.d("TSDEBUG", exception.getMessage());
        } catch (TrustbadgeException exception) {
            Log.d("TSDEBUG", exception.getMessage());
        }
    }


    private void getProductReviewsSummary() {


        Log.d("TSDEBUG", "Custom callback call");
        OnTsProductReviewsFetchCompleted tsCallBack = new OnTsProductReviewsFetchCompleted() {
            @Override
            public void onProductReviewsFetchCompleted(Product productObject) {
                AppCompatRatingBar productRatingSummary = (AppCompatRatingBar) findViewById(R.id.productRatingBar);
                TextView productReviewCount = (TextView) findViewById(R.id.productReviewCount);
                TextView productMark = (TextView) findViewById(R.id.productMark);

                productRatingSummary.setRating(productObject.getReviewIndicator().getOverallMark());
                productReviewCount.setText("(" + String.valueOf(productObject.getReviewIndicator().getTotalReviewCount()) + ")");
                productMark.setText(String.format("%.02f", productObject.getReviewIndicator().getOverallMark()) + "/5.00");
            }

            @Override
            public void onProductReviewsFetchFailed(Message errorMessage) {
                //do stuff if call failed
            }
        };

        String tsId="XA2A8D35838AF5F63E5EB0E05847B1CB8";
        String SKU = "1001";
        Trustbadge trustbadge =new Trustbadge(tsId);
        try {
            trustbadge.setLoggingActive(true);
            trustbadge.enableDebugmode();
            trustbadge.getProductReviewsSummary(this, SKU, tsCallBack);
        } catch (IllegalArgumentException exception) {
            Log.d("TSDEBUG", exception.getMessage());
        } catch (TrustbadgeException exception) {
            Log.d("TSDEBUG", exception.getMessage());
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.drawer_home) {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addTsIdChangeListener() {
        Spinner spinner = (Spinner) findViewById(R.id.tsId_id);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getProductReviewsList();
                getProductReviewsSummary();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private String getSelectedTsId() {
        return ((Spinner) findViewById(R.id.tsId_id)).getSelectedItem().toString();
    }
}
