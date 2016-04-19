package com.trustedshops.androidsdk.trustbadge;

import android.app.Activity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.afollestad.materialdialogs.MaterialDialog;

import okhttp3.OkHttpClient;

public class TrustedShopsCheckout {
    OkHttpClient _client;
    private String _tsId;
    protected TrustbadgeOrder _Trustbadge_order;
    protected Activity _activity;

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
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        /*webView.setWebContentsDebuggingEnabled(true);*/

        //@TODO build and pass parameters to WebView
        MaterialDialog dialog = new MaterialDialog.Builder(_activity)
                .customView(webView, true)
                .build();

        //webView.loadUrl("http://www.google.de");
        JsInterface jsInterface = new JsInterface(dialog);
        webView.addJavascriptInterface(jsInterface, "jsInterface");
        webView.setMinimumHeight(125);
        webView.loadUrl("file:///android_asset/checkout_page.html");

        dialog.show();

    }
}
