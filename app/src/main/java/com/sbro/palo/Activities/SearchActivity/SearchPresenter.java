package com.sbro.palo.Activities.SearchActivity;

import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
                .subscribe(new Action1<ArrayList<String>>() {
                    @Override
                    public void call(ArrayList<String> strings) {
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
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                    }
                });
    }

    @Override
    public void search(String keyword) {
        Observable<ArrayList<Movie>> observable = service.searchResult(keyword);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<Movie>>() {
                    @Override
                    public void call(ArrayList<Movie> movies) {
                        if(movies != null) {
                            view.showResult(movies);
                        }
                    }
                });
    }

    @Override
    public void showBackground() {
        Observable<Background> observable = service.background("search");
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Background>() {
                    @Override
                    public void call(Background background) {
                        if(background != null) {
                            view.showBackground(background);
                        }
                    }
                });
    }
}
