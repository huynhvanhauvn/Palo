package com.sbro.palo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Quote {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("idUser")
    @Expose
    private String idUser;
    @SerializedName("idMovie")
    @Expose
    private String idMovie;
    @SerializedName("end")
    @Expose
    private String end;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(String idMovie) {
        this.idMovie = idMovie;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

}
