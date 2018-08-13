package com.buildboard.modules.home.modules.mailbox.inbox;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.mailbox.inbox.adapters.TrashMessageAdapter;
import com.buildboard.modules.home.modules.mailbox.models.MessageData;
import com.buildboard.modules.home.modules.mailbox.models.MessagesResponse;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TrashActivity extends AppCompatActivity implements AppConstant {

    private TrashMessageAdapter mTrashMessageAdapter;
     private ArrayList<MessageData> mMessagesList = new ArrayList<>();
    private int mCurrentPage = 1;
    private String mUserId = null;
    private MessagesResponse mMessagesResponse;
    private String mSelfUserId;
    private boolean isSwiped = false;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recycler_messages)
    RecyclerView recyclerMessages;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.relative_root)
    RelativeLayout constraintLayout;
    @BindView(R.id.progress_messages)
    ProgressBar progressBar;
    @BindView(R.id.text_error_message)
    BuildBoardTextView textErrorMessage;
    @BindView(R.id.swipe_root)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindString(R.string.bin)
    String stringTrash;
    @BindString(R.string.no_trash_yet)
    String textNoTrash;
    @BindString(R.string.text)
    String textMessageType;
    @BindString(R.string.internet_connection_check)
    String stringNoInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);
        ButterKnife.bind(this);

        boolean isNetworkConnected = ConnectionDetector.isNetworkConnected(this);
        mSelfUserId = AppPreference.getAppPreference(this).getString(AppConstant.USER_ID);
        title.setText(stringTrash);

        if (isNetworkConnected) getTrashMessages();
        else textErrorMessage.setText(stringNoInternet);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (ConnectionDetector.isNetworkConnected(TrashActivity.this)) {
                    isSwiped = true;
                    getTrashMessages();
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    ConnectionDetector.createSnackBar(TrashActivity.this, swipeRefreshLayout);
                }
            }
        });
    }

    private void getTrashMessages() {
        if (!isSwiped)
            ProgressHelper.showProgressBar(TrashActivity.this, progressBar);

        DataManager.getInstance().getTrash(TrashActivity.this, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {

                if (isSwiped) mTrashMessageAdapter=null;

                isSwiped=false;
                ProgressHelper.hideProgressBar();
                swipeRefreshLayout.setRefreshing(false);

                if (response == null) return;

                mMessagesResponse = (MessagesResponse) response;
                boolean isMessageAvailable = mMessagesResponse.getData().get(0).getData().size() > 0;
                recyclerMessages.setVisibility(isMessageAvailable ? View.VISIBLE : View.INVISIBLE);
                textErrorMessage.setVisibility(isMessageAvailable ? View.INVISIBLE : View.VISIBLE);

                if (mMessagesResponse != null && mMessagesResponse.getData().get(0).getData() != null &&
                        mMessagesResponse.getData().get(0).getData().size() > 0) {
                    setInboxRecycler(mMessagesResponse.getData().get(0).getData(),
                            mMessagesResponse.getData().get(0).getLastPage());
                } else {
                    textErrorMessage.setText(textNoTrash);
                }
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
                Utils.showError(TrashActivity.this, constraintLayout, error);
            }
        });
    }

    private void setInboxRecycler(ArrayList<MessageData> messageData, int lastPage) {
        mMessagesList= new ArrayList<>();
        mMessagesList.addAll(messageData);

        if (mTrashMessageAdapter == null) {
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(TrashActivity.this);
            recyclerMessages.setLayoutManager(mLinearLayoutManager);
            mTrashMessageAdapter = new TrashMessageAdapter(TrashActivity.this, mMessagesList, recyclerMessages);
            recyclerMessages.setAdapter(mTrashMessageAdapter);
            mTrashMessageAdapter.setOnLoadMoreListener(new TrashMessageAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    mCurrentPage++;
                    if (mTrashMessageAdapter != null && mTrashMessageAdapter.isLoading)
                        getTrashMessages();
                }
            });
        } else {
            recyclerMessages.getAdapter().notifyItemInserted((mMessagesList.size()));
        }

        if (mTrashMessageAdapter != null) {
            mTrashMessageAdapter.setLoading(false);
            if (mCurrentPage == lastPage)
                mTrashMessageAdapter.setLastPage(true);
        }
    }
 }
