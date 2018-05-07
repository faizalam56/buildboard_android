package com.buildboard.modules.home.modules.marketplace.contractor_projecttype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.buildboard.R;
import com.buildboard.modules.home.modules.marketplace.contractor_projecttype.adapters.ContractorByProjectTypeDetailAdapter;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ContractorByProjectTypeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler_contractors_by_projecttype)
    RecyclerView recyclerContractorByProjectType;

    @BindString(R.string.contractors)
    String stringContractors;
    @BindString(R.string.by_project_type)
    String stringByProjectType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_by_project_type);
        ButterKnife.bind(this);
        toolbar.setTitle(stringContractors);
        toolbar.setSubtitle(stringByProjectType);
        toolbar.inflateMenu(R.menu.filter);
        toolbar.setOnMenuItemClickListener(menuItemClickListener);
        setInboxRecycler();
    }

    private Toolbar.OnMenuItemClickListener menuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            return false;
        }
    };

    private void setInboxRecycler() {
        ContractorByProjectTypeDetailAdapter contractorByProjectTypeAdapter = new ContractorByProjectTypeDetailAdapter(this);
        recyclerContractorByProjectType.setLayoutManager(new LinearLayoutManager(this));
        recyclerContractorByProjectType.setAdapter(contractorByProjectTypeAdapter);
    }
}