package com.hhub.palo.Activities.SignUp;

import com.hhub.palo.Models.Background;
import com.hhub.palo.Services.APIService;
import com.hhub.palo.Services.Service;

import java.util.Locale;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SignupPresenter implements SignupInterface {

    private SignupView view;
    private Service service = APIService.getService();

    public SignupPresenter(SignupView signupView) {
        this.view = signupView;
    }

    @Override
    public void signup(final String username, final String password) {
        Observable<String> observable = service.signup(username,
                password);
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
                            view.signup(username, password);
                        }
                    }
                });
    }

    @Override
    public void getBackground() {
        Observable<Background> observable = service.background("signup", Locale.getDefault().getLanguage());
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
