package com.sbro.palo.Fragments.HomeFragment;

import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.Models.Promotion;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
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
                .subscribe(new Observer<ArrayList<Promotion>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<Promotion> promotions) {
                        if (promotions != null) {
                            homeView.showHeader(promotions);
                        }
                    }
                });
    }

    @Override
    public void showBackground(String language) {
        Observable<Background> backgroundObservable = service.background("home",language);
        backgroundObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
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
                            homeView.showBackground(background);
                        }
                    }
                });
    }

    @Override
    public void showRecentMovie() {
        Observable<ArrayList<Movie>> recentObservable = service.recentMovie();
        recentObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Movie>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<Movie> movies) {
                        if(movies != null) {
                            homeView.showRecentMovie(movies);
                        }
                    }
                });
    }
}
