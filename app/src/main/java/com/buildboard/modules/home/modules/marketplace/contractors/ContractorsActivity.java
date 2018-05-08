package com.buildboard.modules.home.modules.marketplace.contractors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.marketplace.contractors.adapters.ContractorsAdapter;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ContractorsActivity extends AppCompatActivity {

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
        setInboxRecycler();
    }

    private void setInboxRecycler() {
        ContractorsAdapter contractorsAdapter = new ContractorsAdapter(this);
        recyclerContractors.setLayoutManager(new LinearLayoutManager(this));
        recyclerContractors.setAdapter(contractorsAdapter);
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textProjectType);
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, editSearchByName);
    }
}