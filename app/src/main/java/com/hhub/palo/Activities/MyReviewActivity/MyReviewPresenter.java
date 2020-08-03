package com.hhub.palo.Activities.MyReviewActivity;

import com.hhub.palo.Models.Background;
import com.hhub.palo.Models.Quote;
import com.hhub.palo.Services.APIService;
import com.hhub.palo.Services.Service;

import java.util.ArrayList;
import java.util.Locale;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyReviewPresenter implements MyReviewInterface {
    private MyReviewView view;
    private Service service = APIService.getService();

    public MyReviewPresenter(MyReviewView view) {
        this.view = view;
    }

    @Override
    public void getListReview(String id) {
        Observable<ArrayList<Quote>> observable = service.getMyReview(id);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Quote>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<Quote> quotes) {
                        if(quotes != null) {
                            view.showReviewList(quotes);
                        }
                    }
                });
    }

    @Override
    public void getBackground() {
        Observable<Background> observable = service.background("myreview", Locale.getDefault().getLanguage());
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Background>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Background background) {
                        if(background != null) {
                            view.showBackground(background);
                        }
                    }
                });
    }
}
