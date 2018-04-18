package com.buildboard.modules.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.buildboard.R;
import com.buildboard.modules.selection.adapters.SelectionAdapter;
import com.buildboard.utils.AppConstant;
import com.buildboard.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpSelectionActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type_login);
        ButterKnife.bind(this);

        setRecyclerView();
    }

    private void setRecyclerView() {
        if (getIntent().hasExtra(INTENT_TITLE))
            getSupportActionBar().setTitle(getIntent().getStringExtra(INTENT_TITLE));

        if (!getIntent().hasExtra(DATA)) return;

        final ArrayList<String> arrayList = getIntent().getStringArrayListExtra(DATA);

        SelectionAdapter userTypeAdapter = new SelectionAdapter(this, arrayList, new SelectionAdapter.IItemClick() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.putExtra(INTENT_SELECTION, arrayList.get(position));
                setResult(ACTIVITY_RESULT_CODE, intent);
                SignUpSelectionActivity.this.finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.setAdapter(userTypeAdapter);
    }
}