package com.sbro.palo.Activities.MovieDetail;

import android.content.Context;
import android.content.res.Resources;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sbro.palo.Models.Movie;

public interface MovieInterface {
    void showMovie(String id);
    void getArtist(Context context, String[] ids, RecyclerView recycler, TextView textView);
    void showRating(String idUser, String idMovie, Context context, Resources resources);
    void actionRating(float rating, String idUser, String idMovie);
}
