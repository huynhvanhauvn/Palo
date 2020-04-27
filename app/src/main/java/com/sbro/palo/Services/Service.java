package com.sbro.palo.Services;

import com.sbro.palo.Models.Artist;
import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.Models.Promotion;
import com.sbro.palo.Models.User;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
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
    Observable<Background> background(@Field("position") String position);

    @GET("promotion.php")
    Observable<ArrayList<Promotion>> promotion();

    @GET("recentmovie.php")
    Observable<ArrayList<Movie>> recentMovie();

    @FormUrlEncoded
    @POST("artistname.php")
    Observable<String> artistName(@Field("id") String id);

    @FormUrlEncoded
    @POST("movie.php")
    Observable<Movie> movie(@Field("id") String id);

    @FormUrlEncoded
    @POST("artist.php")
    Observable<Artist> artist(@Field("id") String id);

    @FormUrlEncoded
    @POST("vote.php")
    Observable<String> vote(@Field("rating") float rating, @Field("idUser") String idUser, @Field("idMovie") String idMovie);

    @FormUrlEncoded
    @POST("rating.php")
    Observable<String> rating(@Field("id") String id);

    @FormUrlEncoded
    @POST("checkVoted.php")
    Observable<String> checkVoted(@Field("idUser") String idUser, @Field("idMovie") String idMovie);

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

    @Multipart
    @POST("uploadImage.php")
    Observable<String> uploadImage(@Part MultipartBody.Part file, @Query("id") String idUser, @Query("type") String type);
}
