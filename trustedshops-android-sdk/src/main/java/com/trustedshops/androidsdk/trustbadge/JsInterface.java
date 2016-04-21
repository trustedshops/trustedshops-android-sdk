package com.trustedshops.androidsdk.trustbadge;


import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.afollestad.materialdialogs.MaterialDialog;

public class JsInterface {
    public String someString;
    Context mContext;
    MaterialDialog mDialog;
    TrustbadgeOrder mOrder;

    public JsInterface(MaterialDialog dialog, TrustbadgeOrder order) {
        mDialog = dialog;
        mOrder = order;
    }

    @JavascriptInterface
    protected String getTrustbadgeCheckoutDataHtml() {
        Log.d("TSDEBUG", mOrder.getTrustbadgeCheckoutDataHtml());
        return mOrder.getTrustbadgeCheckoutDataHtml();
    }

    @JavascriptInterface
    protected void log(String log) {
        Log.d("TSDEBUG", log);
    }

    @JavascriptInterface
    public String dialogDismiss(){
        Log.v("TSDEBUG","Dismissing dialog");
        mDialog.dismiss();
        return someString;
    }
}