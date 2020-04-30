package com.sbro.palo.Activities.ReviewDetailActivity;

import com.sbro.palo.Models.Review;

public interface ReviewDetailView {
    void showReview(Review review);
    void enableVote(String idUser, String idReview);
    void voteSuccess();
}
