package com.sbro.palo.Activities.FeedbackActivity;

import android.util.Log;

import com.sbro.palo.Models.Background;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import java.util.Locale;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FeedbackPresenter implements FeedbackInterface {

    private FeedbackView view;
    private Service service = APIService.getService();

    public FeedbackPresenter(FeedbackView view) {
        this.view = view;
    }

    @Override
    public void sendFeedBack(int type, String feedback, String idUser) {
        Observable<String> observable = service.feedback(type, feedback, idUser);
        observable.subscribeOn(Schedulers.newThread()).subscribeOn(AndroidSchedulers.mainThread())
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
                            view.feedbackSuccess();
                        }
                    }
                });
    }

    @Override
    public void getBackground() {
        Observable<Background> observable = service.background("feedback", Locale.getDefault().getLanguage());
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
