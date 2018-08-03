package com.buildboard.modules.home.modules.profile.consumer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.modules.home.modules.profile.consumer.adapter.ReviewsAdapter;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewActivity extends AppCompatActivity {

    @BindView(R.id.title)
    BuildBoardTextView textTitle;
    @BindView(R.id.recycler_reviews)
    RecyclerView recyclerReviews;

    @BindString(R.string.reviews)
    String stringTitle;

    private ReviewsAdapter mReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);

        textTitle.setText(stringTitle);
        setRecycler();
    }

    private void setRecycler() {
        mReviewAdapter = new ReviewsAdapter(this, recyclerReviews);
        recyclerReviews.setLayoutManager(new LinearLayoutManager(this));
        recyclerReviews.setAdapter(mReviewAdapter);
    }
}
