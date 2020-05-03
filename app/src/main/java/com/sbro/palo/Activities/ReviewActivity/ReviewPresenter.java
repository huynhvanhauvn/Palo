package com.sbro.palo.Activities.ReviewActivity;

import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.Models.Review;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

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
    public void showMovieInfo(Movie movie) {
        view.showMovieInfo(movie);
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
}
