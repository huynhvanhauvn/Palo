package com.sbro.palo.Activities.MovieList;

import com.sbro.palo.Models.Movie;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.sbro.palo.Activities.MovieList.MovieListActivity.TYPE_BEST;
import static com.sbro.palo.Activities.MovieList.MovieListActivity.TYPE_RECENT;

public class MovieListPresenter implements MovieListInterface {
    private MovieListView movieListView;
    private Service service = APIService.getService();

    public MovieListPresenter(MovieListView movieListView) {
        this.movieListView = movieListView;
    }

    @Override
    public void showList(int type) {
        switch (type) {
            case TYPE_RECENT:
                getRecentList();
                break;
            case TYPE_BEST:
                getBestList();
                break;
            default:
                break;
        }
    }

    private void getRecentList() {
        Observable<ArrayList<Movie>> observable = service.recentMovieList();
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
                            movieListView.showList(movies);
                        }
                    }
                });
    }

    private void getBestList() {
        Observable<ArrayList<Movie>> observable = service.bestMovieList();
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
                            movieListView.showList(movies);
                        }
                    }
                });
    }
}
