package com.hhub.palo.Activities.ReviewActivity;

import com.hhub.palo.Models.Movie;
import com.hhub.palo.Models.Review;

public interface ReviewView {
    void showMovieInfo(Movie movie);
    void showReview(Review review);
    void updateReviewTextSuccess(String idReview);
    void sendReviewSuccess(String idReview);
    void uploadImageSuccess();
}
