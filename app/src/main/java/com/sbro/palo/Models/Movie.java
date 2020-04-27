package com.sbro.palo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.inject.Inject;

public class Movie {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("director")
    @Expose
    private String director;
    @SerializedName("writer")
    @Expose
    private String writer;
    @SerializedName("cast")
    @Expose
    private String cast;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("vote")
    @Expose
    private String vote;
    @SerializedName("trailer")
    @Expose
    private String trailer;
    @SerializedName("poster")
    @Expose
    private String poster;
    @SerializedName("nation")
    @Expose
    private String nation;
    @SerializedName("date")
    @Expose
    private String date;

    @Inject
    public Movie() {
    }

    public Movie(String id, String title, String description, String director, String writer, String cast, String rating, String vote, String trailer, String poster, String nation, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.director = director;
        this.writer = writer;
        this.cast = cast;
        this.rating = rating;
        this.vote = vote;
        this.trailer = trailer;
        this.poster = poster;
        this.nation = nation;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
}
