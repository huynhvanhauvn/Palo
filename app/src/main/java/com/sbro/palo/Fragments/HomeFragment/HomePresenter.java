package com.sbro.palo.Fragments.HomeFragment;

import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.Models.Promotion;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class HomePresenter implements HomeInterface {
    private HomeView homeView;
    private Service service = APIService.getService();

    public HomePresenter(HomeView homeView) {
        this.homeView = homeView;
    }

    @Override
    public void showHeader() {
        Service service = APIService.getService();
        Observable<ArrayList<Promotion>> promoObservable = service.promotion();
        promoObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<Promotion>>() {
                    @Override
                    public void call(ArrayList<Promotion> promotions) {
                        if(promotions != null) {
                            homeView.showHeader(promotions);
                        }
                    }
                });
    }

    @Override
    public void showBackground() {
        Observable<Background> backgroundObservable = service.background("home");
        backgroundObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Background>() {
                    @Override
                    public void call(Background background) {
                        if(background != null) {
                            homeView.showBackground(background);
                        }
                    }
                });
    }

    @Override
    public void showRecentMovie() {
        Observable<ArrayList<Movie>> recentObservable = service.recentMovie();
        recentObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<Movie>>() {
                    @Override
                    public void call(ArrayList<Movie> movies) {
                        if(movies != null) {
                            homeView.showRecentMovie(movies);
                        }
                    }
                });
    }
}
