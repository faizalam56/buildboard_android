package com.buildboard.modules.home.modules.marketplace.contractors;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.modules.home.modules.marketplace.contractors.adapters.ContractorProjectAttachmetsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.buildboard.constants.AppConstant.DATA;

public class ContractorProjectsAttachmentActivity extends AppCompatActivity {

    private ArrayList<String> mMenuArray = new ArrayList<>();
    private ArrayList<String> mAttachmentArray = new ArrayList<>();
    private Context mContext;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    BuildBoardTextView toolbarTitle;
    @BindView(R.id.recycler_attachment)
    RecyclerView recyclerAttachmets;
    @BindView(R.id.text_noattachmets)
    TextView textNoAttachment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_attachments);
        ButterKnife.bind(this);

        toolbarTitle.setText(AppConstant.TEXT_ATTACHMENT);
        mContext = this;
        getIntentData();
    }

    private void getIntentData() {
        if (getIntent().hasExtra(DATA)) {
            mAttachmentArray = (ArrayList<String>) getIntent().getSerializableExtra(DATA);

            if (mAttachmentArray == null || mAttachmentArray.isEmpty()) {
                textNoAttachment.setVisibility(View.VISIBLE);
                recyclerAttachmets.setVisibility(View.GONE);
            }

            setNearbyContractorsRecycler(mAttachmentArray);
        }
    }

    private void setNearbyContractorsRecycler(ArrayList<String> AttachmentArray) {
        ContractorProjectAttachmetsAdapter selectionAdapter = new ContractorProjectAttachmetsAdapter(mContext, AttachmentArray);
        recyclerAttachmets.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerAttachmets.setAdapter(selectionAdapter);
    }
}