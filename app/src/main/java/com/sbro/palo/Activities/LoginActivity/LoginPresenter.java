package com.sbro.palo.Activities.LoginActivity;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.User;
import com.sbro.palo.R;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LoginPresenter implements LoginInterface{
    private LoginView loginView;
    private Service service = APIService.getService();

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void infoUser(String user, String pass, final Context context, final Resources resources) {
        Observable<User> observable = service.login(user,pass);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        if(user.getId()!=null) {
                            loginView.infoUser(user);
                        } else {
                            Toast.makeText(context,
                                    resources.getString(R.string.login_failed),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void updateBackground(String language) {
        Observable<Background> obBackground = service.background("login",language);
        obBackground.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Background>() {
                    @Override
                    public void call(Background background) {
                        if(background.getId() != null) {
                            loginView.updateBackground(background);
                        }
                    }
                });
    }
}
