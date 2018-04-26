package com.buildboard.modules.selection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.interfaces.IRecyclerItemClickListener;
import com.buildboard.modules.selection.adapters.SelectionAdapter;
import com.buildboard.view.SimpleDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectionActivity extends AppCompatActivity implements AppConstant, IRecyclerItemClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindString(R.string.user_type)
    String stringUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        ButterKnife.bind(this);

        getIntentData();
    }

    private void setRecyclerView(ArrayList<String> datas) {
        SelectionAdapter selectionAdapter = new SelectionAdapter(this, datas, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.setAdapter(selectionAdapter);
    }

    private void getIntentData() {
        if (getIntent().hasExtra(INTENT_TITLE))
            toolbar.setTitle(getIntent().getStringExtra(INTENT_TITLE));

        if (getIntent().hasExtra(DATA)) {
            setRecyclerView(getIntent().getStringArrayListExtra(DATA));
        }
    }

    @Override
    public void onItemClick(View view, int position, Object data) {
        Intent intent = new Intent();
        intent.putExtra(INTENT_SELECTED_ITEM, (String) data);
        setResult(RESULT_OK, intent);
        finish();
    }
}