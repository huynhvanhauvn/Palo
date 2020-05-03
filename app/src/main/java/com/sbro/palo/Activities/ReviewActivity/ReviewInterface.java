package com.sbro.palo.Activities.ReviewActivity;

import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.Models.Review;

public interface ReviewInterface {
    void showMovieInfo(Movie movie);
    void getReview(String idReview);
}
