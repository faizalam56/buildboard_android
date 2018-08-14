package com.buildboard.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardEditText;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.view.SnackBarFactory;
import com.google.android.gms.location.places.Place;

public class PopUpHelper {

    public interface ConfirmPopUp {
        void onConfirm(boolean isConfirm);
        void onDismiss(boolean isDismiss);
    }

    public interface InfoPopupListener {
        void onConfirm();
    }

    public interface EditAddressListener {
        void onConfirm(String updatedAddress);
    }

    public static AlertDialog showConfirmPopup(Activity activity, String message, final ConfirmPopUp confirmPopup) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setMessage(message);

        builder.setPositiveButton(activity.getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                confirmPopup.onConfirm(true);
            }
        });

        builder.setNegativeButton(activity.getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                confirmPopup.onDismiss(false);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        return alertDialog;
    }

    public static AlertDialog showInfoConfirmPopup(Activity activity, String message, final InfoPopupListener confirmPopup) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setMessage(message);

        builder.setPositiveButton(activity.getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                confirmPopup.onConfirm();
            }
        });

        builder.setNegativeButton(activity.getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        return alertDialog;
    }

    public static AlertDialog showAlertPopup(Activity activity, String message, final ConfirmPopUp confirmPopup) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        confirmPopup.onConfirm(true);
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }

    public static AlertDialog showInfoAlertPopup(Activity activity, String message, final InfoPopupListener confirmPopup) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        confirmPopup.onConfirm();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }

    public static void showAddressDialog(final Activity activity, Place place, final EditAddressListener editAddressListener) {
        if (place != null) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            @SuppressLint("InflateParams") final View dialogView = inflater.inflate(R.layout.dialog_custom_places, null);
            dialogBuilder.setView(dialogView);
            final BuildBoardEditText buildBoardEditText = dialogView.findViewById(R.id.editPlaceName);
            final BuildBoardTextView textView = dialogView.findViewById(R.id.textSelectedLocation);
            buildBoardEditText.setText(place.getName());
            textView.setText(place.getAddress());
            dialogBuilder.setTitle(activity.getString(R.string.confirm_location));
            dialogBuilder.setPositiveButton(activity.getString(R.string.done), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    editAddressListener.onConfirm(buildBoardEditText.getText().toString());
                }
            });
            dialogBuilder.setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertChangeAddressDialog = dialogBuilder.create();
            alertChangeAddressDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            alertChangeAddressDialog.show();
        }
    }
}
