package com.sbro.palo.Activities.MovieDetail;

import android.content.Context;
import android.content.res.Resources;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public interface MovieInterface {
    void showMovie(String id, String language);
    void getArtist(String idMovie, int role);
    void showRating(String idUser, String idMovie, Context context, Resources resources);
    void actionRating(float rating, String idUser, String idMovie);
    void showReviews(String id, String title, String poster);
}
