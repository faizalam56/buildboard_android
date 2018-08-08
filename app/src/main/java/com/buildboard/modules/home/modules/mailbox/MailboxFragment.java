package com.buildboard.modules.home.modules.mailbox;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.mailbox.adapters.MessagesAdapter;
import com.buildboard.modules.home.modules.mailbox.modules.models.ConsumerRelatedResponse;
import com.buildboard.modules.home.modules.mailbox.models.MessageData;
import com.buildboard.modules.home.modules.mailbox.models.MessagesResponse;
import com.buildboard.modules.home.modules.mailbox.modules.models.ContractorRelatedResponse;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;
import com.buildboard.view.SimpleDividerItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MailboxFragment extends Fragment implements AppConstant {

    @BindView(R.id.recycler_messages)
    RecyclerView recyclerMessages;
    @BindView(R.id.progress_messages)
    ProgressBar progressMessages;
    @BindView(R.id.text_error_message)
    BuildBoardTextView textErrorMessage;
    @BindView(R.id.relative_root)
    RelativeLayout relativeLayout;

    @BindString(R.string.internet_connection_check)
    String stringNoInternet;
    @BindString(R.string.error_no_messages)
    String stringNoMessages;

    private Unbinder mUnbinder;
    private int mCurrentPage = 1;
    private ArrayList<MessageData> mMessagesList = new ArrayList<>();
    private MessagesAdapter mMessagesAdapter;

    public static MailboxFragment newInstance() {
        MailboxFragment fragment = new MailboxFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mailbox, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        boolean isNetworkConnected = ConnectionDetector.isNetworkConnected(getActivity());

        recyclerMessages.setVisibility(isNetworkConnected ? View.VISIBLE : View.INVISIBLE);
        textErrorMessage.setVisibility(isNetworkConnected ? View.INVISIBLE : View.VISIBLE);

        if (isNetworkConnected)
            getMessages();
        else textErrorMessage.setText(stringNoInternet);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.fab)
    public void addChat(){

        if (ConnectionDetector.isNetworkConnected(getActivity())) {
            if (AppPreference.getAppPreference(getActivity()).getBoolean(IS_CONTRACTOR)) {
                getContactListForContractor();
            } else {
                getContactListForConsumer();
            }
        } else {
            ConnectionDetector.createSnackBar(getActivity(), relativeLayout);
        }
    }

    private void getContactListForContractor(){
        ProgressHelper.showProgressBar(getActivity(), progressMessages);
        DataManager.getInstance().getRelatedConsumer(getActivity(), new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                if (response == null) return;

                ConsumerRelatedResponse messagesResponse = (ConsumerRelatedResponse) response;

                if (messagesResponse != null && messagesResponse.getData().get(0).getConsumerRelatedData() != null &&
                        messagesResponse.getData().get(0).getConsumerRelatedData().size() > 0) {

                    Intent intent = new Intent(getActivity(), ContactListActivity.class);
                    intent.putExtra(DATA, (Serializable) messagesResponse.getData().get(0).getConsumerRelatedData());
                    intent.putExtra(IS_CONTRACTOR, true);
                    startActivity(intent);
                }
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
                Utils.showError(getActivity(), relativeLayout, error);
            }
        });

    }

    private void getContactListForConsumer() {
        ProgressHelper.showProgressBar(getActivity(), progressMessages);
        DataManager.getInstance().getRelatedContractor(getActivity(), new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                if (response == null) return;

                ContractorRelatedResponse messagesResponse = (ContractorRelatedResponse) response;

                if (messagesResponse != null && messagesResponse.getData().get(0).getConsumerRelatedData() != null &&
                        messagesResponse.getData().get(0).getConsumerRelatedData().size() > 0) {

                    Intent intent = new Intent(getActivity(), ContactListActivity.class);
                    intent.putExtra(DATA, (Serializable) messagesResponse.getData().get(0).getConsumerRelatedData());
                    intent.putExtra(IS_CONTRACTOR, false);
                    startActivity(intent);
                }
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
                Utils.showError(getActivity(), relativeLayout, error);
            }
        });

    }

    private void getMessages() {
        ProgressHelper.showProgressBar(getActivity(), progressMessages);
        DataManager.getInstance().getMessages(getActivity(), new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                if (response == null) return;

                MessagesResponse messagesResponse = (MessagesResponse) response;

                boolean isMessageAvailable = messagesResponse.getData().get(0).getData().size() > 0;
                recyclerMessages.setVisibility(isMessageAvailable ? View.VISIBLE : View.INVISIBLE);
                textErrorMessage.setVisibility(isMessageAvailable ? View.INVISIBLE : View.VISIBLE);

                if (messagesResponse != null && messagesResponse.getData().get(0).getData() != null &&
                        messagesResponse.getData().get(0).getData().size() > 0) {
                    setRecycler(messagesResponse.getData().get(0).getData(),
                            messagesResponse.getData().get(0).getLastPage());
                } else {
                    textErrorMessage.setText(stringNoMessages);
                }
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
                Utils.showError(getActivity(), relativeLayout, error);
            }
        });
    }

    private void setRecycler(ArrayList<MessageData> messageData, int lastPage) {
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
            recyclerMessages.getAdapter().notifyItemInserted((mMessagesList.size()));
        }

        if (mMessagesAdapter != null) {
            mMessagesAdapter.setLoading(false);
            if (mCurrentPage == lastPage)
                mMessagesAdapter.setLastPage(true);
        }
    }
}
