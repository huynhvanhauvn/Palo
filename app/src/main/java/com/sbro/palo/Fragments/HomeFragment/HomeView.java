package com.sbro.palo.Fragments.HomeFragment;

import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.Models.Promotion;

import java.util.ArrayList;

public interface HomeView {
    void showHeader(ArrayList<Promotion> promotions);
    void showBackground(Background background);
    void showRecentMovie(ArrayList<Movie> movies);
    void showBestMovie(ArrayList<Movie> movies);
}
