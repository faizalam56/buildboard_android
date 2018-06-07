package com.buildboard.modules.home.modules.mailbox.inbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.modules.home.modules.mailbox.inbox.adapters.InboxListAdapter;
import com.buildboard.view.SimpleDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class InboxListActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recycler_inbox_list)
    RecyclerView recyclerInboxList;

    @BindString(R.string.inbox)
    String stringInbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_list);
        ButterKnife.bind(this);

        title.setText(stringInbox);
        setRecycler();
    }

    private void setRecycler() {
        InboxListAdapter inboxListAdapter = new InboxListAdapter(this, getDatas());
        recyclerInboxList.setLayoutManager(new LinearLayoutManager(this));
        recyclerInboxList.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerInboxList.setAdapter(inboxListAdapter);
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
