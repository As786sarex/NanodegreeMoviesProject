package com.wildcardenter.myfab.nanodegreemoviesproject.models;

/*
                                #  #           #  #     
    Created by Asif Mondal on 03-05-2020 at 22:24
*/


import java.io.Serializable;

public class Movie implements Serializable {

    private String posterPath;
    private String originalTitle;
    private double voteAverage;
    private String overview;
    private String releaseDate;

    public Movie(String posterPath, String originalTitle, double voteAverage, String overview, String releaseDate) {
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.releaseDate = releaseDate;
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
}
