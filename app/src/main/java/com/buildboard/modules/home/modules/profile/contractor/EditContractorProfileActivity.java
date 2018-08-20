package com.buildboard.modules.home.modules.profile.contractor;

import android.content.Intent;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.modules.signup.contractor.businessdocuments.BusinessDocumentsActivity;
import com.buildboard.modules.signup.contractor.businessinfo.SignUpContractorActivity;
import com.buildboard.modules.signup.contractor.previouswork.PreviousWorkActivity;
import com.buildboard.modules.signup.contractor.worktype.WorkTypeActivity;
import com.buildboard.permissions.PermissionHelper;
import com.buildboard.utils.ConnectionDetector;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditContractorProfileActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;

    @BindString(R.string.edit_profile)
    String stringEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contractor_profile);
        ButterKnife.bind(this);
        title.setText(stringEditProfile);
    }

    @OnClick(R.id.card_business_info)
    void businessInfoTapped() {
        gotoScreen(SignUpContractorActivity.class);
    }

    @OnClick(R.id.card_type_of_work)
    void typeOfWorkTapped() {
        gotoScreen(WorkTypeActivity.class);
    }

    @OnClick(R.id.card_documents)
    void documentsTapped() {
        gotoScreen(BusinessDocumentsActivity.class);
    }

    @OnClick(R.id.card_previous_work)
    void previousWorkTapped() {
        gotoScreen(PreviousWorkActivity.class);
    }

    private void gotoScreen(Class target) {
        if (ConnectionDetector.isNetworkConnected(this))
            startActivity(new Intent(this, target));
        else
            ConnectionDetector.createSnackBar(this, constraintRoot);
    }
}