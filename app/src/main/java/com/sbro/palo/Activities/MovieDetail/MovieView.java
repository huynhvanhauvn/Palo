package com.sbro.palo.Activities.MovieDetail;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sbro.palo.Adapter.ArtistAdapter;
import com.sbro.palo.Models.Artist;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.Models.Quote;

import java.util.ArrayList;

public interface MovieView {
    void showMovie(Movie movie);
    void getArtist(ArrayList<Artist> artists, int role);
    void showRating();
    void updateRating(String rating);
    void showReviews(ArrayList<Quote> quotes, String title, String poster);
}
