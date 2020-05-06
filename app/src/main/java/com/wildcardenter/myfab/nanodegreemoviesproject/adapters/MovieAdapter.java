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

import com.squareup.picasso.Picasso;
import com.wildcardenter.myfab.nanodegreemoviesproject.R;
import com.wildcardenter.myfab.nanodegreemoviesproject.models.Movie;

import java.util.List;

import static com.wildcardenter.myfab.nanodegreemoviesproject.utils.Constants.IMAGE_URL_PREFIX;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context mContext;
    private List<Movie> movies;
    private OnMovieClickListener listener;

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
            Movie movie = movies.get(position);
            if (movie.getPosterPath() != null) {
                Picasso.get()
                        .load(IMAGE_URL_PREFIX + movie.getPosterPath())
                        .into(holder.movieItemBannerImg);
            }
        }
    }


    public void setOnMovieClickListener(OnMovieClickListener listener) {
        this.listener = listener;
    }

    public interface OnMovieClickListener {
        void onClick(int position);
    }


    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieItemBannerImg;
        View itemContainer;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieItemBannerImg = itemView.findViewById(R.id.movie_item_banner_img);
            itemView.findViewById(R.id.movies_grid_card).setOnClickListener(i -> {
                if (listener != null) {
                    int pos = getAdapterPosition();
                    listener.onClick(pos);
                }
            });
        }

    }
}
