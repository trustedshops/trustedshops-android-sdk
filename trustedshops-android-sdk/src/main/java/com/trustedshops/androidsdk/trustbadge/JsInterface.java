package com.trustedshops.androidsdk.trustbadge;


import android.content.Context;
import android.os.Handler.Callback;
import android.os.Message;
import android.webkit.JavascriptInterface;

import com.afollestad.materialdialogs.MaterialDialog;

public class JsInterface {
    public String someString;
    Context mContext;
    MaterialDialog mDialog;
    TrustbadgeOrder mOrder;
    Callback mCallback;

    public JsInterface(MaterialDialog dialog, TrustbadgeOrder order, Callback checkoutCallback) {
        mDialog = dialog;
        mOrder = order;
        mCallback = checkoutCallback;
    }

    @JavascriptInterface
    public void dialogDismiss(){
        mDialog.dismiss();
        if (mCallback != null) {
            Message dismissMessage = Message.obtain();
            dismissMessage.what = TrustedShopsCheckout._dismissCallNumber;
            mCallback.handleMessage(dismissMessage);
        }
    }
}