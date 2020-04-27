package com.sbro.palo.Activities.ReviewActivity;

import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.Movie;

public class ReviewPresenter implements ReviewInterface {
    private ReviewView view;

    public ReviewPresenter(ReviewView view) {
        this.view = view;
    }

    @Override
    public void showMovieInfo(Movie movie) {
        view.showMovieInfo(movie);
    }
}
