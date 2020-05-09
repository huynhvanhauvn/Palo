package com.sbro.palo.Activities.SearchActivity;

import com.sbro.palo.Models.Artist;
import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.Movie;

import java.util.ArrayList;

public interface SearchView {
    void showPopularTags(ArrayList<String> tags);
    void showResult(ArrayList<Movie> movies);
    void showArtists(ArrayList<Artist> artists);
    void showBackground(Background background);
}
