package com.buildboard.modules.selection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.buildboard.R;
import com.buildboard.modules.selection.adapters.SelectionAdapter;
import com.buildboard.utils.AppConstant;
import com.buildboard.utils.SimpleDividerItemDecoration;

import java.util.Arrays;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserTypeLoginActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindString(R.string.user_type)
    String stringUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type_login);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(stringUserType);

        setRecyclerView();
    }

    private void setRecyclerView() {
        if (getIntent().hasExtra(INTENT_TITLE))
            getSupportActionBar().setTitle(getIntent().getStringExtra(INTENT_TITLE));

        String[] stringArray = getResources().getStringArray(R.array.user_type_array);
        final List<String> list = Arrays.asList(stringArray);
        SelectionAdapter userTypeAdapter = new SelectionAdapter(this, list, new SelectionAdapter.IItemClick() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.putExtra(INTENT_SELECTION, list.get(position));
                setResult(ACTIVITY_RESULT_CODE, intent);
                UserTypeLoginActivity.this.finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.setAdapter(userTypeAdapter);
    }
}