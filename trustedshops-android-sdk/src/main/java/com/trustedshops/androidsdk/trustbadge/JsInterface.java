package com.trustedshops.androidsdk.trustbadge;


import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.afollestad.materialdialogs.MaterialDialog;

public class JsInterface {
    public String someString;
    Context mContext;
    MaterialDialog mDialog;

    public JsInterface(MaterialDialog dialog) {
        mDialog = dialog;
    }
    @JavascriptInterface
    public String dialogDismiss(){
        Log.v("TSDEBUG","Dismissing dialog");
        mDialog.dismiss();
        return someString;
    }
}