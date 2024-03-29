package com.hhub.palo.Services;

import com.hhub.palo.Models.Artist;
import com.hhub.palo.Models.ArtistTrend;
import com.hhub.palo.Models.Background;
import com.hhub.palo.Models.Category;
import com.hhub.palo.Models.DateView;
import com.hhub.palo.Models.Movie;
import com.hhub.palo.Models.Promotion;
import com.hhub.palo.Models.Quote;
import com.hhub.palo.Models.Review;
import com.hhub.palo.Models.Reward;
import com.hhub.palo.Models.Table;
import com.hhub.palo.Models.TrendMovie;
import com.hhub.palo.Models.User;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

public interface Service {

    @FormUrlEncoded
    @POST("login.php")
    Observable<User> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("signup.php")
    Observable<String> signup(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("background.php")
    Observable<Background> background(@Field("position") String position, @Field("language") String language);

    @GET("promotion.php")
    Observable<ArrayList<Promotion>> promotion();

    @GET("recentmovie.php")
    Observable<ArrayList<Movie>> recentMovie();

    @GET("recentmovielist.php")
    Observable<ArrayList<Movie>> recentMovieList();

    @GET("trendmovie.php")
    Observable<ArrayList<Movie>> moviePopular();

    @FormUrlEncoded
    @POST("artistname.php")
    Observable<ArrayList<String>> artistName(@Field("id") String id, @Field("role") int role);

    @FormUrlEncoded
    @POST("movie.php")
    Observable<Movie> movie(@Field("id") String id, @Field("language") String language, @Field("idUser") String idUser);

    @FormUrlEncoded
    @POST("movieinfo.php")
    Observable<Movie> movieInfo(@Field("id") String id);

    @GET("movietrend.php")
    Observable<ArrayList<TrendMovie>> movieTrend();

    @FormUrlEncoded
    @POST("artist.php")
    Observable<ArrayList<Artist>> artist(@Field("id") String id, @Field("role") int role);

    @FormUrlEncoded
    @POST("artistdetail.php")
    Observable<Artist> artistDetail(@Field("id") String id, @Field("language") String language, @Field("idUser") String idUser);

    @FormUrlEncoded
    @POST("vote.php")
    Observable<String> vote(@Field("rating") float rating, @Field("idUser") String idUser,
                            @Field("idMovie") String idMovie);

    @FormUrlEncoded
    @POST("reviewvote.php")
    Observable<String> voteReview(@Field("rating") float rating, @Field("idUser") String idUser,
                                  @Field("idReview") String idReview);

    @FormUrlEncoded
    @POST("rating.php")
    Observable<String> rating(@Field("id") String id);

    @FormUrlEncoded
    @POST("checkVoted.php")
    Observable<String> checkVoted(@Field("idUser") String idUser, @Field("idMovie") String idMovie);

    @FormUrlEncoded
    @POST("checkReviewVoted.php")
    Observable<String> checkReviewVoted(@Field("idUser") String idUser, @Field("idReview") String idMovie);

    @FormUrlEncoded
    @POST("review.php")
    Observable<String> review(@Field("idUser") String idUser, @Field("idMovie") String idMovie,
                              @Field("introduction") String intro, @Field("story") String story,
                              @Field("storyImage") String storyImg, @Field("acting") String act,
                              @Field("actingImage") String actImg, @Field("picture") String pic,
                              @Field("pictureImage") String picImg, @Field("sound") String sound,
                              @Field("soundImage") String soundImage, @Field("feel") String feel,
                              @Field("feelImage") String feelImg, @Field("message") String msg,
                              @Field("messageImage") String msgImg, @Field("end") String end);

    @FormUrlEncoded
    @POST("reviewupdate.php")
    Observable<String> updateReview(@Field("id") String id,
                                    @Field("introduction") String intro, @Field("story") String story,
                                    @Field("acting") String act, @Field("picture") String pic,
                                    @Field("sound") String sound, @Field("feel") String feel,
                                    @Field("message") String msg, @Field("end") String end);

    @Multipart
    @POST("uploadImage.php")
    Observable<String> uploadImage(@Part MultipartBody.Part file, @Query("id") String idMovie, @Query("type") String type);

    @Multipart
    @POST("updateImage.php")
    Observable<String> updateImage(@Part MultipartBody.Part file, @Query("id") String idMovie, @Query("type") String type,
                                   @Query("oldFile") String oldFile);

    @Multipart
    @POST("updateavatar.php")
    Observable<String> updateAvatar(@Part MultipartBody.Part file, @Query("id") String idUser,
                                    @Query("oldFile") String oldFile);

    @FormUrlEncoded
    @POST("quote.php")
    Observable<ArrayList<Quote>> getQuote(@Field("idMovie") String idMovie);

    @FormUrlEncoded
    @POST("reviewdetail.php")
    Observable<Review> getReview(@Field("id") String id, @Field("language") String language);

    @FormUrlEncoded
    @POST("reviewinfo.php")
    Observable<Review> getReviewInfo(@Field("id") String id);

    @GET("keyword.php")
    Observable<ArrayList<String>> getPopularTags();

    @FormUrlEncoded
    @POST("searchmovie.php")
    Observable<ArrayList<Movie>> searchResult(@Field("keyword") String keyword);

    @FormUrlEncoded
    @POST("searchartist.php")
    Observable<ArrayList<Artist>> searchArtist(@Field("keyword") String keyword);

    @FormUrlEncoded
    @POST("updatekeyword.php")
    Observable<String> updateKeyword(@Field("keyword") String keyword);

    @FormUrlEncoded
    @POST("dateviewmovie.php")
    Observable<ArrayList<DateView>> getDateView(@Field("idMovie") String idMovie);

    @FormUrlEncoded
    @POST("profile.php")
    Observable<User> getProfile(@Field("id") String id);

    @FormUrlEncoded
    @POST("myreview.php")
    Observable<ArrayList<Quote>> getMyReview(@Field("idUser") String idUser);

    @FormUrlEncoded
    @POST("changepassword.php")
    Observable<String> changePassword(@Field("id") String id, @Field("password") String password);

    @FormUrlEncoded
    @POST("artistlistmovie.php")
    Observable<ArrayList<Movie>> artistmovie(@Field("id") String id, @Field("role") int role);

    @FormUrlEncoded
    @POST("updateuser.php")
    Observable<String> updateUser(@Field("id") String id, @Field("fullName") String fullName,
                                  @Field("gender") String gender, @Field("birthday") String birthday);

    @GET("artisttrendcast.php")
    Observable<ArrayList<ArtistTrend>> hotCast();

    @GET("reviewtrend.php")
    Observable<ArrayList<Quote>> hotReview();

    @GET("moviebest.php")
    Observable<ArrayList<Movie>> bestMovie();

    @GET("moviebestlist.php")
    Observable<ArrayList<Movie>> bestMovieList();

    @FormUrlEncoded
    @POST("feedback.php")
    Observable<String> feedback(@Field("type") int type, @Field("feedback") String feedback,
                                @Field("idUser") String idUser);

    @GET("artistbirthday.php")
    Observable<ArrayList<Artist>> artistBirthday();

    @FormUrlEncoded
    @POST("checkmoviewinreward.php")
    Observable<String> checkMovieWinReward(@Field("idMovie") String idMovie);

    @GET("analyzenation.php")
    Observable<ArrayList<Table>> analyzeNation();

    @FormUrlEncoded
    @POST("categorymovie.php")
    Observable<ArrayList<Category>> categoryMovie(@Field("id") String id, @Field("language") String language);

    @FormUrlEncoded
    @POST("movienation.php")
    Observable<ArrayList<Movie>> movieNation(@Field("nation") String nation);

    @FormUrlEncoded
    @POST("moviecategory.php")
    Observable<ArrayList<Movie>> movieCategory(@Field("category") String category);

    @FormUrlEncoded
    @POST("oscarmovie.php")
    Observable<ArrayList<Reward>> oscarMovie(@Field("idMovie") String idMovie, @Field("language") String language);

    @FormUrlEncoded
    @POST("oscarartist.php")
    Observable<ArrayList<Reward>> oscarArtist(@Field("idArtist") String idArtist, @Field("language") String language);
}
