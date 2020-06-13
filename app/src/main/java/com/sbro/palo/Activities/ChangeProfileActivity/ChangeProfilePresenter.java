package com.sbro.palo.Activities.ChangeProfileActivity;

import android.util.Log;

import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.User;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import java.util.Locale;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChangeProfilePresenter implements ChangeProfileInterface {

    private ChangeProfileView view;
    private Service service = APIService.getService();

    public ChangeProfilePresenter(ChangeProfileView view) {
        this.view = view;
    }

    @Override
    public void getProfile(String id) {
        Observable<User> observable = service.getProfile(id);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(User user) {
                        if(user != null) {
                            view.showProfile(user);
                        }
                    }
                });
    }

    @Override
    public void updateProfie(String id, String fullName, String gender, String birthday) {
        Log.d("hvhau", "api");
        Observable<String> observable = service.updateUser(id,fullName,gender,birthday);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("hvhau", e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        if(s != null && s.equals("success")) {
                            view.updateSuccess();
                        }
                    }
                });
    }

    @Override
    public void getBackground() {
        Observable<Background> observable = service.background("changeprofile", Locale.getDefault().getLanguage());
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
