package com.trustedshops.androidsdk.trustbadge;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.nfc.Tag;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.trustedshops.androidsdk.R;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Trustbadge {
    OkHttpClient client = new OkHttpClient();
    private String _tsId;
    private String _clientToken;
    protected ImageView _imageView;
    protected Activity _activity;
    protected final String certificateCheckAPI = "https://api-qa.trustedshops.com/rest/internal/v2/shops/%s/trustmarks.json";
    protected boolean _loggingActive = true;

    public Trustbadge() {

    }

    public Trustbadge(String tsId) {
        this._tsId = tsId;
    }

    /**
     * @param tsId - your Trusted Shops ID
     * @param clientToken - your client token
     */
    public Trustbadge(String tsId, String clientToken) {
        this._tsId = tsId;
        this._clientToken = clientToken;
    }

    /**
     * @param tsId - set your trusted shops id
     */
    public void setTsId(String tsId) {
        this._tsId = tsId;
    }

    /**
     * @return - your trusted shops id that is currently set
     */
    public String getTsId() {
        return this._tsId;
    }

    /**
     * @param clientToken - your api client tokenok
     */

    public void setClientToken(String clientToken) {
        this._clientToken = clientToken;
    }

    /**
     * @return - the clientToken that is currently set
     */
    public String getClinetToken() {
        return this._clientToken;
    }

    public void setLoggingActive(boolean loggingActive) {
        _loggingActive = loggingActive;
    }

    public boolean isLoggingActive() {
        return _loggingActive;
    }

    /**
     * @param view - ImageView to inject trustmark into
     * @throws TrustbadgeException
     * @throws IllegalArgumentException
     */
    public void getTrustbadge(ImageView view, Activity activity) throws TrustbadgeException, IllegalArgumentException {
        if (!Validator.validateTsId(getTsId())) {
            throw new IllegalArgumentException("Wrong TSID provided");
        }
        //view.setImageResource(R.drawable.ts_seal);
        _imageView = view;
        _activity = activity;
        view.setTag("Trustbadge");
        try {
            this.run();
        } catch (Exception e) {
            Log.d("TSDEBUG", e.getMessage());
            throw new TrustbadgeException("Could not verify Trustmark");
        }

    }



    protected ImageView.OnClickListener showTrustcard = new ImageView.OnClickListener() {
        public void onClick(View v) {
            // do something here
            Log.d("TSDEBUG","Cliiiick");
            boolean wrapInScrollView = true;

            MaterialDialog dialog = new MaterialDialog.Builder(_activity)
                    .customView(R.layout.trustcard, wrapInScrollView)
                    .positiveText("Sure")
                    .negativeText("Nah")
                    .build();

            try {
                Typeface test = Typeface.createFromAsset(_activity.getAssets(), "fonts/fontawesome.ttf");
                TextView trustcard_icon_1 = (TextView) dialog.getCustomView().findViewById(R.id.trustcard_icon_1);
                TextView trustcard_icon_2 = (TextView) dialog.getCustomView().findViewById(R.id.trustcard_icon_2);
                TextView trustcard_icon_3 = (TextView) dialog.getCustomView().findViewById(R.id.trustcard_icon_3);

                trustcard_icon_1.setTypeface(test);
                trustcard_icon_2.setTypeface(test);
                trustcard_icon_3.setTypeface(test);
                trustcard_icon_1.setTextColor(Color.parseColor("#18A866"));
                trustcard_icon_2.setTextColor(Color.parseColor("#18A866"));
                trustcard_icon_3.setTextColor(Color.parseColor("#18A866"));
                dialog.show();
            } catch (Exception e) {
                Log.d("TSDEBUG", e.getMessage());
            }


        }
    };


    public void run() throws Exception {


        final Request request = new Request.Builder()
                .url(String.format(certificateCheckAPI, getTsId()))
                .addHeader("client-token", getClinetToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                String jsonData = response.body().string();

                try {
                    JSONObject Jobject = new JSONObject(jsonData);
                    JSONObject responseJsonObject = Jobject.getJSONObject("response");
                    JSONObject dataJsonObject = responseJsonObject.getJSONObject("data");
                    JSONObject shopJsonObject = dataJsonObject.getJSONObject("shop");
                    JSONObject trustMarkJsonObject = shopJsonObject.getJSONObject("trustMark");
                    if (trustMarkJsonObject.getString("status").equals("VALID")) {
                        _activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Handle UI here
                                _imageView.setImageResource(R.drawable.ts_seal);
                                _imageView.setOnClickListener(showTrustcard);
                                Log.d("TSDEBUG","Trustmark Validation successfull - setting trustmark drawable into imageview");
                            }
                        });
                    }
                } catch (Exception e) {
                    if (isLoggingActive()) {
                        Log.d("TSDEBUG", "Could not parse JSON Response " + e.getMessage() + " URL: " + request.url());
                    }
                }
            }
        });
    }
}
