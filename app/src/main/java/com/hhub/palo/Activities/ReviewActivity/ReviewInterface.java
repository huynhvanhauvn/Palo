package com.hhub.palo.Activities.ReviewActivity;

import okhttp3.MultipartBody;

public interface ReviewInterface {
    void showMovieInfo(String id);
    void getReview(String idReview);
    void sendReview(String idUser, String idMovie, String intro, String story, String act, String pic, String sound, String feel,
                    String msg, String end);
    void updateReview(String id, String intro, String story, String act, String pic, String sound, String feel,
                      String msg, String end);
    void uploadImage(MultipartBody.Part body, String idReview, String type);
    void updateImage(MultipartBody.Part body, String idReview, String type, String oldFile);
}
