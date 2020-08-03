package com.hhub.palo.Activities.ReviewDetailActivity;

import com.hhub.palo.Models.Review;

public interface ReviewDetailView {
    void showReview(Review review);
    void enableVote(String idUser, String idReview);
    void voteSuccess();
}
