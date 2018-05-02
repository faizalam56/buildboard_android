package com.buildboard.modules.home.modules.mailbox.draft.drafts_reply;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.buildboard.R;
import com.buildboard.fonts.FontHelper;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DraftsReplyActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_draft_msg)
    EditText editDraftMsg;
    @BindView(R.id.button_next)
    Button buttonNext;

    @BindString(R.string.draft)
    String stringDraft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drafts_reply);
        ButterKnife.bind(this);
        setFont();
        setTitle();
    }

    private void setTitle() {
        toolbar.setTitle(stringDraft);
        toolbar.setSubtitle("To: Alex");
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.colorGray));
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, editDraftMsg);
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, buttonNext);
    }
}
