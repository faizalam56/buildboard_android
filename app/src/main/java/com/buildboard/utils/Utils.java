package com.buildboard.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Utils {

    public static void display(Context context, String imgUri, ImageView imageView, int placeHolder) {
        if (!TextUtils.isEmpty(imgUri) && context != null && imageView != null) {
            Picasso.get().load(imgUri).placeholder(placeHolder).into(imageView);
        } else if (context != null && imageView != null) {
            imageView.setImageResource(placeHolder);
        }
    }
}