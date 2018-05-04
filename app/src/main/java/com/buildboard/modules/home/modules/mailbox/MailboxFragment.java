package com.buildboard.modules.home.modules.mailbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.mailbox.draft.drafts.DraftsActivity;
import com.buildboard.modules.home.modules.mailbox.inbox.InboxActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MailboxFragment extends Fragment {

    private Unbinder mUnbinder;

    @BindView(R.id.text_compose)
    TextView textCompose;
    @BindView(R.id.text_inbox)
    TextView textInbox;
    @BindView(R.id.text_draft)
    TextView textDraft;
    @BindView(R.id.text_bin)
    TextView textBin;

    public static MailboxFragment newInstance() {
        MailboxFragment fragment = new MailboxFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mailbox, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        setFont();

        return view;
    }

    @OnClick(R.id.text_draft)
    void draftsTapped() {
        startActivity(new Intent(getActivity(), DraftsActivity.class));
    }

    @OnClick(R.id.text_inbox)
    void inboxTapped() {
        startActivity(new Intent(getActivity(), InboxActivity.class));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, textCompose, textInbox, textDraft, textBin);
    }
}
