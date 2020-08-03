package com.hhub.palo.Fragments.HomeFragment;

import com.hhub.palo.Models.Artist;
import com.hhub.palo.Models.Background;
import com.hhub.palo.Models.Movie;
import com.hhub.palo.Models.Promotion;

import java.util.ArrayList;

public interface HomeView {
    void showHeader(ArrayList<Promotion> promotions);
    void showBackground(Background background);
    void showRecentMovie(ArrayList<Movie> movies);
    void showBestMovie(ArrayList<Movie> movies);
    void showArtistBirthday(ArrayList<Artist> artists);
}
