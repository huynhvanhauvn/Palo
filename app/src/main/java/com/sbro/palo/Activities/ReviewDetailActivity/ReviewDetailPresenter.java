package com.sbro.palo.Activities.ReviewDetailActivity;

import com.sbro.palo.Models.Review;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ReviewDetailPresenter implements ReviewDetailInterface {
    private ReviewDetailView view;
    private Service service = APIService.getService();

    public ReviewDetailPresenter(ReviewDetailView view) {
        this.view = view;
    }

    @Override
    public void showReview(String id) {
        Observable<Review> observable = service.getReview(id);
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
