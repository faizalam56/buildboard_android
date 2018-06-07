package com.buildboard.modules.home.modules.mailbox.draft.drafts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.modules.home.modules.mailbox.draft.drafts.adapters.DraftsAdapter;
import com.buildboard.view.SimpleDividerItemDecoration;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DraftsActivity extends AppCompatActivity {

    @BindView(R.id.recycler_drafts)
    RecyclerView recyclerDrafts;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;

    @BindString(R.string.drafts)
    String stringDrafts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drafts);
        ButterKnife.bind(this);
        title.setText(stringDrafts);
        setServicesRecycler();
    }

    private void setServicesRecycler() {
        DraftsAdapter selectionAdapter = new DraftsAdapter(this, getDatas());
        recyclerDrafts.setLayoutManager(new LinearLayoutManager(this));
        recyclerDrafts.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerDrafts.setAdapter(selectionAdapter);
    }

    private ArrayList<String> getDatas() {
        ArrayList<String> datas = new ArrayList<>();
        datas.add("Service 1");
        datas.add("Service 2");
        datas.add("Service 3");
        datas.add("Service 4");
        datas.add("Service 5");

        return datas;
    }
}
