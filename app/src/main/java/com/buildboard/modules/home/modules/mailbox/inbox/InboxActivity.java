package com.buildboard.modules.home.modules.mailbox.inbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.buildboard.R;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.mailbox.inbox.adapters.InboxAdapter;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InboxActivity extends AppCompatActivity {

    private final ArrayList<MessageModel> datas = new ArrayList<>();
    private InboxAdapter inboxAdapter;
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

    @BindString(R.string.inbox)
    String stringInbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        ButterKnife.bind(this);

        toolbar.setTitle(stringInbox);
        setFonts();
        setInboxRecycler();
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

    private void setInboxRecycler() {
        inboxAdapter = new InboxAdapter(this, getDatas());
        recyclerMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerMessages.setAdapter(inboxAdapter);

        recyclerMessages.scrollToPosition(datas.size() - 1);
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
