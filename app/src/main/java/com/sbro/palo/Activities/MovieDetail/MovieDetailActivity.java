package com.sbro.palo.Activities.MovieDetail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.sbro.palo.Activities.ReviewActivity.ReviewActivity;
import com.sbro.palo.Activities.WelcomeActivity;
import com.sbro.palo.Adapter.ArtistAdapter;
import com.sbro.palo.Models.Artist;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.R;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity implements MovieView {

    private YouTubePlayerView playerView;
    private MoviePresenter presenter;
    private ConstraintLayout layout, ratingLayout, ratingPoint, title;
    private TextView txtTitle, txtRating, txtDescription,
            txtLabelDescription, txtDirector, txtWriter, txtCast,
            txtDateLabel, txtDate, txtNationLabel, txtNation;
    private RecyclerView recyclerDirector, recyclerWriter, recyclerCast;
    private RatingBar ratingBar;
    private CardView cardSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getSupportActionBar().hide();

        presenter = new MoviePresenter(this);
        playerView = (YouTubePlayerView) findViewById(R.id.detail_youtube);
        layout = (ConstraintLayout) findViewById(R.id.detail_layout);
        txtTitle = (TextView) findViewById(R.id.detail_txt_title);
        recyclerDirector = (RecyclerView) findViewById(R.id.detail_recycler_director);
        recyclerWriter = (RecyclerView) findViewById(R.id.detail_recycler_writer);
        recyclerCast = (RecyclerView) findViewById(R.id.detail_recycler_cast);
        txtDescription = (TextView) findViewById(R.id.detail_txt_description);
        txtLabelDescription = (TextView) findViewById(R.id.detail_txt_description_label);
        txtDirector = (TextView) findViewById(R.id.detail_txt_director);
        txtWriter = (TextView) findViewById(R.id.detail_txt_writer);
        txtCast = (TextView) findViewById(R.id.detail_txt_cast);
        txtRating = (TextView) findViewById(R.id.detail_txt_rating);
        ratingLayout = (ConstraintLayout) findViewById(R.id.detail_layout_background);
        ratingPoint = (ConstraintLayout) findViewById(R.id.detail_rating);
        ratingBar = (RatingBar) findViewById(R.id.detail_rating_star);
        cardSend = (CardView) findViewById(R.id.detail_card_send);
        txtDateLabel = (TextView) findViewById(R.id.detail_txt_date_label);
        txtDate = (TextView) findViewById(R.id.detail_txt_date);
        txtNationLabel = (TextView) findViewById(R.id.detail_txt_nation_label);
        txtNation = (TextView) findViewById(R.id.detail_txt_nation);
        title = (ConstraintLayout) findViewById(R.id.detail_title);

        ratingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingLayout.setVisibility(View.GONE);
            }
        });

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        if(id != null) {
            presenter.showMovie(id);
        }
    }

    @Override
    public void showMovie(final Movie movie) {
        Glide.with(getApplicationContext()).load(movie.getPoster()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                layout.setBackground(resource);
            }
        });
        getLifecycle().addObserver(playerView);
        playerView.addYouTubePlayerListener(new YouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(movie.getTrailer(), 0);
                youTubePlayer.play();
            }

            @Override
            public void onStateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlayerState playerState) {

            }

            @Override
            public void onPlaybackQualityChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackQuality playbackQuality) {

            }

            @Override
            public void onPlaybackRateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackRate playbackRate) {

            }

            @Override
            public void onError(YouTubePlayer youTubePlayer, PlayerConstants.PlayerError playerError) {

            }

            @Override
            public void onCurrentSecond(YouTubePlayer youTubePlayer, float v) {

            }

            @Override
            public void onVideoDuration(YouTubePlayer youTubePlayer, float v) {

            }

            @Override
            public void onVideoLoadedFraction(YouTubePlayer youTubePlayer, float v) {

            }

            @Override
            public void onVideoId(YouTubePlayer youTubePlayer, String s) {

            }

            @Override
            public void onApiChange(YouTubePlayer youTubePlayer) {

            }
        });
        txtTitle.setText(movie.getTitle());
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetailActivity.this, ReviewActivity.class);
                intent.putExtra("id",movie.getId());
                startActivity(intent);
            }
        });
        final DecimalFormat format = new DecimalFormat("0.0");
        txtRating.setText(format.format(Float.parseFloat(movie.getRating())));
        if(movie.getDirector() != null && !movie.getDescription().equals("")) {
            txtLabelDescription.setVisibility(View.VISIBLE);
            txtDescription.setText(movie.getDescription());
        }
        showArtist(movie.getDirector(),recyclerDirector, txtDirector);
        showArtist(movie.getWriter(),recyclerWriter, txtWriter);
        showArtist(movie.getCast(),recyclerCast, txtCast);
        if(movie.getDate() != null && !movie.getDate().equals("")) {
            txtDateLabel.setVisibility(View.VISIBLE);
            txtDate.setVisibility(View.VISIBLE);
            txtDate.setText(movie.getDate());
        }
        if(movie.getNation() != null && !movie.getNation().equals("")) {
            txtNationLabel.setVisibility(View.VISIBLE);
            txtNation.setVisibility(View.VISIBLE);
            txtNation.setText(movie.getNation());
        }

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(WelcomeActivity.SHARED_DATA, Context.MODE_PRIVATE);
        final String id = preferences.getString(WelcomeActivity.USER_ID,"");

        ratingPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!id.equals("")) {
                    presenter.showRating(id, movie.getId(), getApplicationContext(), getResources());
                }
            }
        });
        cardSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ratingBar.getRating()>0) {
                    ratingLayout.setVisibility(View.GONE);
                    presenter.actionRating(ratingBar.getRating(),id,movie.getId());
                } else {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.detail_not_rating),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void getArtist(ArtistAdapter adapter, RecyclerView recycler, TextView textView) {
        textView.setVisibility(View.VISIBLE);
        recycler.setNestedScrollingEnabled(false);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);
    }

    @Override
    public void showRating() {
        ratingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateRating(String rating) {
        final DecimalFormat format = new DecimalFormat("0.0");
        txtRating.setText(format.format(Float.parseFloat(rating)));
    }

    private void showArtist(String ids, RecyclerView recycler, TextView textView) {
        if(!ids.equals("")) {
            String[] strings = ids.split("/");
            presenter.getArtist(getApplicationContext(), strings, recycler, textView);
        }
    }


}
