package com.trustedshops.androidsdk.trustbadge;


import android.content.Context;
import android.os.Handler.Callback;
import android.os.Message;
import android.webkit.JavascriptInterface;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

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

    public JsInterface(MaterialDialog dialog, Callback resizeCallback) {
        mDialog = dialog;
        mCallback = resizeCallback;
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

    @JavascriptInterface
    public void resizeDialog(String widthCommaHeight) {
//        Log.v("JS STUFF", "got something from js: " + widthCommaHeight);
        try {
            JSONObject temp = new JSONObject(widthCommaHeight);
//            Log.v("JS STUFF", "created JSON object...");

            if (mCallback != null) {
                Message resizeMessage = Message.obtain();
                resizeMessage.arg1 = temp.getInt("width");
                resizeMessage.arg2 = temp.getInt("height");
                mCallback.handleMessage(resizeMessage);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}