package com.hhub.palo.Activities.SearchActivity;

import com.hhub.palo.Models.Artist;
import com.hhub.palo.Models.Background;
import com.hhub.palo.Models.Movie;
import com.hhub.palo.Services.APIService;
import com.hhub.palo.Services.Service;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchPresenter implements SearchInterface {
    private SearchView view;
    private Service service = APIService.getService();

    public SearchPresenter(SearchView view) {
        this.view = view;
    }

    @Override
    public void getPopularTags() {
        Observable<ArrayList<String>> observable = service.getPopularTags();
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<String> strings) {
                        if (strings != null) {
                            view.showPopularTags(strings);
                        }
                    }
                });
    }

    @Override
    public void updateKeyword(String keyword) {
        Observable<String> observable = service.updateKeyword(keyword);
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

                    }
                });
    }

    @Override
    public void search(String keyword) {
        Observable<ArrayList<Movie>> observable = service.searchResult(keyword);
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
                        if(movies != null) {
                            view.showResult(movies);
                        }
                    }
                });
    }

    @Override
    public void searchArtist(String keyword) {
        Observable<ArrayList<Artist>> observable = service.searchArtist(keyword);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Artist>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<Artist> artists) {
                        if(artists != null) {
                            view.showArtists(artists);
                        }
                    }
                });
    }

    @Override
    public void showBackground(String language) {
        Observable<Background> observable = service.background("search",language);
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
