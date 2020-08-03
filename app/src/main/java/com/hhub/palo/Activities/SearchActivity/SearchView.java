package com.hhub.palo.Activities.SearchActivity;

import com.hhub.palo.Models.Artist;
import com.hhub.palo.Models.Background;
import com.hhub.palo.Models.Movie;

import java.util.ArrayList;

public interface SearchView {
    void showPopularTags(ArrayList<String> tags);
    void showResult(ArrayList<Movie> movies);
    void showArtists(ArrayList<Artist> artists);
    void showBackground(Background background);
}
