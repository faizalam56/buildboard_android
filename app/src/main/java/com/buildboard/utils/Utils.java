package com.buildboard.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.http.ErrorManager;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.buildboard.constants.AppConstant.INPUT_PATTERN;
import static com.buildboard.constants.AppConstant.OUTPUT_PATTERN;

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

    public static String getImagePath(Activity activity, Uri uri) {
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


    public static void showProgressColor(Activity activity, ProgressBar progressBar) {
        if (activity != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                Drawable wrapDrawable = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
                DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(activity, R.color.colorGreen));
                progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
            } else {
                progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorGreen), PorterDuff.Mode.SRC_IN);
            }
        }
    }

    public static Uri getImageUri(Context mContext, Bitmap photo) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(mContext.getContentResolver(), photo, "", null);
        return Uri.parse(path);
    }


    public static void selectImage(final Activity activity) {
        final int PICK_IMAGE_CAMERA = 2001;
        final int PICK_IMAGE_GALLERY = 2002;
        try {
            final CharSequence[] options = activity.getResources().getStringArray(R.array.array_choose_image);
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity);
            builder.setTitle(activity.getString(R.string.select_option));
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals(activity.getString(R.string.image_from_camera))) {
                        dialog.dismiss();
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        activity.startActivityForResult(intent, PICK_IMAGE_CAMERA);
                    } else if (options[item].equals(activity.getString(R.string.image_from_gallery))) {
                        dialog.dismiss();
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        activity.startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                    } else if (options[item].equals(activity.getString(R.string.cancel))) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();

        } catch (Exception e) {
            Toast.makeText(activity, activity.getString(R.string.image_from_gallery), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static String dottedAfterCertainLength(String data, Context context, int length) {
        if (data.length() > length) {
            return data.substring(0, length) + context.getResources().getString(R.string.dot);
        } else {
            return data;
        }
    }

    public static String parseDateFormat(String time) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(INPUT_PATTERN, Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat(OUTPUT_PATTERN, Locale.getDefault());

        Date date;
        String formatted_date = null;

        try {
            date = inputFormat.parse(time);
            formatted_date = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatted_date;
    }

    public static String loadJsonFromAsset(Activity activity, String jsonFile){
        String json = null;
        try {
            InputStream inputStream = activity.getAssets().open(jsonFile);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    /* get date format: Jan 1, 2021 */
    public static String getFormattedDate(String stringDate) {
        String nonFormattedDate = stringDate.split("\\s+")[0].replaceAll("-", "/");
        String formattedDate = "";
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        SimpleDateFormat format2 = new SimpleDateFormat("MMM d, yyyy",Locale.getDefault());
        Date date = null;
        try {
            date = format1.parse(nonFormattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return nonFormattedDate;
        }
        formattedDate = format2.format(date);
        return formattedDate;
    }

}