package com.trustedshops.androidsdk.trustbadge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.MDButton;
import com.trustedshops.androidsdk.R;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Trustbadge {
    OkHttpClient _client;
    private String _tsId;
    protected ImageView _imageView;
    protected Activity _activity;
    protected boolean _loggingActive = false;
    protected int _iconColor;
    protected Shop _shop;
    protected String _endPoint = "cdn1.api.trustedshops.com";
    protected RatingBar _ratingBar;
    protected Shop _shopWithQualityIndicators;
    protected TextView _reviewMark, _reviewMarkDescription, _reviewCount, _reviewCountLong;
    private OnTsCustomerReviewsFetchCompleted listener;
    static final Set<String> acceptLanguages = new HashSet<String>(Arrays.asList("de", "fr","es","pl","en"));
    private String _acceptLanguage = "en";
    public Trustbadge() {
    }


    /**
     * @param tsId - your Trusted Shops ID
     */
    public Trustbadge(String tsId) {
        this._tsId = tsId;
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

    public void setActivity(Activity activity) {
        _activity = activity;
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
            this.callTrustmarkInformationApi();
        } catch (Exception e) {
            if (isLoggingActive()) {
                Log.d("TSDEBUG", e.getMessage());
            }
            throw new TrustbadgeException("Could not verify Trustmark");
        }
    }


    /**
     * Set Custom Listener for TS Customer Reviews
     * @param listener
     */
    public void setCustomTsCustomerReviewsFetchListener(OnTsCustomerReviewsFetchCompleted listener) {
            this.listener = listener;

        if (isLoggingActive()) {
            Log.d("TSDEBUG", "Setting listener " + listener.hashCode());
        }
    }

    /**
     * Return the listener that was set on TS Customer Reviews Fetch Complete
     * @return OnTsCustomerReviewsFetchCompleted listener
     */
    public OnTsCustomerReviewsFetchCompleted getCustomTsCustomerReviewsFetchListener() {
        return this.listener;
    }


    /**
     *
     * @param activity - Current activity
     * @param ratingBar - RatingBar instance to set the rating to
     * @param reviewMark - TextView where to set the review mark
     * @param reviewMarkDescription - TextView where to set the review mark description
     * @param reviewCount - TextView where to set the review count
     * @param reviewCountLong - TextView where to set the review count in long format
     * @throws TrustbadgeException
     * @throws IllegalArgumentException
     */
    public void getTsCustomerReviews(Activity activity, RatingBar ratingBar, TextView reviewMark, TextView reviewMarkDescription, TextView reviewCount, TextView reviewCountLong) throws TrustbadgeException, IllegalArgumentException {
        _activity = activity;
        _ratingBar = ratingBar;
        _reviewMark = reviewMark;
        _reviewCount = reviewCount;
        _reviewCountLong = reviewCountLong;
        _reviewMarkDescription = reviewMarkDescription;

        OnTsCustomerReviewsFetchCompleted tsCustomerReviesFetchCompletedListener = new OnTsCustomerReviewsFetchCompleted() {
            @Override
            public void onCustomerReviewsFetchCompleted(Shop shopObject) {
                populateElementsWithQualityIndicatorsValues(shopObject);
            }

            @Override
            public void onCustomerReviewsFetchFailed(Message errorMessage) {
                if (isLoggingActive()){
                    Log.d("TSDEBUG", "Customer Reviews Fetch Failed " + errorMessage.obj.toString());
                }
            }
        };

        this.setCustomTsCustomerReviewsFetchListener(tsCustomerReviesFetchCompletedListener);

        try {
            this.callQualityIndicatorsApi(UrlManager.getQualityIndicatorsApiUrl(getTsId(), getEndPoint()));
        } catch (Exception e) {
            if (isLoggingActive()) {
                Log.d("TSDEBUG", e.getMessage());
            }
            throw new TrustbadgeException("Could not find reviews for shop");
        }
    }


    public void getTsCustomerReviews(Activity activity, OnTsCustomerReviewsFetchCompleted tsCustomerReviesFetchCompletedListener) throws TrustbadgeException, IllegalArgumentException {

        _activity = activity;
        this.setCustomTsCustomerReviewsFetchListener(tsCustomerReviesFetchCompletedListener);

        try {
            this.callQualityIndicatorsApi(UrlManager.getQualityIndicatorsApiUrl(getTsId(), getEndPoint()));
        } catch (Exception e) {
            if (isLoggingActive()) {
                Log.d("TSDEBUG", e.getMessage());
            }
            throw new TrustbadgeException("Could not find reviews for shop");
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


    protected View.OnTouchListener showShopProfile = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (_shopWithQualityIndicators.getTargetMarketISO3()!=null && _shopWithQualityIndicators.getLanguageISO2() != null) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(UrlManager.getShopProfileUrl(_shopWithQualityIndicators)));
                _activity.startActivity(browserIntent);
            }
            return false;
        }
    };
    /**
     * API CALL Async
     * @throws Exception
     */

    protected void callTrustmarkInformationApi() throws Exception {
        final Request request = new Request.Builder()
                .url(UrlManager.getTrustMarkAPIUrl(getTsId(), getEndPoint()))
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

                try {
                    Shop parsedShopObject = Shop.createFromTrustmarkInformationApi(jsonData);
                    if (parsedShopObject.getTrustMark()!= null && parsedShopObject.getTrustMark().getStatus().equals("VALID")) {
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
                } catch (Exception e) {
                    if (isLoggingActive()) {
                        Log.d("TSDEBUG", "Could not parse api response. " + e.getMessage());
                    }
                }
            }
        });
    }


    protected void populateElementsWithQualityIndicatorsValues(Shop parsedShopObject){


        if (_ratingBar != null && parsedShopObject.getReviewIndicator() != null && parsedShopObject.getReviewIndicator().getOverallMark() > 0) {
            _ratingBar.setRating((float)parsedShopObject.getReviewIndicator().getOverallMark());
            _ratingBar.setOnTouchListener(showShopProfile);
            _ratingBar.setStepSize((float)0.1);
            _ratingBar.setNumStars(parsedShopObject.getReviewIndicator().getNumStars());
            _ratingBar.setIsIndicator(true);
            _ratingBar.setVisibility(View.VISIBLE);
            if (isLoggingActive()) {
                Log.d("TSDEBUG", "Setting rating bar rating value to " + String.valueOf(parsedShopObject.getReviewIndicator().getOverallMark()));
            }
        }


        if (_reviewMark != null && parsedShopObject.getReviewIndicator().getOverallMark() != null && parsedShopObject.getReviewIndicator().getOverallMark() > 0 ) {
            _reviewMark.setText(String.valueOf(parsedShopObject.getReviewIndicator().getOverallMark()) + "/5.00");
            _reviewMark.setText(Html.fromHtml(String.format(Locale.ENGLISH, "%.2f", parsedShopObject.getReviewIndicator().getOverallMark() )+"<font color=#878780>/5.00</font>"));
            _reviewMark.setVisibility(View.VISIBLE);
            if (isLoggingActive()) {
                Log.d("TSDEBUG", "Setting review mark value to " + String.valueOf(parsedShopObject.getReviewIndicator().getOverallMark()));
            }
        }

        if (_reviewMarkDescription != null && parsedShopObject.getReviewIndicator().getOverallMarkDescription() != null ) {
            _reviewMarkDescription.setText(parsedShopObject.getReviewIndicator().getOverallMarkDescription());
            _reviewMarkDescription.setVisibility(View.VISIBLE);
            if (isLoggingActive()) {
                Log.d("TSDEBUG", "Setting review mark description to " + parsedShopObject.getReviewIndicator().getOverallMarkDescription());
            }
        }

        if (_reviewCount != null && parsedShopObject.getReviewIndicator().getActiveReviewCount() > 0 ) {
            _reviewCount.setText(String.valueOf(parsedShopObject.getReviewIndicator().getActiveReviewCount()));
            _reviewCount.setVisibility(View.VISIBLE);
            if (isLoggingActive()) {
                Log.d("TSDEBUG", "Setting review count " + String.valueOf(parsedShopObject.getReviewIndicator().getActiveReviewCount()));
            }
        }

        if (_reviewCountLong != null && parsedShopObject.getReviewIndicator().getActiveReviewCount() > 0 ) {
            String reviewCountLongText = _activity.getResources().getQuantityString(R.plurals.reviewDescriptionLong, parsedShopObject.getReviewIndicator().getActiveReviewCount(), parsedShopObject.getReviewIndicator().getActiveReviewCount());
            _reviewCountLong.setText(reviewCountLongText);
            _reviewCountLong.setVisibility(View.VISIBLE);
            if (isLoggingActive()) {
                Log.d("TSDEBUG", "Setting review count long text to " + reviewCountLongText);
            }

        }

    }

    private void runOnUiThread(Activity _activity, Runnable _runnable) {

            _activity.runOnUiThread(_runnable);
    }

    protected void callQualityIndicatorsApi(String qualityIndicatorsApiUrl) throws Exception {


        /* If we already did the api call and parsed data, do not do it again */
        if (_shopWithQualityIndicators != null && listener != null) {
            runOnUiThread(_activity, new Runnable() {
                @Override
                public void run() {
                    //Handle UI here
                    listener.onCustomerReviewsFetchCompleted(_shopWithQualityIndicators);
                    if (isLoggingActive()) {
                        Log.d("TSDEBUG", "Got quality indicators data from cache - running the callback");
                    }
                }}
            );

        }


        if (isLoggingActive()) {
            Log.d("TSDEBUG", "Calling Quality Indicators API URL: " + qualityIndicatorsApiUrl);
        }

        String currentLanguage = Locale.getDefault().getLanguage();

        if (acceptLanguages.contains(currentLanguage)) {
            _acceptLanguage = currentLanguage;
        }

        final Request request = new Request.Builder()
                .url(qualityIndicatorsApiUrl)
                .addHeader("accept-language", _acceptLanguage)
                .build();


        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        File cacheDirectory = new File(_activity.getCacheDir().getAbsolutePath(), "HttpCacheQualityIndicatorApi");
        Cache cache = new Cache(cacheDirectory, cacheSize);
        _client = new OkHttpClient.Builder()
                .cache(cache)
                .build();


        _client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {

                if (listener != null) {
                    Message errorMessage = Message.obtain();
                    String message = e.getMessage();
                    errorMessage.obj = message;
                    listener.onCustomerReviewsFetchFailed(errorMessage);
                }

                if (isLoggingActive()) {
                    Log.d("TSDEBUG", e.getMessage());
                }
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful())  {
                    if (isLoggingActive()) {
                        Log.d("TSDEBUG", "Quality Indicators call unsuccessfull " + response);
                    }
                }
                try {
                    String jsonData = response.body().string();
                    final Shop parsedShopObject = Shop.createFromQualityIndicatorsApiResponse(jsonData);
                    if (parsedShopObject.getReviewIndicator()!= null && parsedShopObject.getReviewIndicator().getActiveReviewCount() > 0) {
                        _shopWithQualityIndicators = parsedShopObject;
                        runOnUiThread(_activity, new Runnable() {
                            @Override
                            public void run() {
                                //Handle UI here
                                if (listener != null) {
                                    listener.onCustomerReviewsFetchCompleted(_shopWithQualityIndicators);
                                }

                                if (isLoggingActive()) {
                                    Log.d("TSDEBUG", "Quality Indicators call done");
                                }
                            }});
                    } else {
                        if (isLoggingActive()) {
                            Log.d("TSDEBUG", "No reviews found for this shop");
                        }
                    }

                } catch (Exception e) {
                    if (isLoggingActive()) {
                        Log.d("TSDEBUG", "Could not parse JSON Response " + e.getMessage());
                    }
                }
            }
        });
    }
}
