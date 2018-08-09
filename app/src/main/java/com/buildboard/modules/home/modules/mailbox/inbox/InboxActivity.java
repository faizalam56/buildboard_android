package com.buildboard.modules.home.modules.mailbox.inbox;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.fonts.FontHelper;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.mailbox.adapters.MessagesAdapter;
import com.buildboard.modules.home.modules.mailbox.inbox.adapters.InboxAdapter;
import com.buildboard.modules.home.modules.mailbox.inbox.models.Data;
import com.buildboard.modules.home.modules.mailbox.inbox.models.InboxMessagesResponse;
import com.buildboard.modules.home.modules.mailbox.models.MessageData;
import com.buildboard.modules.home.modules.mailbox.models.MessagesResponse;
import com.buildboard.modules.home.modules.mailbox.modules.models.ConsumerRelatedData;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;
import com.buildboard.view.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InboxActivity extends AppCompatActivity implements AppConstant{

    private final ArrayList<MessageModel> datas = new ArrayList<>();
    private InboxAdapter inboxAdapter;
    private Context mContext;
    private ArrayList<Data> mMessagesList = new ArrayList<>();
    private int mCurrentPage = 1;
    private String mUserId=null;

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

    @BindString(R.string.inbox)
    String stringInbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        ButterKnife.bind(this);

        mContext=this;
        title.setText(stringInbox);
        setFonts();
        getIntentData();
     }

    @OnClick(R.id.linear_reply)
    void replyTapped() {
        if (!TextUtils.isEmpty(editWriteMsg.getText())) {
            datas.add(new MessageModel(editWriteMsg.getText().toString(), true));
            inboxAdapter.notifyDataSetChanged();
            recyclerMessages.scrollToPosition(datas.size() - 1);
            editWriteMsg.setText("");
        }
    }

    private void getIntentData() {
        if (getIntent().hasExtra(DATA)) {
            mUserId = getIntent().getStringExtra(DATA);
            getMessages(mUserId);
        }
    }

    private void getMessages(String userId){
         DataManager.getInstance().getInboxMessages(InboxActivity.this, userId, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                if (response == null) return;

                InboxMessagesResponse messagesResponse = (InboxMessagesResponse) response;

                boolean isMessageAvailable = messagesResponse.getData().get(0).getData().size() > 0;
                recyclerMessages.setVisibility(isMessageAvailable ? View.VISIBLE : View.INVISIBLE);
                //textErrorMessage.setVisibility(isMessageAvailable ? View.INVISIBLE : View.VISIBLE);

                if (messagesResponse != null && messagesResponse.getData().get(0).getData() != null &&
                        messagesResponse.getData().get(0).getData().size() > 0) {
                    setInboxRecycler(messagesResponse.getData().get(0).getData(),
                            messagesResponse.getData().get(0).getLastPage());
                } else {
                   // textErrorMessage.setText(stringNoMessages);
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
            recyclerMessages.setLayoutManager(mLinearLayoutManager);
            recyclerMessages.addItemDecoration(new SimpleDividerItemDecoration(InboxActivity.this));
            inboxAdapter = new InboxAdapter(InboxActivity.this, mMessagesList, recyclerMessages);
            recyclerMessages.setAdapter(inboxAdapter);
            recyclerMessages.scrollToPosition(datas.size() - 1);
            inboxAdapter.setOnLoadMoreListener(new InboxAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    mCurrentPage++;
                    if (inboxAdapter != null && inboxAdapter.isLoading)
                        getMessages(mUserId);
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

    private ArrayList<MessageModel> getDatas() {
        datas.add(new MessageModel("jsbdjsbj", false));
        datas.add(new MessageModel("Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a gal", false));
        datas.add(new MessageModel("jsbdjsbj", true));
        datas.add(new MessageModel("jsbdjsbj", true));
        datas.add(new MessageModel("Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a gal", false));
        datas.add(new MessageModel("jsbdjsbj", true));
        datas.add(new MessageModel("jsbdjsbj", false));
        datas.add(new MessageModel("Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a gal", false));
        datas.add(new MessageModel("jsbdjsbj", true));
        datas.add(new MessageModel("jsbdsnf,dsmfsddjsbj", true));
        datas.add(new MessageModel("jsdsmnfm,dbdjsbj", true));
        datas.add(new MessageModel("jsmdsnm,sadnbdjsbj", true));

        return datas;
    }

    private void setFonts() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, editWriteMsg, buttonSaveAsDraft);
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, buttonSaveAsDraft);
    }

    public class MessageModel {
        String msg;
        boolean isSentMsg;

        MessageModel(String msg, boolean isSentMsg) {
            this.msg = msg;
            this.isSentMsg = isSentMsg;
        }

        public String getMsg() {
            return msg;
        }

        public boolean isSentMsg() {
            return isSentMsg;
        }
    }
}
