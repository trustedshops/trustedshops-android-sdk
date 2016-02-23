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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ImageView testImageView = (ImageView) findViewById(R.id.trustbadgeTestImageView);
        Trustbadge trustbadge = new Trustbadge("XA2A8D35838AF5F63E5EB0E05847B1CB8");

        try {
            //trustbadge.setIconColor("#F98222");
            //trustbadge.setLoggingActive(true);
            //trustbadge.setClientToken("");
            trustbadge.getTrustbadge(testImageView, this);

        } catch(IllegalArgumentException exception) {
            Log.d("TSDEBUG", exception.getMessage());
        } catch (TrustbadgeException exception) {
            Log.d("TSDEBUG", exception.getMessage());
        }
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
