package com.buildboard.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.http.ErrorManager;
import com.buildboard.modules.home.modules.profile.consumer.models.addresses.getaddress.AddressListData;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class Utils {

    public static void display(Context context, String imgUri, ImageView imageView, int placeHolder) {
        if (!TextUtils.isEmpty(imgUri) && context != null && imageView != null) {
            Picasso.get().load(imgUri).placeholder(placeHolder).into(imageView);
        } else if (context != null && imageView != null) {
            imageView.setImageResource(placeHolder);
        }
    }

    public static void showError(Activity activity, View view, Object error) {
        ErrorManager errorManager = new ErrorManager(activity, view, error);
        errorManager.handleErrorResponse();
    }

   public static String getImagePath(Activity activity,Uri uri) {
       String path = null;
       try {
           if (uri != null) {
               String[] projection = {MediaStore.Images.Media.DATA};
               Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);

               if (cursor == null) return null;

               int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
               cursor.moveToFirst();
               path = cursor.getString(column_index);
               cursor.close();
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return path;
   }

    @NonNull
    public static SpannableStringBuilder setStarToLabel(String text) {
        String colored = " *";
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(text);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();
        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public static void openAddressInMap(Activity mActivity, LatLng latLng, String address) {
        Uri mapUri = Uri.parse("geo:0,0?q=" + latLng.latitude + "," + latLng.longitude + address);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        mActivity.startActivity(mapIntent);
    }

    public static String resizeAndCompressImageBeforeSend(Context context, String filePath) {
        int compressQuality = 70;
        int imageResolution = 800;
        Bitmap bmpPic = BitmapFactory.decodeFile(filePath);
        File file = new File(filePath);
        String filename = file.getName();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bos);

        if (bmpPic.getHeight() >= imageResolution && bmpPic.getWidth() >= imageResolution) {
            bmpPic = getResizedBitmap(bmpPic, imageResolution);
        }

        try {
            FileOutputStream bmpFile = new FileOutputStream(context.getCacheDir() + filename);
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpFile);
            bmpFile.flush();
            bmpFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return context.getCacheDir() + filename;
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    public static void showProgressColor(Activity activity , ProgressBar progressBar){
        if(activity!=null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                Drawable wrapDrawable = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
                DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(activity, R.color.colorGreen));
                progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
            } else {
                progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorGreen), PorterDuff.Mode.SRC_IN);
            }
        }
    }
}