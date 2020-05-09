package com.sbro.palo.Activities.SearchActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sbro.palo.Activities.ArtistActivity.ArtistActivity;
import com.sbro.palo.Activities.MovieDetail.MovieDetailActivity;
import com.sbro.palo.Adapter.ArtistListAdapter;
import com.sbro.palo.Adapter.RecentAdapter;
import com.sbro.palo.Models.Artist;
import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.R;

import java.util.ArrayList;
import java.util.Locale;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

public class SearchActivity extends AppCompatActivity implements SearchView {

    private androidx.appcompat.widget.SearchView searchView;
    private TagContainerLayout tagView;
    private RecyclerView recyclerView, recyclerArtist;
    private SearchPresenter presenter;
    private RecentAdapter adapter;
    private ArtistListAdapter artistAdapter;
    private ConstraintLayout layout;
    private TextView txtMovie, txtArtist;
    private NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();

        layout = (ConstraintLayout) findViewById(R.id.search_layout);
        searchView = (androidx.appcompat.widget.SearchView) findViewById(R.id.search_input);
        tagView = (TagContainerLayout) findViewById(R.id.search_popular);
        recyclerView = (RecyclerView) findViewById(R.id.search_recycler);
        recyclerArtist = (RecyclerView) findViewById(R.id.search_recycler_artist);
        txtMovie = (TextView) findViewById(R.id.search_label_movie);
        txtArtist = (TextView) findViewById(R.id.search_label_artist);
        scrollView = (NestedScrollView) findViewById(R.id.search_scroll);

        presenter = new SearchPresenter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManagerArtist = new LinearLayoutManager(this);
        layoutManagerArtist.setOrientation(RecyclerView.VERTICAL);
        recyclerArtist.setLayoutManager(layoutManagerArtist);

        presenter.showBackground(Locale.getDefault().getLanguage());
        tagView.setTagBackgroundColor(Color.TRANSPARENT);
        presenter.getPopularTags();
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.equals("")) {
                    presenter.updateKeyword(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.equals("")) {
                    presenter.search(newText);
                    presenter.searchArtist(newText);
                } else {
                    scrollView.setVisibility(View.GONE);
                    tagView.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        searchView.setOnCloseListener(new androidx.appcompat.widget.SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                scrollView.setVisibility(View.GONE);
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
            scrollView.setVisibility(View.VISIBLE);
            txtMovie.setVisibility(View.VISIBLE);
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
            txtMovie.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showArtists(final ArrayList<Artist> artists) {
        if(artists.size()>0) {
            tagView.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            txtArtist.setVisibility(View.VISIBLE);
            recyclerArtist.setVisibility(View.VISIBLE);
            artistAdapter = new ArtistListAdapter(getApplicationContext(),artists);
            recyclerArtist.setAdapter(artistAdapter);
            artistAdapter.setOnItemClickListener(new ArtistListAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, int position) {
                    Intent intent = new Intent(SearchActivity.this, ArtistActivity.class);
                    intent.putExtra(ArtistActivity.ID,artists.get(position).getId());
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            txtArtist.setVisibility(View.GONE);
            recyclerArtist.setVisibility(View.GONE);
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
