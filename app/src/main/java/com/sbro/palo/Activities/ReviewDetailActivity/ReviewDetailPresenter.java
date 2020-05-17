package com.sbro.palo.Activities.ReviewDetailActivity;

import com.sbro.palo.Models.Review;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import java.util.Locale;

import rx.Observable;
import rx.Observer;
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
        Observable<Review> observable = service.getReview(id, Locale.getDefault().getLanguage());
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Review>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Review review) {
                        if(review != null) {
                            view.showReview(review);
                        }
                    }
                });
    }

    @Override
    public void enableVote(final String idUser, final String idReview) {
        Observable<String> observable = service.checkReviewVoted(idUser,idReview);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        if(s.equals("true")) {
                            view.enableVote(idUser,idReview);
                        }
                    }
                });
    }

    @Override
    public void rating(float rating, String idUser, String idReview) {
        Observable<String> observable = service.voteReview(rating,idUser,idReview);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        if(s.equals("success")) {
                            view.voteSuccess();
                        }
                    }
                });
    }
}
