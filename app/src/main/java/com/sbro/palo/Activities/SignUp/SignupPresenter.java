package com.sbro.palo.Activities.SignUp;

import android.widget.Toast;

import com.sbro.palo.Models.User;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SignupPresenter implements SignupInterface {

    private SignupView signupView;
    private Service service = APIService.getService();

    public SignupPresenter(SignupView signupView) {
        this.signupView = signupView;
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
                            signupView.signup(username, password);
                        }
                    }
                });
    }
}
