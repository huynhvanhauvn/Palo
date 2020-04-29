package com.sbro.palo.Activities.MovieList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sbro.palo.Activities.MovieDetail.MovieDetailActivity;
import com.sbro.palo.Adapter.RecentAdapter;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.R;

import java.util.ArrayList;

public class MovieListActivity extends AppCompatActivity implements MovieListView {

    public static final String TYPE = "type";
    public static final int TYPE_RECENT = 432;
    private RecyclerView recycler;
    private MovieListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        getSupportActionBar().hide();
        presenter = new MovieListPresenter(this);

        recycler = (RecyclerView) findViewById(R.id.movList_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        int type = intent.getIntExtra(TYPE, TYPE_RECENT);
        presenter.showList(type);
    }

    @Override
    public void showList(final ArrayList<Movie> movies) {
        RecentAdapter adapter = new RecentAdapter(getApplicationContext(),movies);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecentAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                intent.putExtra("id",movies.get(position).getId());
                startActivity(intent);
            }
        });
    }
}
