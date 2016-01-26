package com.trustedshops.androidsdk.trustbadge;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;

import com.trustedshops.androidsdk.R;

public class Trustbadge {
    public void getTrustbadge(ImageView view, String tsId) {
           view.setImageResource(R.drawable.ts_seal);
    }
}
