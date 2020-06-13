package com.sbro.palo.Activities.ChangePassActivity;

import com.sbro.palo.Models.Background;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import java.util.Locale;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChangePassPresenter implements ChangePassInterface {
    private ChangePassView view;
    private Service service = APIService.getService();

    public ChangePassPresenter(ChangePassView view) {
        this.view = view;
    }

    @Override
    public void showBackground() {
        Observable<Background> observable = service.background("changepass", Locale.getDefault().getLanguage());
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

    @Override
    public void changePassword(String id, String pass) {
        Observable<String> observable = service.changePassword(id, pass);
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
                            view.ChangeSuccess();
                        }
                    }
                });
    }
}
