package com.buildboard.modules.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.buildboard.R;
import com.buildboard.modules.selection.adapters.SelectionAdapter;
import com.buildboard.utils.AppConstant;
import com.buildboard.utils.IRecyclerItemClickListener;
import com.buildboard.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpSelectionActivity extends AppCompatActivity implements AppConstant, IRecyclerItemClickListener {

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

        SelectionAdapter userTypeAdapter = new SelectionAdapter(this, arrayList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.setAdapter(userTypeAdapter);
    }

    @Override
    public void onItemClick(View view, int position, Object data) {
        Intent intent = new Intent();
        intent.putExtra(INTENT_SELECTION, (String) data);
        setResult(ACTIVITY_RESULT_CODE, intent);
        SignUpSelectionActivity.this.finish();
    }
}