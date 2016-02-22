package com.trustedshops.androidsdk.trustbadge.typefacehelper;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class TextVIewWithCustomTypeFace extends TextView {
    public TextVIewWithCustomTypeFace(Context context) {
        super(context);
        init();
    }

    public TextVIewWithCustomTypeFace(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        String font = "fonts/fontawesome.ttf";
        setCustomFont(this, font, getContext());
    }

    public static void setCustomFont(TextView textview, String font, Context context) {
        if(font == null) {
            return;
        }
        Typeface tf = FontCache.get(font, context);
        if(tf != null) {
            textview.setTypeface(tf);
            Log.d("TSDEBUT","Setting typeface");
        }
    }
}