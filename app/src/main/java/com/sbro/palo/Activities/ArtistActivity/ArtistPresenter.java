package com.sbro.palo.Activities.ArtistActivity;

import android.util.Log;

import com.sbro.palo.Models.Artist;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import java.util.ArrayList;
import java.util.Locale;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ArtistPresenter implements ArtistInterface {
    private ArtistView view;
    private Service service = APIService.getService();

    public ArtistPresenter(ArtistView view) {
        this.view = view;
    }

    @Override
    public void getArtist(String id) {
        Observable<Artist> observable = service.artistDetail(id, Locale.getDefault().getLanguage());
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Artist>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Artist artist) {
                        if(artist != null) {
                            view.showArtist(artist);
                        }
                    }
                });
    }

    @Override
    public void getDirectList(String id) {
        Observable<ArrayList<Movie>> observable = service.artistmovie(id,1);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Movie>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<Movie> movies) {
                        if(movies != null && movies.size() > 0) {
                            view.showDirectList(movies);
                        }
                    }
                });
    }

    @Override
    public void getWriteList(String id) {
        Observable<ArrayList<Movie>> observable = service.artistmovie(id, 2);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Movie>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<Movie> movies) {
                        if(movies != null && movies.size() > 0) {
                            view.showWriteList(movies);
                        }
                    }
                });
    }

    @Override
    public void getActList(String id) {
        Observable<ArrayList<Movie>> observable = service.artistmovie(id,3);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Movie>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<Movie> movies) {
                        if(movies != null && movies.size() > 0) {
                            view.showActList(movies);
                        }
                    }
                });
    }
}
