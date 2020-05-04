package com.sbro.palo.Activities.MyReviewActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sbro.palo.Activities.ReviewActivity.ReviewActivity;
import com.sbro.palo.Activities.ReviewDetailActivity.ReviewDetailActivity;
import com.sbro.palo.Activities.WelcomeActivity;
import com.sbro.palo.Adapter.ReviewItemAdapter;
import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.Quote;
import com.sbro.palo.R;

import java.util.ArrayList;

public class MyReviewActivity extends AppCompatActivity implements MyReviewView {

    private MyReviewPresenter presenter;
    private RecyclerView recycler;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);
        getSupportActionBar().hide();

        recycler = (RecyclerView) findViewById(R.id.myreview_recycler);
        layout = (ConstraintLayout) findViewById(R.id.myreview_layout);

        presenter = new MyReviewPresenter(this);
        presenter.getBackground();
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(WelcomeActivity.SHARED_DATA,
                Context.MODE_PRIVATE);
        String id = preferences.getString(WelcomeActivity.USER_ID,"");
        if(id != null) {
            presenter.getListReview(id);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recycler.setLayoutManager(layoutManager);
    }

    @Override
    public void showReviewList(final ArrayList<Quote> quotes) {
        ReviewItemAdapter adapter = new ReviewItemAdapter(getApplicationContext(),quotes);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new ReviewItemAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(MyReviewActivity.this, ReviewActivity.class);
                intent.putExtra(ReviewDetailActivity.ID,quotes.get(position).getId());
                intent.putExtra(ReviewDetailActivity.TITLE,quotes.get(position).getTitle());
                intent.putExtra(ReviewDetailActivity.POSTER,quotes.get(position).getPoster());
                startActivity(intent);
            }
        });
    }

    @Override
    public void showBackground(Background background) {
        Glide.with(getApplicationContext()).load(background.getImage()).centerCrop().into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                layout.setBackground(resource);
            }
        });
    }
}
