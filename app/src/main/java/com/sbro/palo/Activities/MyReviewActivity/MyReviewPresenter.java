package com.sbro.palo.Activities.MyReviewActivity;

import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.Quote;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import java.util.ArrayList;
import java.util.Locale;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
                .subscribe(new Action1<ArrayList<Quote>>() {
                    @Override
                    public void call(ArrayList<Quote> quotes) {
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
                .subscribe(new Action1<Background>() {
                    @Override
                    public void call(Background background) {
                        if(background != null) {
                            view.showBackground(background);
                        }
                    }
                });
    }
}
