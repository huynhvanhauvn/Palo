package com.hhub.palo.Activities.MovieList;

import com.hhub.palo.Models.Background;
import com.hhub.palo.Models.Movie;
import com.hhub.palo.Services.APIService;
import com.hhub.palo.Services.Service;

import java.util.ArrayList;
import java.util.Locale;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.hhub.palo.Activities.MovieList.MovieListActivity.TYPE_BEST;
import static com.hhub.palo.Activities.MovieList.MovieListActivity.TYPE_RECENT;

public class MovieListPresenter implements MovieListInterface {
    private MovieListView view;
    private Service service = APIService.getService();

    public MovieListPresenter(MovieListView movieListView) {
        this.view = movieListView;
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
                            view.showList(movies);
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
                            view.showList(movies);
                        }
                    }
                });
    }

    @Override
    public void getBackground() {
        Observable<Background> observable = service.background("movielist", Locale.getDefault().getLanguage());
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
