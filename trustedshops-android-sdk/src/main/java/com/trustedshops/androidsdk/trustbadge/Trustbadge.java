package com.trustedshops.androidsdk.trustbadge;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
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
    protected boolean _debugActive = false;
    protected int _iconColor;
    protected Shop _shop;
    private String _endPoint = "cdn1.api.trustedshops.com";
    private String _endPointDebug = "cdn1.api-qa.trustedshops.com";
    private String _trustcardEndpoint = "widgets.trustedshops.com";
    private String _trustcardEndpointDebug = "widgets-qa.trustedshops.com";
    protected RatingBar _ratingBar;
    protected Shop _shopWithQualityIndicators;
    protected TextView _reviewMark, _reviewMarkDescription, _reviewCount, _reviewCountLong;
    protected Product _productWithReviewsList;
    protected Product _productWithQuaityIndicators;
    protected String _productSKU;
    private WebView _webView;
    private boolean _alreadyInjected = false;

    private OnTsCustomerReviewsFetchCompleted listener;
    private OnTsProductReviewsFetchCompleted productReviewsListener;
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
     * @param debugActive boolean
     */
    public void setDebugActive(boolean debugActive) { this._debugActive = debugActive; }

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
     * @return String endPoint
     */
    public String getEndPoint() {
        return _debugActive ? _endPointDebug : _endPoint;
    }

    /**
     *
     * @return String trustcardEndpoint
     */
    public String getTrustcardEndpoint() {
        return _debugActive ? _trustcardEndpointDebug: _trustcardEndpoint;
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
     * @return debugActive boolean
     */
    public boolean isDebugActive() {
        return _debugActive;
    }

    public void setActivity(Activity activity) {
        _activity = activity;
    }

    public void setProductSKU(String sku) {
        this._productSKU = sku;
    }

    public String getProductSKU() {
        return this._productSKU;
    }


    public void enableDebugmode() {
        this.setLoggingActive(true);
        this.setDebugActive(true);
    }

    public void disableDebugmode() {
        this.setLoggingActive(false);
        this.setDebugActive(false);
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
     * Return the listener that was set on TS Product Reviews Fetch Complete
     * @return OnTsCustomerReviewsFetchCompleted listener
     */
    public OnTsProductReviewsFetchCompleted getCustomTsProductReviewsFetchListener() {
        return this.productReviewsListener;
    }



    /**
     * Set Custom Listener for TS Product Reviews
     * @param listener
     */
    public void setCustomTsProductReviewsFetchListener(OnTsProductReviewsFetchCompleted listener) {
        this.productReviewsListener = listener;

        if (isLoggingActive()) {
            Log.d("TSDEBUG", "Setting product reviews listener " + listener.hashCode());
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
     * Get Product Reviews List
     * @param activity
     * @param SKU
     * @param tsProductReviesFetchCompletedListener
     * @throws TrustbadgeException
     * @throws IllegalArgumentException
     */

    public void getProductReviewsList(Activity activity, String SKU, OnTsProductReviewsFetchCompleted tsProductReviesFetchCompletedListener) throws TrustbadgeException, IllegalArgumentException {

        _activity = activity;
        this.setProductSKU(SKU);
        this.setCustomTsProductReviewsFetchListener(tsProductReviesFetchCompletedListener);

        try {
            this.callProductReviewsListApi(getTsId(), getProductSKU());
        } catch (Exception e) {
            if (isLoggingActive()) {
                Log.d("TSDEBUG", e.getMessage());
            }
            throw new TrustbadgeException("Could not find reviews for product");
        }
    }


    /**
     * Get Product Reviews Summary
     * @param activity
     * @param SKU
     * @param tsProductReviesFetchCompletedListener
     * @throws TrustbadgeException
     * @throws IllegalArgumentException
     */
    public void getProductReviewsSummary(Activity activity, String SKU, OnTsProductReviewsFetchCompleted tsProductReviesFetchCompletedListener) throws TrustbadgeException, IllegalArgumentException {

        _activity = activity;
        this.setProductSKU(SKU);
        this.setCustomTsProductReviewsFetchListener(tsProductReviesFetchCompletedListener);

        try {
            this.callProductReviewsSummaryApi(getTsId(), getProductSKU());
        } catch (Exception e) {
            if (isLoggingActive()) {
                Log.d("TSDEBUG", e.getMessage());
            }
            throw new TrustbadgeException("Could not find reviews for product");
        }
    }


    /**
     * Trustcard onClick Listener
     */
    protected ImageView.OnClickListener showTrustcard = new ImageView.OnClickListener() {
        public void onClick(View v) {
            _alreadyInjected = false;
            _webView = new WebView(_activity);
            _webView.clearCache(true);


            _webView.getSettings().setJavaScriptEnabled(true);
            _webView.getSettings().setDisplayZoomControls(false);
            _webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            _webView.setBackgroundColor(Color.parseColor("#FFDC0F"));
            _webView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return (event.getAction() == MotionEvent.ACTION_MOVE);
                }
            });

            if (isDebugActive()) {
                _webView.setWebContentsDebuggingEnabled(true);
            }

            final MaterialDialog dialog = new MaterialDialog.Builder(_activity)
                    .customView(_webView, false)
                    .dismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if (_webView != null) {
                                _webView.loadUrl("about:blank");
                                _webView = null;
                            }
                        }
                    })
                    .backgroundColor(Color.parseColor("#FFDC0F"))
                    .build();
            Handler.Callback resizeCallback = new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    final float scale = _activity.getResources().getDisplayMetrics().density;
                    final int width = (int)((float) msg.arg1 * scale);
                    final int height = (int)((float)msg.arg2 * scale);
                    _activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (_webView == null) {return;}
                            int oldWidth = _webView.getWidth();
                            int oldHeight = _webView.getHeight();
                            if ((oldWidth != width || oldHeight != height) && width != 0 && height != 0) {
                                View containingView = dialog.getWindow().getDecorView();
                                int offsetW = containingView.getPaddingLeft() + containingView.getPaddingRight();
                                int offsetH = containingView.getPaddingTop() + containingView.getPaddingBottom();
                                dialog.getWindow().setLayout(width + offsetW, height + offsetH);
                                ViewGroup.LayoutParams params = _webView.getLayoutParams();
                                params.width = width;
                                params.height = height;
                                _webView.setLayoutParams(params);
                                _webView.forceLayout();
                                if (!dialog.isShowing()) {
                                    dialog.show();
                                }
                            }
                        }
                    });
                    return true; //unused return value
                }
            };
            JsInterface jsInterface = new JsInterface(dialog, resizeCallback);
            _webView.addJavascriptInterface(jsInterface, "jsInterface");
            _webView.loadUrl("file:///android_asset/trustcard_page.html");
            _webView.setWebChromeClient(new WebChromeClient(){
                public void onProgressChanged(WebView view, int newProgress) {
                    if (newProgress == 100 && !_alreadyInjected) {

                        // TODO: finish js injection and such
                        view.loadUrl("javascript:injectTrustbadge('"+ getTsId() +"', '"+ getTrustcardEndpoint() +"')");

                        if (isLoggingActive()) {
                            Log.d("TSDEBUG","Page loaded");
                        }
                        _alreadyInjected = true;
                    }
                }
            });
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
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
                    Shop parsedShopObject = Shop.createFromQualityIndicatorsApiResponse(jsonData);

                    parsedShopObject.getReviewIndicator().setOverallMarkDescription(_activity.getResources().getString(parsedShopObject.getReviewIndicator().getTranslatedRatingMark(parsedShopObject.getReviewIndicator().getOverallMarkDescription())).toUpperCase());

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

    protected void callProductReviewsListApi(String tsId, String SKU) throws Exception {


        /* If we already did the api call and parsed data, do not do it again */
        if (_productWithReviewsList != null && listener != null) {
            runOnUiThread(_activity, new Runnable() {
                @Override
                public void run() {
                    //Handle UI here
                    productReviewsListener.onProductReviewsFetchCompleted(_productWithReviewsList);
                    if (isLoggingActive()) {
                        Log.d("TSDEBUG", "Got product with reviews list from cache - running the callback");
                    }
                }}
            );

        }

        final String productReviewsListApi = UrlManager.getProductReviewsListApiUrl(getEndPoint(), tsId, TextUtil.encodeHex(SKU.getBytes()));

        final Request request = new Request.Builder()
                .url(productReviewsListApi)
//                .addHeader("accept-language", _acceptLanguage)
                .build();

        if (isLoggingActive()) {
            Log.d("TSDEBUG", "Calling Product Reviews List API URL: " + productReviewsListApi);
        }

        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        File cacheDirectory = new File(_activity.getCacheDir().getAbsolutePath(), "HttpCacheQualityIndicatorApi");
        Cache cache = new Cache(cacheDirectory, cacheSize);
        _client = new OkHttpClient.Builder()
                .cache(cache)
                .build();


        _client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {

                if (productReviewsListener != null) {
                    Message errorMessage = Message.obtain();
                    String message = e.getMessage();
                    errorMessage.obj = message;
                    productReviewsListener.onProductReviewsFetchFailed(errorMessage);
                }

                if (isLoggingActive()) {
                    Log.d("TSDEBUG", e.getMessage());
                }
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful())  {
                    if (isLoggingActive()) {
                        Log.d("TSDEBUG", "Product Reviews list call unsuccessfull " + response);
                    }
                }
                try {
                    String jsonData = response.body().string();
                    final Product parsedProductObject = Product.createFromReviewsListApi(jsonData);


                    if (parsedProductObject.getProductReviewArrayList()!= null && parsedProductObject.getProductReviewArrayList().size() > 0) {
                        _productWithReviewsList = parsedProductObject;
                        runOnUiThread(_activity, new Runnable() {
                            @Override
                            public void run() {
                                //Handle UI here
                                if (productReviewsListener != null) {
                                    productReviewsListener.onProductReviewsFetchCompleted(_productWithReviewsList);
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

    protected void callProductReviewsSummaryApi(String tsId, String SKU) throws Exception {


        /* If we already did the api call and parsed data, do not do it again */
        if (_productWithQuaityIndicators != null && listener != null) {
            runOnUiThread(_activity, new Runnable() {
                @Override
                public void run() {
                    //Handle UI here
                    productReviewsListener.onProductReviewsFetchCompleted(_productWithQuaityIndicators);
                    if (isLoggingActive()) {
                        Log.d("TSDEBUG", "Got product with reviews list from cache - running the callback");
                    }
                }}
            );

        }





        final String productReviewsSummaryApi = UrlManager.getProductSummaryApiUrl(getEndPoint(), tsId, TextUtil.encodeHex(SKU.getBytes()));

        final Request request = new Request.Builder()
                .url(productReviewsSummaryApi)
//                .addHeader("accept-language", _acceptLanguage)
                .build();
        if (isLoggingActive()) {
            Log.d("TSDEBUG", "Calling Product Reviews Summary API URL: " + productReviewsSummaryApi);
        }

        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        File cacheDirectory = new File(_activity.getCacheDir().getAbsolutePath(), "HttpCacheQualityIndicatorApi");
        Cache cache = new Cache(cacheDirectory, cacheSize);
        _client = new OkHttpClient.Builder()
                .cache(cache)
                .build();


        _client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {

                if (productReviewsListener != null) {
                    Message errorMessage = Message.obtain();
                    String message = e.getMessage();
                    errorMessage.obj = message;
                    productReviewsListener.onProductReviewsFetchFailed(errorMessage);
                }

                if (isLoggingActive()) {
                    Log.d("TSDEBUG", e.getMessage());
                }
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful())  {
                    if (isLoggingActive()) {
                        Log.d("TSDEBUG", "Product Reviews Summary call unsuccessful " + response);
                    }
                }
                try {
                    String jsonData = response.body().string();
                    final Product parsedProductObject = Product.createFromSummaryApi(jsonData);


                    if (parsedProductObject.getReviewIndicator()!= null && parsedProductObject.getReviewIndicator().getTotalReviewCount() > 0) {
                        _productWithQuaityIndicators = parsedProductObject;
                        runOnUiThread(_activity, new Runnable() {
                            @Override
                            public void run() {
                                //Handle UI here
                                if (productReviewsListener != null) {
                                    productReviewsListener.onProductReviewsFetchCompleted(_productWithQuaityIndicators);
                                }

                                if (isLoggingActive()) {
                                    Log.d("TSDEBUG", "Product Reviews Summary call done");
                                }
                            }});
                    } else {
                        if (isLoggingActive()) {
                            Log.d("TSDEBUG", "No product reviews summary found for this product");
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
