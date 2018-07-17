package com.buildboard.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardTextView;

public class UserTypeDialog {

    public void showDialog(Activity activity, final IUserTypeCallback iUserTypeCallback) {
        final Dialog dialog = new Dialog(activity);

        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_usertype);

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
                iUserTypeCallback.onConsumerSelection();
            }
        });

        TextView textContractor = (TextView) dialog.findViewById(R.id.text_contractor);
        textContractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                iUserTypeCallback.onContractorSelection();
            }
        });

        dialog.show();
    }

    public interface IUserTypeCallback {
        void onConsumerSelection();

        void onContractorSelection();
    }
}
