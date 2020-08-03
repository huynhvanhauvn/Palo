package com.hhub.palo.Activities.MovieDetail;

import com.hhub.palo.Models.Artist;
import com.hhub.palo.Models.Movie;
import com.hhub.palo.Models.Quote;

import java.util.ArrayList;

public interface MovieView {
    void showMovie(Movie movie);
    void getArtist(ArrayList<Artist> artists, int role);
    void showRating();
    void updateRating(String rating);
    void showReviews(ArrayList<Quote> quotes, String title, String poster);
}
