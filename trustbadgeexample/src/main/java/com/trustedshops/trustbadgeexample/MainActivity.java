package com.trustedshops.trustbadgeexample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.trustedshops.androidsdk.trustbadge.Trustbadge;
import com.trustedshops.androidsdk.trustbadge.TrustbadgeException;


public class MainActivity extends AppCompatActivity {

    protected Context mContext;

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
                Intent intent = new Intent(MainActivity.this, CheckoutPageActivity.class);
                startActivity(intent);
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
    }
}
