package com.buildboard.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;

public class BuildBoardRadioButton extends AppCompatRadioButton {

    public BuildBoardRadioButton(Context context) {
        super(context);
        if (isInEditMode()) return;
        parseAttributes(null);
    }

    public BuildBoardRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) return;
        parseAttributes(attrs);
    }

    public BuildBoardRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode()) return;
        parseAttributes(attrs);
    }

    public static Typeface getRoboto(Context context, int typeface) {
        switch (typeface) {
            case Roboto.ROBOTO_BOLD:
                if (Roboto.sRobotoBold == null) {
                    Roboto.sRobotoBold = Typeface.createFromAsset(context.getAssets(), AppConstant.ROBOTO_BOLD);
                }
                return Roboto.sRobotoBold;

            case Roboto.ROBOTO_MEDIUM:
                if (Roboto.sRobotoMedium == null) {
                    Roboto.sRobotoMedium = Typeface.createFromAsset(context.getAssets(), AppConstant.ROBOTO_MEDIUM);
                }
                return Roboto.sRobotoMedium;
            case Roboto.ROBOTO_LIGHT:
                if (Roboto.sRobotoLight == null) {
                    Roboto.sRobotoLight = Typeface.createFromAsset(context.getAssets(), AppConstant.ROBOTO_LIGHT);
                }
                return Roboto.sRobotoLight;

            default:
                if (Roboto.sRobotoRegular == null) {
                    Roboto.sRobotoRegular = Typeface.createFromAsset(context.getAssets(), AppConstant.ROBOTO_REGULAR);
                }
                return Roboto.sRobotoRegular;
        }
    }

    private void parseAttributes(AttributeSet attrs) {
        int typeface;
        if (attrs == null) {
            typeface = Roboto.ROBOTO_REGULAR;
        } else {
            TypedArray values = getContext().obtainStyledAttributes(attrs, R.styleable.BuildBoardWidget);
            typeface = values.getInt(R.styleable.BuildBoardWidget_typeface, Roboto.ROBOTO_REGULAR);
            values.recycle();
        }
        setTypeface(getRoboto(typeface));
    }

    private Typeface getRoboto(int typeface) {
        return getRoboto(getContext(), typeface);
    }

    public void setRobotoTypeface(int typeface) {
        setTypeface(getRoboto(typeface));
    }

    public static class Roboto {

        public static final int ROBOTO_BOLD = 1;
        public static final int ROBOTO_MEDIUM = 2;
        public static final int ROBOTO_REGULAR = 3;
        public static final int ROBOTO_LIGHT = 4;
        private static Typeface sRobotoBold;
        private static Typeface sRobotoMedium;
        private static Typeface sRobotoRegular;
        private static Typeface sRobotoLight;
    }
}