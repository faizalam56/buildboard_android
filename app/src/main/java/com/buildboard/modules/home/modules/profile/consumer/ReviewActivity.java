package com.buildboard.modules.home.modules.profile.consumer;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.profile.consumer.adapter.ReviewsAdapter;
import com.buildboard.modules.home.modules.profile.consumer.models.reviews.ReviewData;
import com.buildboard.modules.home.modules.profile.consumer.models.reviews.ReviewsResponse;
import com.buildboard.modules.signup.imageupload.ImageUploadActivity;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;
import com.buildboard.view.SimpleDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewActivity extends AppCompatActivity {

    private ReviewsAdapter mReviewAdapter;
    private int mCurrentPage = 1;
    private ArrayList<ReviewData> mReviewsList = new ArrayList<>();

    @BindView(R.id.title)
    BuildBoardTextView textTitle;
    @BindView(R.id.recycler_reviews)
    RecyclerView recyclerReviews;
    @BindView(R.id.text_no_record_found)
    BuildBoardTextView textNoDataFound;
    @BindView(R.id.constraint_root)
    ConstraintLayout constraintLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindString(R.string.reviews)
    String stringTitle;
    @BindString(R.string.msg_please_wait)
    String stringPleaseWait;
    @BindString(R.string.no_internet_text)
    String stringNoInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);

        textTitle.setText(stringTitle);

        if (ConnectionDetector.isNetworkConnected(this)) {
            getReviews();
        } else {
            textNoDataFound.setText(stringNoInternet);
            ConnectionDetector.createSnackBar(this, constraintLayout);
        }
    }

    private void getReviews() {
        ProgressHelper.showProgressBar(this, progressBar);
        DataManager.getInstance().getReviews(this, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                if (response == null) return;

                ReviewsResponse reviewsResponse = (ReviewsResponse) response;
                if (reviewsResponse.getData().get(0).getData() != null)
                    setRecycler(reviewsResponse.getData().get(0).getData(),
                            reviewsResponse.getData().get(0).getLastPage());

                recyclerReviews.setVisibility(reviewsResponse.getData().get(0).getData() != null ?
                        View.VISIBLE : View.GONE);
                textNoDataFound.setVisibility(reviewsResponse.getData().get(0).getData() != null ?
                        View.GONE : View.VISIBLE);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
                Utils.showError(ReviewActivity.this, constraintLayout, error);
            }
        });
    }

    private void setRecycler(ArrayList<ReviewData> reviewData, int lastPage) {
        mReviewsList.addAll(reviewData);

        if (mReviewAdapter == null) {
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
            recyclerReviews.setLayoutManager(mLinearLayoutManager);
            mReviewAdapter = new ReviewsAdapter(this, mReviewsList, recyclerReviews);
            recyclerReviews.setAdapter(mReviewAdapter);
            mReviewAdapter.setOnLoadMoreListener(new ReviewsAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    mCurrentPage++;
                    if (mReviewAdapter != null && mReviewAdapter.isLoading)
                        getReviews();
                }
            });
        } else {
            recyclerReviews.getAdapter().notifyItemInserted((mReviewsList.size()));
        }

        if (mReviewAdapter != null) {
            mReviewAdapter.setLoading(false);
            if (mCurrentPage == lastPage)
                mReviewAdapter.setLastPage(true);
        }
    }
}