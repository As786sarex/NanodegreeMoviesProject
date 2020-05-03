package com.wildcardenter.myfab.nanodegreemoviesproject.adapters;




/*
                                #  #           #  #     
    Created by Asif Mondal on 03-05-2020 at 22:06
*/


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wildcardenter.myfab.nanodegreemoviesproject.R;
import com.wildcardenter.myfab.nanodegreemoviesproject.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context mContext;
    private List<Movie> movies;

    public MovieAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void refreshMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.movie_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        if (movies != null) {

        }
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieItemBannerImg;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieItemBannerImg = itemView.findViewById(R.id.movie_item_banner_img);
        }
    }
}
