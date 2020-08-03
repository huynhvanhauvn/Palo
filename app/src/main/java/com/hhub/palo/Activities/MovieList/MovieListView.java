package com.hhub.palo.Activities.MovieList;

import com.hhub.palo.Models.Background;
import com.hhub.palo.Models.Movie;

import java.util.ArrayList;

public interface MovieListView {
    void showList(ArrayList<Movie> movies);
    void showBackground(Background background);
}
