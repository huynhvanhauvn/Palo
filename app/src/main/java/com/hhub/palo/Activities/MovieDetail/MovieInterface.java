package com.hhub.palo.Activities.MovieDetail;

import android.content.Context;
import android.content.res.Resources;

public interface MovieInterface {
    void showMovie(String id, String language, String idUser);
    void getArtist(String idMovie, int role);
    void showRating(String idUser, String idMovie, Context context, Resources resources);
    void actionRating(float rating, String idUser, String idMovie);
    void showReviews(String id, String title, String poster);
    void getCategory(String idMovie);
    void getReward(String idMovie);
}
