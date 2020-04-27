package com.sbro.palo.Activities.MovieDetail;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sbro.palo.Adapter.ArtistAdapter;
import com.sbro.palo.Models.Artist;
import com.sbro.palo.Models.Movie;

import java.util.ArrayList;

public interface MovieView {
    void showMovie(Movie movie);
    void getArtist(ArtistAdapter adapter, RecyclerView recycler, TextView textView);
    void showRating();
    void updateRating(String rating);
}
