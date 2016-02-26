package com.trustedshops.androidsdk.trustbadge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.MDButton;
import com.trustedshops.androidsdk.R;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Trustbadge {
    OkHttpClient _client;
    private String _tsId;
    private String _clientToken;
    protected ImageView _imageView;
    protected Activity _activity;
    protected boolean _loggingActive = false;
    protected int _iconColor;
    protected Shop _shop;
    protected String _endPoint = "api.trustedshops.com";


    public Trustbadge() {
    }

    /**
     * @param tsId
     */
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

    /**
     *
     * @param loggingActive boolean
     */
    public void setLoggingActive(boolean loggingActive) {
        this._loggingActive = loggingActive;
    }

    /**
     *
     * @param iconColor String
     */
    public void setIconColor(String iconColor) {
        this._iconColor = Color.parseColor(iconColor);

    }

    /**
     * @return Integer Icon Color
     */
    public int getIconColor() {
        return _iconColor;

    }

    /**
     *
     * @param endPoint for API to use
     */
    public void setEndPoint(String endPoint) {
        _endPoint = endPoint;
    }

    /**
     *
     * @return String endPoint
     */
    public String getEndPoint() {
        return _endPoint;
    }

    /**
     * @param shop Shop
     */
    public void setShop(Shop shop) {
        _shop = shop;
    }

    /**
     * @return loggingActive boolean
     */
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

        if (getClinetToken() == null) {
            throw new IllegalArgumentException("Client Token is Missing");
        }
        //view.setImageResource(R.drawable.ts_seal);
        _imageView = view;
        _activity = activity;
        view.setTag("Trustbadge");
        try {
            this.run();
        } catch (Exception e) {
            if (isLoggingActive()) {
                Log.d("TSDEBUG", e.getMessage());
            }
            throw new TrustbadgeException("Could not verify Trustmark");
        }
    }


    /**
     * Trustcard onClick Listener
     */
    protected ImageView.OnClickListener showTrustcard = new ImageView.OnClickListener() {
        public void onClick(View v) {
            boolean wrapInScrollView = true;
            MaterialDialog dialog = new MaterialDialog.Builder(_activity)
                    .customView(R.layout.trustcard, wrapInScrollView)
                    .positiveText(android.R.string.ok)
                    .negativeText(R.string.trustedshops_sdk_trustbadge_dialog_negative_button_text)
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(UrlManager.getShopProfileUrl(_shop)));
                            _activity.startActivity(browserIntent);
                        }
                    })
                    .positiveColor(getIconColor())
                    .negativeColor(getIconColor())
                    .build();
            try {

                TextView trustcard_icon_1 = (TextView) dialog.getCustomView().findViewById(R.id.trustcard_icon_1);
                TextView trustcard_icon_2 = (TextView) dialog.getCustomView().findViewById(R.id.trustcard_icon_2);
                TextView trustcard_icon_3 = (TextView) dialog.getCustomView().findViewById(R.id.trustcard_icon_3);
                if (getIconColor() != 0) {
                    trustcard_icon_1.setTextColor(getIconColor());
                    trustcard_icon_2.setTextColor(getIconColor());
                    trustcard_icon_3.setTextColor(getIconColor());
                    MDButton negative = dialog.getActionButton(DialogAction.NEUTRAL);
                    negative.setTextColor(getIconColor());

                } else {
                    Log.d("TSDEBUG","NOT SETTING COLOR " + getIconColor());
                }

                dialog.show();
            } catch (Exception e) {
                if (isLoggingActive()) {
                    Log.d("TSDEBUG", e.getMessage());
                }
            }


        }
    };


    /**
     * API CALL Async
     * @throws Exception
     */

    public void run() throws Exception {
        final Request request = new Request.Builder()
                .url(UrlManager.getTrustMarkAPIUrl(getTsId(), getEndPoint()))
                .addHeader("client-token", getClinetToken())
                .build();

        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        File cacheDirectory = new File(_activity.getCacheDir().getAbsolutePath(), "HttpCache");
        Cache cache = new Cache(cacheDirectory, cacheSize);
        _client = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        _client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                if (isLoggingActive()) {
                    Log.d("TSDEBUG", e.getMessage());
                }
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful())  {
                    if (isLoggingActive()) {
                        Log.d("TSDEBUG", "Trustmark API call unsuccessfull " + response);
                    }
                }
                String jsonData = response.body().string();
                Shop parsedShopObject = parseApiResponse(jsonData);
                if (parsedShopObject != null && parsedShopObject.getTrustMark()!= null && parsedShopObject.getTrustMark().getStatus().equals("VALID")) {
                    setShop(parsedShopObject);
                    _activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Handle UI here
                            _imageView.setImageResource(R.drawable.ts_seal);
                            _imageView.setOnClickListener(showTrustcard);
                            if (isLoggingActive()) {
                                Log.d("TSDEBUG", "Trustmark Validation successfull - setting trustmark drawable into imageview");
                            }
                        }
                    });

                } else {
                    if (isLoggingActive()) {
                        Log.d("TSDEBUG", "No valid certificate found");
                    }
                }
            }
        });
    }

    /**
     * @param jsonData
     * @return Parsed API Response
     */
    protected Shop parseApiResponse(String jsonData) {
        Shop s = null;

        try {
            Shop _responseShop = new Shop();
            JSONObject Jobject = new JSONObject(jsonData);
            JSONObject responseJsonObject = Jobject.getJSONObject("response");
            JSONObject dataJsonObject = responseJsonObject.getJSONObject("data");
            JSONObject shopJsonObject = dataJsonObject.getJSONObject("shop");

            _responseShop.setLanguageISO2(shopJsonObject.getString("languageISO2"));
            _responseShop.setTargetMarketISO3(shopJsonObject.getString("targetMarketISO3"));
            _responseShop.setUrl(shopJsonObject.getString("url"));
            _responseShop.setTsId(shopJsonObject.getString("tsId"));
            _responseShop.setName(shopJsonObject.getString("name"));

            TrustMark _responseTrustMark = new TrustMark();

            if (shopJsonObject.has("trustMark")) {
                JSONObject trustMarkJsonObject = shopJsonObject.getJSONObject("trustMark");

                if (trustMarkJsonObject.has("status")) {
                    _responseTrustMark.setStatus(trustMarkJsonObject.getString("status"));

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                    if (trustMarkJsonObject.has("validFrom")) {
                        _responseTrustMark.setValidFrom(format.parse(trustMarkJsonObject.getString("validFrom")));
                    }

                    if (trustMarkJsonObject.has("validTo")) {
                        _responseTrustMark.setValidTo(format.parse(trustMarkJsonObject.getString("validTo")));
                    }
                }
                _responseShop.setTrustMark(_responseTrustMark);
            }

            return _responseShop;

        } catch (Exception e) {
            if (isLoggingActive()) {
                Log.d("TSDEBUG", "Could not parse JSON Response " + e.getMessage());
            }
        }

        return s;
    }
}
