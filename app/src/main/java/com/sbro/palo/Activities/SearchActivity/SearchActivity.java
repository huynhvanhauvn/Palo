package com.sbro.palo.Activities.SearchActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sbro.palo.Activities.MovieDetail.MovieDetailActivity;
import com.sbro.palo.Adapter.RecentAdapter;
import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.R;

import java.util.ArrayList;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

public class SearchActivity extends AppCompatActivity implements SearchView {

    private androidx.appcompat.widget.SearchView searchView;
    private TagContainerLayout tagView;
    private RecyclerView recyclerView;
    private SearchPresenter presenter;
    private RecentAdapter adapter;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();

        layout = (ConstraintLayout) findViewById(R.id.search_layout);
        searchView = (androidx.appcompat.widget.SearchView) findViewById(R.id.search_input);
        tagView = (TagContainerLayout) findViewById(R.id.search_popular);
        recyclerView = (RecyclerView) findViewById(R.id.search_recycler);
        presenter = new SearchPresenter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        presenter.showBackground();
        tagView.setTagBackgroundColor(Color.TRANSPARENT);
        presenter.getPopularTags();
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.updateKeyword(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.search(newText);
                return false;
            }
        });
        searchView.setOnCloseListener(new androidx.appcompat.widget.SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                recyclerView.setVisibility(View.GONE);
                tagView.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }

    @Override
    public void showPopularTags(ArrayList<String> tags) {
        tagView.setTags(tags);
        tagView.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                searchView.setQuery(text,true);
            }

            @Override
            public void onTagLongClick(int position, String text) {

            }

            @Override
            public void onSelectedTagDrag(int position, String text) {

            }

            @Override
            public void onTagCrossClick(int position) {

            }
        });
    }

    @Override
    public void showResult(final ArrayList<Movie> movies) {
        if(movies.size()>0) {
            tagView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new RecentAdapter(getApplicationContext(),movies);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new RecentAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, int position) {
                    Intent intent = new Intent(SearchActivity.this, MovieDetailActivity.class);
                    intent.putExtra("id",movies.get(position).getId());
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            recyclerView.setVisibility(View.GONE);
            tagView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showBackground(Background background) {
        Glide.with(getApplicationContext()).load(background.getImage()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                layout.setBackground(resource);
            }
        });
    }
}
