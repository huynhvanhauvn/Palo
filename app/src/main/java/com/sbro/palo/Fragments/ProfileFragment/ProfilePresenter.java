package com.sbro.palo.Fragments.ProfileFragment;

import com.sbro.palo.Models.Background;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import java.util.Locale;

import rx.Observable;
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
                .subscribe(new Action1<Background>() {
                    @Override
                    public void call(Background background) {
                        if(background != null) {
                            view.showBackdround(background);
                        }
                    }
                });
    }
}
