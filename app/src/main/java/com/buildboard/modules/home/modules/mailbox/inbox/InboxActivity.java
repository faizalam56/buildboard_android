package com.buildboard.modules.home.modules.mailbox.inbox;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.fonts.FontHelper;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.mailbox.inbox.adapters.InboxAdapter;
import com.buildboard.modules.home.modules.mailbox.inbox.models.Data;
import com.buildboard.modules.home.modules.mailbox.inbox.models.InboxMessagesResponse;
import com.buildboard.modules.home.modules.mailbox.inbox.models.SendMessageRequest;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InboxActivity extends AppCompatActivity implements AppConstant {

    private InboxAdapter inboxAdapter;
    private Context mContext;
    private ArrayList<Data> mMessagesList = new ArrayList<>();
    private int mCurrentPage = 1;
    private String mUserId = null;
    private InboxMessagesResponse mMessagesResponse;
    private String mSelfUserId;
    private boolean isRefreshed =false;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recycler_messages)
    RecyclerView recyclerMessages;
    @BindView(R.id.edit_write_msg)
    EditText editWriteMsg;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image_reply)
    ImageView imageReply;
    @BindView(R.id.button_save_as_draft)
    Button buttonSaveAsDraft;
    @BindView(R.id.constraint_root)
    ConstraintLayout constraintLayout;
    @BindView(R.id.progress_messages)
    ProgressBar progressBar;
    @BindView(R.id.text_error_message)
    BuildBoardTextView textErrorMessage;

    @BindString(R.string.inbox)
    String stringInbox;
    @BindString(R.string.no_chat_yet)
    String textNoChatMessage;
    @BindString(R.string.text)
    String textMessageType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        mContext = this;
        mSelfUserId = AppPreference.getAppPreference(mContext).getString(AppConstant.USER_ID);
        title.setText(stringInbox);
        setFonts();
        getIntentData();
    }

    @OnClick(R.id.linear_reply)
    void replyTapped() {
        if (ConnectionDetector.isNetworkConnected(mContext)) {
            if (!TextUtils.isEmpty(editWriteMsg.getText())) {

                List<Data> messageData = new ArrayList<>();
                Data data = new Data();
                data.setBody(editWriteMsg.getText().toString());
                data.setRecipientId(mUserId);
                data.setType(textMessageType);
                data.setCreatedAt(getCurrentTime());
                messageData.add(data);
                sendMessage(editWriteMsg.getText().toString());
                editWriteMsg.setText("");

                if (inboxAdapter == null) {
                    recyclerMessages.setVisibility(View.VISIBLE);
                    textErrorMessage.setVisibility(View.GONE);
                    setInboxRecycler(messageData, mCurrentPage);
                } else {
                    mMessagesList.addAll(messageData);
                    inboxAdapter.notifyDataSetChanged();
                    recyclerMessages.scrollToPosition(mMessagesList.size() - 1);
                }
            }
        } else {
            ConnectionDetector.createSnackBar(mContext, constraintLayout);
        }
    }

    private void getIntentData() {
        if (getIntent().hasExtra(DATA)) {
            mUserId = getIntent().getStringExtra(DATA);
            getMessages(mUserId,mCurrentPage);
        }
    }

    private void sendMessage(String message) {

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setBody(message);
        sendMessageRequest.setType(textMessageType);
        sendMessageRequest.setRecipientid(mUserId);
        DataManager.getInstance().sendMessage(this, sendMessageRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                if (response == null) return;
                //TODO Handle Response
            }

            @Override
            public void onError(Object error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.inboxmenu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:

                if (ConnectionDetector.isNetworkConnected(InboxActivity.this)) {
                    isRefreshed = true;
                    mMessagesList=new ArrayList<>();
                    getMessages(mUserId, mCurrentPage);
                } else {
                    ConnectionDetector.createSnackBar(mContext, constraintLayout);
                }
                break;
             default:
                break;
        }

        return true;
    }

    private String getCurrentTime() {
        String currentTime = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentTime = sdf.format(new Date());
        return currentTime;
    }

    private void getMessages(String userId, int page) {

        ProgressHelper.showProgressBar(InboxActivity.this, progressBar);
        DataManager.getInstance().getInboxMessages(InboxActivity.this, userId, page, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                if (isRefreshed) inboxAdapter = null;

                isRefreshed = false;

                if (response == null) return;

                mMessagesResponse = (InboxMessagesResponse) response;
                boolean isMessageAvailable = mMessagesResponse.getData().get(0).getData().size() > 0;
                recyclerMessages.setVisibility(isMessageAvailable ? View.VISIBLE : View.INVISIBLE);
                textErrorMessage.setVisibility(isMessageAvailable ? View.INVISIBLE : View.VISIBLE);

                if (mMessagesResponse != null && mMessagesResponse.getData().get(0).getData() != null &&
                        mMessagesResponse.getData().get(0).getData().size() > 0) {
                    setInboxRecycler(mMessagesResponse.getData().get(0).getData(),
                            mMessagesResponse.getData().get(0).getLastPage());
                } else {
                    textErrorMessage.setText(textNoChatMessage);
                }
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
                Utils.showError(InboxActivity.this, constraintLayout, error);
            }
        });
    }

    private void setInboxRecycler(List<Data> inboxData, int lastPage) {
        mMessagesList.addAll(inboxData);

        if (inboxAdapter == null) {
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(InboxActivity.this);
            mLinearLayoutManager.setStackFromEnd(true);
            recyclerMessages.setLayoutManager(mLinearLayoutManager);
            inboxAdapter = new InboxAdapter(InboxActivity.this, mMessagesList, recyclerMessages);
            recyclerMessages.setAdapter(inboxAdapter);
            recyclerMessages.scrollToPosition(inboxData.size() - 1);
            inboxAdapter.setOnLoadMoreListener(new InboxAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    mCurrentPage++;
                    if (inboxAdapter != null && inboxAdapter.isLoading)
                        getMessages(mUserId, mCurrentPage);
                }
            });
        } else {
            recyclerMessages.getAdapter().notifyItemInserted((mMessagesList.size()));
        }

        if (inboxAdapter != null) {
            inboxAdapter.setLoading(false);
            if (mCurrentPage == lastPage)
                inboxAdapter.setLastPage(true);
        }
    }

    private void setFonts() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, editWriteMsg, buttonSaveAsDraft);
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, buttonSaveAsDraft);
    }
}
