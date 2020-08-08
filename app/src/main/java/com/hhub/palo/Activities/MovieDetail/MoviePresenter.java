package com.hhub.palo.Activities.MovieDetail;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import com.hhub.palo.Models.Artist;
import com.hhub.palo.Models.Category;
import com.hhub.palo.Models.Movie;
import com.hhub.palo.Models.Quote;
import com.hhub.palo.R;
import com.hhub.palo.Services.APIService;
import com.hhub.palo.Services.Service;

import java.util.ArrayList;
import java.util.Locale;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MoviePresenter implements MovieInterface {

    private MovieView movieView;
    private Service service = APIService.getService();

    public MoviePresenter(MovieView movieView) {
        this.movieView = movieView;
    }

    @Override
    public void showMovie(String id, String language) {
        Observable<Movie> movieObservable = service.movie(id, language);
        movieObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Movie movie) {
                        if(movie != null) {
                            movieView.showMovie(movie);
                        }
                    }
                });
    }

    @Override
    public void getArtist(String idMovie, final int role) {
        Observable<ArrayList<Artist>> artistObservable = service.artist(idMovie, role);
        artistObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Artist>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<Artist> artists) {
                        if (artists != null) {
                            movieView.getArtist(artists, role);
                        }
                    }
                });
    }

    @Override
    public void showRating(String idUser, String idMovie, final Context context, final Resources resources) {
        Observable<String> checkVotedObservable = service.checkVoted(idUser,idMovie);
        checkVotedObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        if(s.equals("true")) {
                            movieView.showRating();
                        } else {
                            Toast.makeText(context,
                                    resources.getString(R.string.detail_voted),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void actionRating(float rating, String idUser, final String idMovie) {
        Observable<String> voteObservable = service.vote(rating,
                idUser,idMovie);
        voteObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        if(s.equals("success")) {
                            Observable<String> ratingObservable = service.rating(idMovie);
                            ratingObservable.subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<String>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onNext(String s) {
                                            if(s != null) {
                                                movieView.updateRating(s);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    @Override
    public void showReviews(String id, final String title, final String poster) {
        Observable<ArrayList<Quote>> observable = service.getQuote(id);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Quote>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<Quote> quotes) {
                        if(quotes != null) {
                            movieView.showReviews(quotes,title,poster);
                        }
                    }
                });
    }

    @Override
    public void getCategory(String idMovie) {
        Observable<ArrayList<Category>> observable = service.categoryMovie(idMovie, Locale.getDefault().getLanguage());
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Category>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<Category> categories) {
                        if(categories != null) {
                            ArrayList<String> cate = new ArrayList<>();
                            for(Category category : categories) {
                                cate.add(category.getTitle());
                            }
                            movieView.showCategory(cate);
                        }
                    }
                });
    }
}
