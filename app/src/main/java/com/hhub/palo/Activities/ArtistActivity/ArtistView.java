package com.hhub.palo.Activities.ArtistActivity;

import com.hhub.palo.Models.Artist;
import com.hhub.palo.Models.Movie;

import java.util.ArrayList;

public interface ArtistView {
    void showArtist(Artist artist);
    void showDirectList(ArrayList<Movie> movies);
    void showWriteList(ArrayList<Movie> movies);
    void showActList(ArrayList<Movie> movies);
}
