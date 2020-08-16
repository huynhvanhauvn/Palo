package com.hhub.palo.Activities.MovieList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hhub.palo.Activities.MovieDetail.MovieDetailActivity;
import com.hhub.palo.Adapter.RecentAdapter;
import com.hhub.palo.Models.Background;
import com.hhub.palo.Models.Movie;
import com.hhub.palo.R;

import java.util.ArrayList;

public class MovieListActivity extends AppCompatActivity implements MovieListView {

    public static final String TYPE = "type";
    public static final String KEY = "key";
    public static final String TITLE = "title";
    public static final int TYPE_RECENT = 432;
    public static final int TYPE_BEST = 433;
    public static final int TYPE_NATION = 235;
    public static final int TYPE_CATEGORY = 408;
    private RecyclerView recycler;
    private MovieListPresenter presenter;
    private ConstraintLayout layout;
    private TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        getSupportActionBar().hide();
        presenter = new MovieListPresenter(this);

        recycler = (RecyclerView) findViewById(R.id.movList_recycler);
        layout = (ConstraintLayout) findViewById(R.id.movList_layout);
        txtTitle = (TextView) findViewById(R.id.movlist_txt_title);

        presenter.getBackground();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        int type = intent.getIntExtra(TYPE, TYPE_RECENT);
        switch (type) {
            case TYPE_RECENT:
                txtTitle.setText(getResources().getString(R.string.home_recent));
                break;
            case TYPE_BEST:
                txtTitle.setText(getResources().getString(R.string.home_best));
                break;
            case TYPE_NATION:
                txtTitle.setText(intent.getStringExtra(KEY) != null ? intent.getStringExtra(KEY) : "");
                break;
            case TYPE_CATEGORY:
                txtTitle.setText(intent.getStringExtra(TITLE) != null ? intent.getStringExtra(TITLE) : "");
                break;
        }
        presenter.showList(type, intent.getStringExtra(KEY));
    }

    @Override
    public void showList(final ArrayList<Movie> movies) {
        RecentAdapter adapter = new RecentAdapter(getApplicationContext(), movies);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecentAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                intent.putExtra("id", movies.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void showBackground(Background background) {
        Glide.with(getApplicationContext()).load(background.getImage()).centerCrop().into(new CustomTarget<Drawable>() {

            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                layout.setBackground(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
    }
}
