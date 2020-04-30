package com.sbro.palo.Activities.ReviewDetailActivity;

public interface ReviewDetailInterface {
    void showReview(String id);
    void enableVote(String idUser, String idReview);
    void rating(float rating, String idUser, String idReview);
}
