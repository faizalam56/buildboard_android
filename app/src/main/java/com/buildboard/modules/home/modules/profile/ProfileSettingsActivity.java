package com.buildboard.modules.home.modules.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.fonts.FontHelper;

import org.w3c.dom.Text;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileSettingsActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.text_edit_profile)
    TextView textEditProfile;
    @BindView(R.id.text_manage_payment)
    TextView textManagePayment;
    @BindView(R.id.text_payment)
    TextView textPayment;
    @BindView(R.id.text_privacy_policy)
    TextView textPrivacyPolicy;
    @BindView(R.id.text_terms_of_use)
    TextView textTermsOfUse;
    @BindView(R.id.text_faq)
    TextView textFaqs;
    @BindView(R.id.text_contact)
    TextView textContactUs;
    @BindView(R.id.text_logout)
    TextView textLogout;

    @BindString(R.string.settings)
    String stringSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        ButterKnife.bind(this);

        title.setText(stringSettings);
        setFont();
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, textEditProfile, textManagePayment,
                textPayment, textPrivacyPolicy, textTermsOfUse, textFaqs, textContactUs, textLogout);
    }
}
