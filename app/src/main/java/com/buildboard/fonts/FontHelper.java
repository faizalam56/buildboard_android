package com.buildboard.fonts;

import android.graphics.Typeface;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.buildboard.app.MyApplication;

public class FontHelper {

    public enum FontType {

        FONT_BOLD("fonts/Roboto-Bold.ttf"),
        FONT_MEDIUM("fonts/Roboto-Medium.ttf"),
        FONT_REGULAR("fonts/Roboto-Regular.ttf"),
        FONT_LIGHT("fonts/Roboto-Light.ttf");

        private final String type;

        FontType(String type) {
            this.type = type;
        }

        public static String whichFont(FontType fontType) {
            if (fontType != null) {
                for (FontType typeEnum : FontType.values()) {
                    if (fontType == typeEnum) return typeEnum.type;
                }
            }

            return null;
        }
    }

    public static void setFontFace(FontType fontType, View... views) {
        Typeface type = Typeface.createFromAsset(MyApplication.getInstance().getAssets(), FontType.whichFont(fontType));

        for (View view : views) {
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setTypeface(type);
            }
            if (view instanceof Button) {
                Button button = (Button) view;
                button.setTypeface(type);
            }
            if (view instanceof EditText) {
                EditText editText = (EditText) view;
                editText.setTypeface(type);
            }
            if (view instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) view;
                radioButton.setTypeface(type);
            }
            if (view instanceof SwitchCompat) {
                SwitchCompat switchCompat = (SwitchCompat) view;
                switchCompat.setTypeface(type);
            }
        }
    }
}
