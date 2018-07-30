package com.buildboard.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardButton;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.fonts.FontHelper;

public class AddProfilePhotoDialog {

    public ImageView imageProfile;

    public void showDialog(Activity activity, final IAddProfileCallback iUserTypeCallback) {
        final Dialog dialog = new Dialog(activity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_profile_photo);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawableResource(R.color.colorOverlay);

        ImageView imageClose = (ImageView) dialog.findViewById(R.id.image_close);
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        BuildBoardButton buttonSave = (BuildBoardButton) dialog.findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                iUserTypeCallback.onSaveImage();
            }
        });

        imageProfile = (ImageView) dialog.findViewById(R.id.image_profile);
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iUserTypeCallback.onImageSelection();
            }
        });

        BuildBoardTextView textAddProfile = (BuildBoardTextView) dialog.findViewById(R.id.text_add_profile_picture);
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textAddProfile, buttonSave);

        dialog.show();
    }

    public interface IAddProfileCallback {
        void onImageSelection();

        void onSaveImage();
    }
}
