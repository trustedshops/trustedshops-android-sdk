package com.trustedshops.androidsdk.trustbadge;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.nfc.Tag;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.trustedshops.androidsdk.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Trustbadge {

    private String _tsId;
    private String _clientToken;

    public Trustbadge() {

    }

    public Trustbadge(String tsId) {
        this._tsId = tsId;
    }

    public Trustbadge(String tsId, String clientToken) {
        this._tsId = tsId;
        this._clientToken = clientToken;
    }

    public void setTsId(String tsId) {
        this._tsId = tsId;
    }

    public String getTsId() {
        return this._tsId;
    }

    public void setClientToken(String clientToken) {
        this._clientToken = clientToken;
    }

    public String getClinetToken() {
        return this._clientToken;
    }

    public void getTrustbadge(ImageView view) throws TrustbadgeException, IllegalArgumentException {
        if (!Validator.validateTsId(getTsId())) {
            throw new IllegalArgumentException("Wrong TSID provided");
        }
        view.setImageResource(R.drawable.ts_seal);
        view.setTag("Trustbadge");
    }
}
