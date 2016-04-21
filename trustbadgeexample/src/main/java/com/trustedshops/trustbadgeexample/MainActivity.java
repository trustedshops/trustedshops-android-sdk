package com.trustedshops.trustbadgeexample;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.trustedshops.androidsdk.trustbadge.Trustbadge;
import com.trustedshops.androidsdk.trustbadge.TrustbadgeException;
import com.trustedshops.androidsdk.trustbadge.TrustbadgeOrder;
import com.trustedshops.androidsdk.trustbadge.TrustedShopsCheckout;


public class MainActivity extends AppCompatActivity {

    protected Context mContext;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ImageView testImageView = (ImageView) findViewById(R.id.trustbadgeTestImageView);
        /* Please insert your Trusted Shops ID */
        Trustbadge trustbadge = new Trustbadge("X330A2E7D449E31E467D2F53A55DDD070");

        try {
            //trustbadge.setIconColor("#F98222");
            //trustbadge.setLoggingActive(true);
            trustbadge.getTrustbadge(testImageView, this);

        } catch (IllegalArgumentException exception) {
            Log.d("TSDEBUG", exception.getMessage());
        } catch (TrustbadgeException exception) {
            Log.d("TSDEBUG", exception.getMessage());
        }


        Button checkoutButton = (Button) findViewById(R.id.checkout_button);
        if (checkoutButton != null) {
            checkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TrustbadgeOrder tsCheckoutTrustbadgeOrder = new TrustbadgeOrder();
                    TrustedShopsCheckout tsCheckout = new TrustedShopsCheckout(tsCheckoutTrustbadgeOrder);
                    try {
                        tsCheckout.init(MainActivity.this);
                    } catch (TrustbadgeException exception) {
                        Log.d("TSDEBUG", "Checkout Exception: " + exception.getMessage());
                    }
                }
            });
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
