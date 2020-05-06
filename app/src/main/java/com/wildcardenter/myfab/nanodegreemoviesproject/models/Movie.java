package com.wildcardenter.myfab.nanodegreemoviesproject.models;

/*
                                #  #           #  #     
    Created by Asif Mondal on 03-05-2020 at 22:24
*/


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "favorite_movies")
public class Movie implements Serializable {

    @PrimaryKey(autoGenerate = false)
    private long id;
    private String posterPath;
    private String originalTitle;
    private double voteAverage;
    private String overview;
    private String releaseDate;

    public Movie(long id, String posterPath,
                 String originalTitle, double voteAverage,
                 String overview, String releaseDate) {
        this.id = id;
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public Movie() {
    }


    //getters
    public long getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }


    //setters
    public void setId(long id) {
        this.id = id;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
