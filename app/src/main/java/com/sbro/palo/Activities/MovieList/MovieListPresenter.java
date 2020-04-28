package com.sbro.palo.Activities.MovieList;

import com.sbro.palo.Models.Movie;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MovieListPresenter implements MovieListInterface {
    private MovieListView movieListView;
    private Service service = APIService.getService();

    public MovieListPresenter(MovieListView movieListView) {
        this.movieListView = movieListView;
    }

    @Override
    public void showList(int type) {
        Observable<ArrayList<Movie>> observable = service.recentMovieList();
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<Movie>>() {
                    @Override
                    public void call(ArrayList<Movie> movies) {
                        if(movies != null) {
                            movieListView.showList(movies);
                        }
                    }
                });
    }
}
