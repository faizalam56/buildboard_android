package com.buildboard.modules.home.modules.drafts_reply;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.buildboard.R;
import com.buildboard.fonts.FontHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DraftReplyFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.edit_draft_msg)
    EditText editDraftMsg;
    @BindView(R.id.button_next)
    Button buttonNext;

    public static DraftReplyFragment newInstance() {
        DraftReplyFragment fragment = new DraftReplyFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_draft_reply, container, false);
        unbinder = ButterKnife.bind(this, view);

        setFont();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, editDraftMsg, buttonNext);
    }
}
