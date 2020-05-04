package com.sbro.palo.Activities.ReviewActivity;

import com.sbro.palo.Models.Movie;
import com.sbro.palo.Models.Review;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import okhttp3.MultipartBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ReviewPresenter implements ReviewInterface {
    private ReviewView view;
    private Service service = APIService.getService();

    public ReviewPresenter(ReviewView view) {
        this.view = view;
    }

    @Override
    public void showMovieInfo(String id) {
        Observable<Movie> movieObservable = service.movieInfo(id);
        movieObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Movie>() {
                    @Override
                    public void call(Movie movie) {
                        if(movie != null) {
                            view.showMovieInfo(movie);
                        }
                    }
                });
    }

    @Override
    public void getReview(String idReview) {
        Observable<Review> observable = service.getReview(idReview);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Review>() {
                    @Override
                    public void call(Review review) {
                        if(review != null) {
                            view.showReview(review);
                        }
                    }
                });
    }

    @Override
    public void sendReview(String idUser, String idMovie, String intro, String story, String act, String pic, String sound, String feel, String msg, String end) {
        Observable<String> observable = service.review(idUser,idMovie,intro,story,"",
                act,"",pic,"",sound,"",feel,"",msg,"",end);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if(s != null && !s.equals("")) {
                            view.sendReviewSuccess(s);
                        }
                    }
                });
    }

    @Override
    public void updateReview(final String id, String intro, String story, String act, String pic, String sound, String feel, String msg, String end) {
        Observable<String> observable = service.updateReview(id, intro, story, act, pic, sound, feel, msg, end);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if(s.equals("success")) {
                            view.updateReviewTextSuccess(id);
                        }
                    }
                });
    }

    @Override
    public void uploadImage(MultipartBody.Part body, String idReview, String type) {
        Observable<String> observable = service.uploadImage(body, idReview, type);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if(s.equals("success")) {
                            view.uploadImageSuccess();
                        }
                    }
                });
    }

    @Override
    public void updateImage(MultipartBody.Part body, String idReview, String type) {
        Observable<String> observable = service.updateImage(body, idReview, type);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if(s.equals("success")) {
                            view.uploadImageSuccess();
                        }
                    }
                });
    }
}
