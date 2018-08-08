package com.buildboard.modules.home.modules.mailbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.modules.home.modules.mailbox.adapters.RelatedConsumerListAdapter;
import com.buildboard.modules.home.modules.mailbox.adapters.MessagesAdapter;
import com.buildboard.modules.home.modules.mailbox.adapters.RelatedContractorListAdapter;
import com.buildboard.modules.home.modules.mailbox.modules.models.ConsumerRelatedData;
import com.buildboard.modules.home.modules.mailbox.models.MessageData;
import com.buildboard.modules.home.modules.mailbox.modules.models.ContractorRelatedData;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.view.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ContactListActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.recycler_contactlist)
    RecyclerView recyclerConsumerList;
    @BindView(R.id.progress_messages)
    ProgressBar progressMessages;
    @BindView(R.id.text_error_message)
    BuildBoardTextView textErrorMessage;
    @BindView(R.id.relative_root)
    RelativeLayout relativeLayout;
    @BindView(R.id.title)
    BuildBoardTextView toolbarTitle;

    @BindString(R.string.internet_connection_check)
    String stringNoInternet;
    @BindString(R.string.error_no_messages)
    String stringNoMessages;
    @BindArray(R.array.array_user_type)
    String[] arrayUserType;

    private Unbinder mUnbinder;
    private int mCurrentPage = 1;
    private ArrayList<MessageData> mMessagesList = new ArrayList<>();
    private ArrayList<ConsumerRelatedData> mConsumerList = new ArrayList<>();
    private ArrayList<ContractorRelatedData> mContractorList = new ArrayList<>();

    private MessagesAdapter mMessagesAdapter;
    private RelatedConsumerListAdapter mRelatedConsumerListAdapter;
    private RelatedContractorListAdapter mRelatedContractorListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        mUnbinder = ButterKnife.bind(this);

        getIntentData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    private void getIntentData() {
        if (getIntent().hasExtra(DATA)) {
            if (getIntent().getBooleanExtra(IS_CONTRACTOR, false) == true) {
                toolbarTitle.setText(arrayUserType[0]);
                List<ConsumerRelatedData> mConsumerRelatedData = (List<ConsumerRelatedData>) getIntent().getSerializableExtra(DATA);
                setRelatedConsumerListRecycler(mConsumerRelatedData, 1);

            } else {
                toolbarTitle.setText(arrayUserType[1]);
                List<ContractorRelatedData> mContractorRelatedData = (List<ContractorRelatedData>) getIntent().getSerializableExtra(DATA);
                setRelatedContractorListRecycler(mContractorRelatedData, 1);
            }
        }
    }

    private void setRelatedConsumerListRecycler(List<ConsumerRelatedData> ConsumerRelatedData, int lastPage) {
        mConsumerList.addAll(ConsumerRelatedData);

        if (mRelatedConsumerListAdapter == null) {
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(ContactListActivity.this);
            recyclerConsumerList.setLayoutManager(mLinearLayoutManager);
            mRelatedConsumerListAdapter = new RelatedConsumerListAdapter(ContactListActivity.this, mConsumerList, recyclerConsumerList);
            recyclerConsumerList.setAdapter(mRelatedConsumerListAdapter);
            mRelatedConsumerListAdapter.setOnLoadMoreListener(new MessagesAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    mCurrentPage++;
                    if (mRelatedConsumerListAdapter != null && mRelatedConsumerListAdapter.isLoading) {

                    }
                }
            });
        } else {
            recyclerConsumerList.getAdapter().notifyItemInserted((mConsumerList.size()));
        }

        if (mRelatedConsumerListAdapter != null) {
            mRelatedConsumerListAdapter.setLoading(false);
            if (mCurrentPage == lastPage)
                mRelatedConsumerListAdapter.setLastPage(true);
        }
    }

    private void setRelatedContractorListRecycler(List<ContractorRelatedData> contractorRelatedData, int lastPage) {
        mContractorList.addAll(contractorRelatedData);

        if (mRelatedContractorListAdapter == null) {
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(ContactListActivity.this);
            recyclerConsumerList.setLayoutManager(mLinearLayoutManager);
            mRelatedContractorListAdapter = new RelatedContractorListAdapter(ContactListActivity.this, mContractorList, recyclerConsumerList);
            recyclerConsumerList.setAdapter(mRelatedContractorListAdapter);
            mRelatedContractorListAdapter.setOnLoadMoreListener(new MessagesAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    mCurrentPage++;
                    if (mRelatedContractorListAdapter != null && mRelatedContractorListAdapter.isLoading) {

                    }
                }
            });
        } else {
            recyclerConsumerList.getAdapter().notifyItemInserted((mContractorList.size()));
        }

        if (mRelatedContractorListAdapter != null) {
            mRelatedContractorListAdapter.setLoading(false);
            if (mCurrentPage == lastPage)
                mRelatedContractorListAdapter.setLastPage(true);
        }
    }
}
