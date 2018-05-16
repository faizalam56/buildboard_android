package com.buildboard.modules.home.modules.marketplace.contractors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.fonts.FontHelper;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.marketplace.contractor_projecttype.ContractorByProjectTypeActivity;
import com.buildboard.modules.home.modules.marketplace.contractor_projecttype.models.ContractorByProjectTypeData;
import com.buildboard.modules.home.modules.marketplace.contractor_projecttype.models.ContractorByProjectTypeListData;
import com.buildboard.modules.home.modules.marketplace.contractors.adapters.ContractorsAdapter;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ContractorsActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler_contractors)
    RecyclerView recyclerContractors;

    @BindView(R.id.text_project_type)
    TextView textProjectType;

    @BindView(R.id.edit_search_by_name)
    EditText editSearchByName;

    @BindString(R.string.contractors)
    String stringContractors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractors);
        ButterKnife.bind(this);

        toolbar.setTitle(stringContractors);
        setFont();

        if (getIntent().hasExtra(DATA)) {
            getContractorByProjectType(getIntent().getStringExtra(DATA));
        }

        if (getIntent().hasExtra(INTENT_TITLE)) {
            textProjectType.setText(getIntent().getStringExtra(INTENT_TITLE));
        }
    }

    private void setContractorsAdapter(ArrayList<ContractorByProjectTypeListData> contractorList) {
        ContractorsAdapter contractorsAdapter = new ContractorsAdapter(this, contractorList);
        recyclerContractors.setLayoutManager(new LinearLayoutManager(this));
        recyclerContractors.setAdapter(contractorsAdapter);
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textProjectType);
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, editSearchByName);
    }

    private void getContractorByProjectType(String contractorTypeId) {
        ProgressHelper.start(this, getString(R.string.msg_please_wait));
        DataManager.getInstance().getContractorByProjectType(this, contractorTypeId, 1, 2, 30, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                if (response == null) return;

                ContractorByProjectTypeData contractorByProjectTypeData = (ContractorByProjectTypeData) response;
                setContractorsAdapter(contractorByProjectTypeData.getDatas());
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
//                Utils.showError(ContractorByProjectTypeActivity.this, constraintRoot, error);
            }
        });
    }
}