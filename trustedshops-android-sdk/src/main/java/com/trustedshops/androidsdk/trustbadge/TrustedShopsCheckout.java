package com.trustedshops.androidsdk.trustbadge;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.afollestad.materialdialogs.MaterialDialog;

import okhttp3.OkHttpClient;

public class TrustedShopsCheckout {
    OkHttpClient _client;
    private String _tsId;
    protected TrustbadgeOrder _trustbadgeOrder;
    protected Activity _activity;
    protected boolean _alreadyInjected = false;
    public static final int _dismissCallNumber = 1;
    public static final int _errorCallNumber = 2;

    public TrustedShopsCheckout() {
    }

    public TrustedShopsCheckout(TrustbadgeOrder tsCheckoutTrustbadgeOrder) {
        _trustbadgeOrder = tsCheckoutTrustbadgeOrder;
    }

    public void setActivity(Activity activity) {
        this._activity = activity;
    }

    public Activity getActivity() {
        return _activity;
    }

    public void init(Activity activity, final Callback checkoutCallback) throws TrustbadgeException {
        setActivity(activity);

        if (checkoutCallback != null && !(checkoutCallback instanceof Callback)) {
            throw new TrustbadgeException("Please provide dialog dismiss callback of type Callback");
        }

        //@TODO Validate parameters before starting dialog

        WebView webView = new WebView(getActivity());
        webView.clearCache(true);
        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100 && !_alreadyInjected) {

                    if (_trustbadgeOrder.getTsCheckoutBuyerEmail() != null) {
                        view.loadUrl("javascript:window.trustbadgeCheckoutManager.getOrderManager().setTsCheckoutBuyerEmail('"+ _trustbadgeOrder.getTsCheckoutBuyerEmail()  +"')");
                    }

                    if (_trustbadgeOrder.getTsCheckoutOrderNr() != null) {
                        view.loadUrl("javascript:window.trustbadgeCheckoutManager.getOrderManager().setTsCheckoutOrderNr('"+ _trustbadgeOrder.getTsCheckoutOrderNr() +"')");
                    }

                    if (_trustbadgeOrder.getTsCheckoutOrderCurrency() != null) {
                        view.loadUrl("javascript:window.trustbadgeCheckoutManager.getOrderManager().setTsCheckoutOrderCurrency('"+ _trustbadgeOrder.getTsCheckoutOrderCurrency() +"')");
                    }

                    if (_trustbadgeOrder.getTsCheckoutOrderAmount() != null) {
                        view.loadUrl("javascript:window.trustbadgeCheckoutManager.getOrderManager().setTsCheckoutOrderAmount('"+_trustbadgeOrder.getTsCheckoutOrderAmount() +"')");
                    }

                    if (_trustbadgeOrder.getTsCheckoutOrderPaymentType() != null) {
                        view.loadUrl("javascript:window.trustbadgeCheckoutManager.getOrderManager().setTsCheckoutOrderPaymentType('"+_trustbadgeOrder.getTsCheckoutOrderPaymentType() +"')");
                    }

                    if (_trustbadgeOrder.getTsCheckoutOrderEstDeliveryDate() != null) {
                        view.loadUrl("javascript:window.trustbadgeCheckoutManager.getOrderManager().setTsCheckoutOrderEstDeliveryDate('"+ _trustbadgeOrder.getTsCheckoutOrderEstDeliveryDate() +"')");
                    }

                    if (_trustbadgeOrder.getTsCheckoutProductItems().size() > 0) {
                        for (Product checkoutProductItem : _trustbadgeOrder.getTsCheckoutProductItems() ) {
                            view.loadUrl("javascript:window.trustbadgeCheckoutManager.getOrderManager().addProduct('"+ checkoutProductItem.getTsCheckoutProductUrl() +"', '"+ checkoutProductItem.getTsCheckoutProductName() +"', '"+ checkoutProductItem.getTsCheckoutProductSKU() +"', '" + checkoutProductItem.getTsCheckoutProductImageUrl() +"', '" + checkoutProductItem.getTsCheckoutProductGTIN() +"', '" + checkoutProductItem.getTsCheckoutProductMPN() +"', '" + checkoutProductItem.getTsCheckoutProductBrand() +"')");
                        }
                    }

                    view.loadUrl("javascript:document.body.appendChild(window.trustbadgeCheckoutManager.getOrderManager().getTrustedShopsCheckoutElement())");
                    view.loadUrl("javascript:injectTrustbadge('"+_trustbadgeOrder.getTsId()+"')");
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
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (checkoutCallback != null) {
                            Message test = Message.obtain();
                            //dialog closed
                            test.what = _dismissCallNumber;
                            checkoutCallback.handleMessage(test);
                        }
                    }
                })
                .build();

        //webView.loadUrl("http://www.google.de");
        JsInterface jsInterface = new JsInterface(dialog, _trustbadgeOrder, checkoutCallback);
        webView.addJavascriptInterface(jsInterface, "jsInterface");
        webView.loadUrl("file:///android_asset/checkout_page.html");
        dialog.show();

    }
}
