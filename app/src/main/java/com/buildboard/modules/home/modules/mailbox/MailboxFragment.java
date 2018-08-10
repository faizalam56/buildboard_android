package com.buildboard.modules.home.modules.mailbox;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.mailbox.adapters.MessagesAdapter;
import com.buildboard.modules.home.modules.mailbox.inbox.InboxActivity;
import com.buildboard.modules.home.modules.mailbox.inbox.TrashActivity;
import com.buildboard.modules.home.modules.mailbox.modules.models.ConsumerRelatedResponse;
import com.buildboard.modules.home.modules.mailbox.models.MessageData;
import com.buildboard.modules.home.modules.mailbox.models.MessagesResponse;
import com.buildboard.modules.home.modules.mailbox.modules.models.ContractorRelatedResponse;
import com.buildboard.modules.home.modules.marketplace.contractor_projecttype.models.ContractorByProjectTypeListData;
import com.buildboard.modules.home.modules.profile.consumer.EditProfileActivity;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;
import com.buildboard.view.SimpleDividerItemDecoration;
import com.buildboard.view.SnackBarFactory;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MailboxFragment extends Fragment implements AppConstant {


    private Unbinder mUnbinder;
    private int mCurrentPage = 1;
    private ArrayList<MessageData> mMessagesList = new ArrayList<>();
    private MessagesAdapter mMessagesAdapter;
    public EditProfileActivity.UpdateProfileListener mUpdateProfileListener;
    private boolean isSwiped=false;
    private int mTextCount;

    @BindView(R.id.recycler_messages)
    RecyclerView recyclerMessages;
    @BindView(R.id.progress_messages)
    ProgressBar progressMessages;
    @BindView(R.id.text_error_message)
    BuildBoardTextView textErrorMessage;
    @BindView(R.id.relative_root)
    RelativeLayout relativeLayout;
    @BindView(R.id.swipe_root)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.button_trash)
    LinearLayout layoutTrash;
    @BindView(R.id.text_trashcount)
    TextView textTrashCount;

    @BindString(R.string.internet_connection_check)
    String stringNoInternet;
    @BindString(R.string.error_no_messages)
    String stringNoMessages;

    public static MailboxFragment newInstance() {
        MailboxFragment fragment = new MailboxFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mailbox, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        boolean isNetworkConnected = ConnectionDetector.isNetworkConnected(getActivity());

        if (recyclerMessages != null) {
            recyclerMessages.setVisibility(isNetworkConnected ? View.VISIBLE : View.INVISIBLE);
            textErrorMessage.setVisibility(isNetworkConnected ? View.INVISIBLE : View.VISIBLE);
        }
        if (isNetworkConnected) getMessages();
        else
            textErrorMessage.setText(stringNoInternet);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateChat();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.fab)
    public void addChat() {
        if (ConnectionDetector.isNetworkConnected(getActivity())) {
            Intent intent = new Intent(getActivity(), ContactListActivity.class);
            startActivity(intent);
        } else {
            ConnectionDetector.createSnackBar(getActivity(), relativeLayout);
        }
    }

    private void getMessages() {

       if(!isSwiped)
           ProgressHelper.showProgressBar(getActivity(), progressMessages);

        DataManager.getInstance().getMessages(getActivity(), new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {

                if(isSwiped) mMessagesAdapter=null;

                isSwiped=false;
                ProgressHelper.hideProgressBar();
                if (response == null) return;

                MessagesResponse messagesResponse = (MessagesResponse) response;

                if (isAdded()) {
                    swipeRefreshLayout.setRefreshing(false);
                    boolean isMessageAvailable = messagesResponse.getData().get(0).getData().size() > 0;

                    if (recyclerMessages != null) {
                        recyclerMessages.setVisibility(isMessageAvailable ? View.VISIBLE : View.INVISIBLE);
                        textErrorMessage.setVisibility(isMessageAvailable ? View.INVISIBLE : View.VISIBLE);
                    }
                    if (messagesResponse != null && messagesResponse.getData().get(0).getData() != null &&
                            messagesResponse.getData().get(0).getData().size() > 0) {
                        setRecycler(messagesResponse.getData().get(0).getData(),
                                messagesResponse.getData().get(0).getLastPage());
                        mTextCount = messagesResponse.getData().get(0).getTrashCount();
                        textTrashCount.setText(String.valueOf(mTextCount));
                    } else {
                        textErrorMessage.setText(stringNoMessages);
                    }
                }
            }
            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
                Utils.showError(getActivity(), relativeLayout, error);
                isSwiped=false;
            }
        });
    }

    private void setRecycler(ArrayList<MessageData> messageData, int lastPage) {
        mMessagesList=new ArrayList<>();
        mMessagesList.addAll(messageData);

        if (mMessagesAdapter == null) {
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerMessages.setLayoutManager(mLinearLayoutManager);
            recyclerMessages.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
            mMessagesAdapter = new MessagesAdapter(getActivity(), mMessagesList, recyclerMessages);
            recyclerMessages.setAdapter(mMessagesAdapter);
            mMessagesAdapter.setOnLoadMoreListener(new MessagesAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    mCurrentPage++;
                    if (mMessagesAdapter != null && mMessagesAdapter.isLoading)
                        getMessages();
                }
            });
        } else {
            mMessagesAdapter.notifyDataSetChanged();
            recyclerMessages.getAdapter().notifyItemInserted((mMessagesList.size()));
        }

        if (mMessagesAdapter != null) {
            mMessagesAdapter.setLoading(false);
            if (mCurrentPage == lastPage)
                mMessagesAdapter.setLastPage(true);
        }
    }

    @OnClick(R.id.button_trash)
    public void trashTapped() {
        if (ConnectionDetector.isNetworkConnected(getActivity())) {

            if (mTextCount != 0) {
                Intent intent = new Intent(getActivity(), TrashActivity.class);
                getActivity().startActivity(intent);
            } else {
                SnackBarFactory.createSnackBar(getActivity(), relativeLayout, getString(R.string.no_trash_yet));
            }
        } else {
            ConnectionDetector.createSnackBar(getActivity(), relativeLayout);
        }
    }

    public void updateChat() {
        if (ConnectionDetector.isNetworkConnected(getActivity())) {
            isSwiped = true;
            getMessages();
        } else {
            swipeRefreshLayout.setRefreshing(false);
            ConnectionDetector.createSnackBar(getActivity(), swipeRefreshLayout);
        }
    }
  }
