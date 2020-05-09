package com.sbro.palo.Activities.ArtistActivity;

import com.sbro.palo.Models.Artist;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import java.util.ArrayList;
import java.util.Locale;

import rx.Observable;
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
                .subscribe(new Action1<Artist>() {
                    @Override
                    public void call(Artist artist) {
                        if(artist != null) {
                            view.showArtist(artist);
                        }
                    }
                });
    }

    @Override
    public void getDirectList(String id) {
        Observable<ArrayList<Movie>> observable = service.directorMovie(id);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<Movie>>() {
                    @Override
                    public void call(ArrayList<Movie> movies) {
                        if(movies != null && movies.size() > 0) {
                            view.showDirectList(movies);
                        }
                    }
                });
    }

    @Override
    public void getWriteList(String id) {
        Observable<ArrayList<Movie>> observable = service.writerMovie(id);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<Movie>>() {
                    @Override
                    public void call(ArrayList<Movie> movies) {
                        if(movies != null && movies.size() > 0) {
                            view.showWriteList(movies);
                        }
                    }
                });
    }

    @Override
    public void getActList(String id) {
        Observable<ArrayList<Movie>> observable = service.castMovie(id);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<Movie>>() {
                    @Override
                    public void call(ArrayList<Movie> movies) {
                        if(movies != null && movies.size() > 0) {
                            view.showActList(movies);
                        }
                    }
                });
    }
}
