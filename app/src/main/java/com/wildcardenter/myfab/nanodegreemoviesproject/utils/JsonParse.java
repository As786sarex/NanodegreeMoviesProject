package com.wildcardenter.myfab.nanodegreemoviesproject.utils;

/*
                                #  #           #  #     
    Created by Asif Mondal on 04-05-2020 at 00:31
*/


import com.wildcardenter.myfab.nanodegreemoviesproject.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParse {
    public static List<Movie> parseMoviesFromJson(final String json) {
        if (json == null || json.isEmpty())
            return null;

        try {
            JSONObject rootJsonObject = new JSONObject(json);
            JSONArray resultJsonArray = rootJsonObject.getJSONArray("results");

            List<Movie> movieList = new ArrayList<>();
            for (int i = 0; i < resultJsonArray.length(); i++) {
                JSONObject resultArrayElement = resultJsonArray.getJSONObject(i);

                String posterPath = resultArrayElement.getString("poster_path");
                String originalTitle = resultArrayElement.getString("original_title");
                double voteAverage = resultArrayElement.getDouble("vote_average");
                String overview = resultArrayElement.getString("overview");
                String releaseDate = resultArrayElement.getString("release_date");

                movieList.add(new Movie(posterPath, originalTitle, voteAverage, overview, releaseDate));
            }
            return movieList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
