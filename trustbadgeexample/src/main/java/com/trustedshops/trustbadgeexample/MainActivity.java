package com.trustedshops.trustbadgeexample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
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

        /* @TODO -  Replace with your own Trusted Shops ID */
        Trustbadge trustbadge = new Trustbadge("XA2A8D35838AF5F63E5EB0E05847B1CB8");

        try {
            trustbadge.setClientToken("c46adf9a6880ebf033baaa5c505acae20889527dcfb3063821296ebf5fdb7684");
            trustbadge.getTrustbadge(testImageView, this);
        } catch(IllegalArgumentException exception) {
            Log.d("TSDEBUG", exception.getMessage());
        } catch (TrustbadgeException exception) {
            Log.d("TSDEBUG", exception.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void gotoSecondScreen(MenuItem item) {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
