package com.hhub.palo.Activities.ArtistActivity;

import com.hhub.palo.Models.Artist;
import com.hhub.palo.Models.Movie;
import com.hhub.palo.Models.Reward;
import com.hhub.palo.Services.APIService;
import com.hhub.palo.Services.Service;

import java.util.ArrayList;
import java.util.Locale;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ArtistPresenter implements ArtistInterface {
    private ArtistView view;
    private Service service = APIService.getService();

    public ArtistPresenter(ArtistView view) {
        this.view = view;
    }

    @Override
    public void getArtist(String id, String idUser) {
        Observable<Artist> observable = service.artistDetail(id, Locale.getDefault().getLanguage(), idUser);
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

    @Override
    public void getReward(String idArtist) {
        Observable<ArrayList<Reward>> observable = service.oscarArtist(idArtist, Locale.getDefault().getLanguage());
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Reward>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<Reward> rewards) {
                        if(rewards != null && rewards.size()>0){
                            view.showReward(rewards);
                        }
                    }
                });
    }
}
