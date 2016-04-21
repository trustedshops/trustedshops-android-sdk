package com.trustedshops.androidsdk.trustbadge;

import android.app.Activity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.afollestad.materialdialogs.MaterialDialog;

import okhttp3.OkHttpClient;

public class TrustedShopsCheckout {
    OkHttpClient _client;
    private String _tsId;
    protected TrustbadgeOrder _Trustbadge_order;
    protected Activity _activity;
    protected boolean _alreadyInjected = false;

    public TrustedShopsCheckout() {
    }

    public TrustedShopsCheckout(TrustbadgeOrder tsCheckoutTrustbadgeOrder) {
        _Trustbadge_order = tsCheckoutTrustbadgeOrder;
    }

    public void setActivity(Activity activity) {
        this._activity = activity;
    }

    public Activity getActivity() {
        return _activity;
    }

    public void init(Activity activity) throws TrustbadgeException {
        setActivity(activity);

        //@TODO Validate parameters before starting dialog




        WebView webView = new WebView(getActivity());
        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100 && !_alreadyInjected) {
                    view.loadUrl("javascript:window.trustbadgeCheckoutManager.getOrderManager().setTsCheckoutBuyerEmail('asdasdsa@sadasas.com')");
                    view.loadUrl("javascript:window.trustbadgeCheckoutManager.getOrderManager().setTsCheckoutOrderNr('BNG12312321')");
                    view.loadUrl("javascript:window.trustbadgeCheckoutManager.getOrderManager().setTsCheckoutOrderCurrency('EUR')");
                    view.loadUrl("javascript:window.trustbadgeCheckoutManager.getOrderManager().setTsCheckoutOrderPaymentType('PAYPAL')");
                    view.loadUrl("javascript:window.trustbadgeCheckoutManager.getOrderManager().addProduct('www.google.de', 'Bügeleisen', 'ART-123312', 'http://image.google.com/image/product.png', 'NFS512321321', 'JSDSADSA', 'TEFAL')");
                    view.loadUrl("javascript:document.body.appendChild(window.trustbadgeCheckoutManager.getOrderManager().getTrustedShopsCheckoutElement())");
                    view.loadUrl("javascript:injectTrustbadge('XCD7B06A865895BD55F9B86C6BE099CC7')");
                    _alreadyInjected = true;
                    Log.d("TSDEBUG","Page loaded");

                }
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebContentsDebuggingEnabled(true);

        //@TODO build and pass parameters to WebView
        MaterialDialog dialog = new MaterialDialog.Builder(_activity)
                .customView(webView, true)
                .build();

        //webView.loadUrl("http://www.google.de");
        JsInterface jsInterface = new JsInterface(dialog, _Trustbadge_order);
        webView.addJavascriptInterface(jsInterface, "jsInterface");
        webView.setMinimumHeight(125);
        webView.loadUrl("file:///android_asset/checkout_page.html");
        webView.loadUrl("javascript:testEcho('Hello World!')");

        dialog.show();

    }
}
