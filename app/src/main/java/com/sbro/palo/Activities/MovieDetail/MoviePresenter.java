package com.sbro.palo.Activities.MovieDetail;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.sbro.palo.Adapter.ArtistAdapter;
import com.sbro.palo.Models.Artist;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.Models.Quote;
import com.sbro.palo.R;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
                .subscribe(new Action1<Movie>() {
                    @Override
                    public void call(Movie movie) {
                        if(movie != null) {
                            movieView.showMovie(movie);
                        }
                    }
                });
    }

    @Override
    public void getArtist(Context context, String[] ids, final RecyclerView recycler, final TextView textView) {
        final ArrayList<Artist> artists = new ArrayList<>();
        final ArtistAdapter adapter = new ArtistAdapter(context,artists);
        for(String id : ids) {
            if(!id.equals("")) {
                Observable<Artist> artistObservable = service.artist(id);
                artistObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Artist>() {
                            @Override
                            public void call(Artist artist) {
                                if(artist != null) {
                                    artists.add(artist);
                                    adapter.notifyDataSetChanged();
                                    movieView.getArtist(adapter,recycler,textView);
                                }
                            }
                        });
            }
        }
    }

    @Override
    public void showRating(String idUser, String idMovie, final Context context, final Resources resources) {
        Observable<String> checkVotedObservable = service.checkVoted(idUser,idMovie);
        checkVotedObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
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
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if(s.equals("success")) {
                            Observable<String> ratingObservable = service.rating(idMovie);
                            ratingObservable.subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<String>() {
                                        @Override
                                        public void call(String s) {
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
        Log.d("hvhau",id);
        Observable<ArrayList<Quote>> observable = service.getQuote(id);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<Quote>>() {
                    @Override
                    public void call(ArrayList<Quote> quotes) {
                        if(quotes != null) {
                            movieView.showReviews(quotes,title,poster);
                        }
                    }
                });
    }
}
