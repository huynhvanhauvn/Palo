package com.hhub.palo.Activities.MovieDetail;

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
import com.hhub.palo.Activities.MovieList.MovieListActivity;
import com.hhub.palo.Adapter.AwardAdapter;
import com.hhub.palo.Models.Category;
import com.hhub.palo.Models.Reward;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.hhub.palo.Activities.ArtistActivity.ArtistActivity;
import com.hhub.palo.Activities.ReviewActivity.ReviewActivity;
import com.hhub.palo.Activities.ReviewDetailActivity.ReviewDetailActivity;
import com.hhub.palo.Activities.WelcomeActivity;
import com.hhub.palo.Adapter.ArtistAdapter;
import com.hhub.palo.Adapter.RecentAdapter;
import com.hhub.palo.Adapter.ReviewAdapter;
import com.hhub.palo.Models.Artist;
import com.hhub.palo.Models.Movie;
import com.hhub.palo.Models.Quote;
import com.hhub.palo.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

public class MovieDetailActivity extends AppCompatActivity implements MovieView {

    private YouTubePlayerView playerView;
    private MoviePresenter presenter;
    private ConstraintLayout layout, ratingLayout, ratingPoint, title;
    private TextView txtTitle, txtRating, txtDescription,
            txtLabelDescription, txtDirector, txtWriter, txtCast,
            txtDateLabel, txtDate, txtNationLabel, txtNation, txtReviewsLabel, txtCategoryLabel;
    private RecyclerView recyclerDirector, recyclerWriter, recyclerCast, recyclerQuote, recyclerReward;
    private RatingBar ratingBar;
    private CardView cardSend;
    private TagContainerLayout tagCategory;

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
        txtReviewsLabel = (TextView) findViewById(R.id.detail_txt_quote_label);
        recyclerQuote = (RecyclerView) findViewById(R.id.detail_recycler_quote);
        txtCategoryLabel = (TextView) findViewById(R.id.detail_txt_category_label);
        tagCategory = (TagContainerLayout) findViewById(R.id.detail_tag_category);
        recyclerReward = (RecyclerView) findViewById(R.id.detail_recycler_reward);

        ratingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingLayout.setVisibility(View.GONE);
            }
        });

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        if(id != null) {
            Locale.getDefault().getLanguage();
            SharedPreferences preferences = getSharedPreferences(WelcomeActivity.SHARED_DATA, Context.MODE_PRIVATE);
            String idUser = preferences.getString(WelcomeActivity.USER_ID,"");
            if(!idUser.equals("")) {
                presenter.showMovie(id,Locale.getDefault().getLanguage(),idUser);
            }
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
        if(movie.getDescription() != null && !movie.getDescription().equals("")) {
            txtLabelDescription.setVisibility(View.VISIBLE);
            txtDescription.setText(movie.getDescription());
        }
        showArtist(movie.getId(),1);
        showArtist(movie.getId(),2);
        showArtist(movie.getId(),3);
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
        if(movie.getId() != null && !movie.getId().equals("")) {
            presenter.showReviews(movie.getId(),movie.getTitle(),movie.getPoster());
            presenter.getCategory(movie.getId());
            presenter.getReward(movie.getId());
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
    public void getArtist(ArrayList<Artist> artists, int role) {
        switch (role) {
            case 1:
                setArtist(artists,recyclerDirector,txtDirector);
                break;
            case 2:
                setArtist(artists,recyclerWriter,txtWriter);
                break;
            case 3:
                setArtist(artists,recyclerCast,txtCast);
                break;
        }
    }

    private void setArtist(ArrayList<Artist> artists, RecyclerView recycler, TextView textView) {
        textView.setVisibility(View.VISIBLE);
        recycler.setNestedScrollingEnabled(false);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recycler.setLayoutManager(layoutManager);
        final ArtistAdapter adapter = new ArtistAdapter(getApplicationContext(),artists);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecentAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(MovieDetailActivity.this, ArtistActivity.class);
                intent.putExtra(ArtistActivity.ID,adapter.getArtists().get(position).getId());
                startActivity(intent);
            }
        });
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

    @Override
    public void showReviews(final ArrayList<Quote> quotes, final String title, final String poster) {
        if(quotes.size()>0) {
            txtReviewsLabel.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            ReviewAdapter adapter = new ReviewAdapter(getApplicationContext(),quotes);
            recyclerQuote.setLayoutManager(layoutManager);
            recyclerQuote.setAdapter(adapter);
            adapter.setOnItemClickListener(new ReviewAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, int position) {
                    Intent intent = new Intent(MovieDetailActivity.this, ReviewDetailActivity.class);
                    intent.putExtra(ReviewDetailActivity.ID,quotes.get(position).getId());
                    intent.putExtra(ReviewDetailActivity.TITLE,title);
                    intent.putExtra(ReviewDetailActivity.POSTER,poster);
                    intent.putExtra(ReviewDetailActivity.AUTHOR,quotes.get(position).getAuthor());
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void showCategory(final ArrayList<String> categorytags, final ArrayList<Category> categories) {
        txtCategoryLabel.setVisibility(View.VISIBLE);
        tagCategory.setTags(categorytags);
        tagCategory.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                Intent intent = new Intent(MovieDetailActivity.this, MovieListActivity.class);
                intent.putExtra(MovieListActivity.TYPE, MovieListActivity.TYPE_CATEGORY);
                intent.putExtra(MovieListActivity.KEY, categories.get(position).getId());
                intent.putExtra(MovieListActivity.TITLE, categorytags.get(position));
                startActivity(intent);
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
    public void showReward(ArrayList<Reward> rewards) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerReward.setLayoutManager(layoutManager);
        AwardAdapter adapter = new AwardAdapter(rewards, getApplicationContext());
        recyclerReward.setAdapter(adapter);
        recyclerReward.setVisibility(View.VISIBLE);
    }

    private void showArtist(String idMovie, int role) {
        if(!idMovie.equals("")) {
            presenter.getArtist(idMovie,role);
        }
    }
}
