package com.buildboard.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardTextView;

public class AddProfilePhotoDialog {

    public void showDialog(Activity activity, final IAddProfileCallback iUserTypeCallback) {
//        final Dialog dialog = new Dialog(activity);

        final Dialog dialog=new Dialog(activity,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_profile_photo);

        ImageView dialogButton = (ImageView) dialog.findViewById(R.id.image_close);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        BuildBoardTextView textConsumer = (BuildBoardTextView) dialog.findViewById(R.id.text_consumer);
        textConsumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                iUserTypeCallback.onImageSelection();
            }
        });

        TextView textContractor = (TextView) dialog.findViewById(R.id.text_contractor);
        textContractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                iUserTypeCallback.onSaveInage();
            }
        });

        dialog.show();
    }

    public interface IAddProfileCallback {
        void onImageSelection();

        void onSaveInage();
    }
}
