package com.sbro.palo.Activities.MyReviewActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sbro.palo.Activities.ReviewActivity.ReviewActivity;
import com.sbro.palo.Activities.ReviewDetailActivity.ReviewDetailActivity;
import com.sbro.palo.Activities.WelcomeActivity;
import com.sbro.palo.Adapter.ReviewItemAdapter;
import com.sbro.palo.Models.Quote;
import com.sbro.palo.R;

import java.util.ArrayList;

public class MyReviewActivity extends AppCompatActivity implements MyReviewView {

    private MyReviewPresenter presenter;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);
        getSupportActionBar().hide();

        recycler = (RecyclerView) findViewById(R.id.myreview_recycler);

        presenter = new MyReviewPresenter(this);
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
}
