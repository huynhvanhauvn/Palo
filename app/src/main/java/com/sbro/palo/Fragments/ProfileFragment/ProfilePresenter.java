package com.sbro.palo.Fragments.ProfileFragment;

import android.content.SharedPreferences;

import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.User;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import java.util.Locale;

import okhttp3.MultipartBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ProfilePresenter implements ProfileInterface {
    private ProfileView view;
    private Service service = APIService.getService();

    public ProfilePresenter(ProfileView view) {
        this.view = view;
    }

    @Override
    public void getBackground() {
        Observable<Background> observable = service.background("profile", Locale.getDefault().getLanguage());
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
    public void updateAvatar(MultipartBody.Part body, String id, String oldFile) {
        Observable<String> observable = service.updateAvatar(body,id,oldFile);
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
                        if(s != null && !s.equals("failed")) {
                            view.updatedAvatar(s);
                        }
                    }
                });
    }
}
