package com.sbro.palo.Activities.ReviewActivity;

import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.Models.Review;

public interface ReviewView {
    void showMovieInfo(Movie movie);
    void showReview(Review review);
}
