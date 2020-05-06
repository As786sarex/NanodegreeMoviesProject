package com.wildcardenter.myfab.nanodegreemoviesproject.database;

/*
                                #  #           #  #     
    Created by Asif Mondal on 05-05-2020 at 17:10
*/


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.wildcardenter.myfab.nanodegreemoviesproject.models.Movie;

import java.util.List;

public class FavoriteMoviesRepository {

    private FavoriteMoviesDao favoriteMoviesDao;

    public FavoriteMoviesRepository(Application application) {
        FavoriteMoviesDatabase favoriteMoviesDatabase = FavoriteMoviesDatabase.getFavoriteDatabase(application);
        favoriteMoviesDao = favoriteMoviesDatabase.getFavoriteMoviesDao();
    }

    //saving movies
    public void saveFavoriteMovie(Movie movie) {
        new AddFavoriteMovieAsync(favoriteMoviesDao).execute(movie);
    }

    //retrieve all movies
    LiveData<List<Movie>> getMoviesListLiveData() {
        return favoriteMoviesDao.getAllFavoriteMovies();
    }

    LiveData<Long> isFavoritePresent(long id) {
        return favoriteMoviesDao.isFavPresent(id);
    }

    //delete a movie
    public void deleteFavoriteMovie(long id) {
        new DeleteFavoriteMovieAsync(favoriteMoviesDao).execute(id);
    }

    //Asynctask classes for saving and deleting

    private static class AddFavoriteMovieAsync extends AsyncTask<Movie, Void, Void> {
        private FavoriteMoviesDao favoriteMoviesDao;

        AddFavoriteMovieAsync(FavoriteMoviesDao favoriteMoviesDao) {
            this.favoriteMoviesDao = favoriteMoviesDao;
        }


        @Override
        protected Void doInBackground(Movie... movies) {
            favoriteMoviesDao.addFavoriteMovie(movies[0]);
            return null;
        }
    }

    private static class DeleteFavoriteMovieAsync extends AsyncTask<Long, Void, Void> {
        private FavoriteMoviesDao favoriteMoviesDao;

        DeleteFavoriteMovieAsync(FavoriteMoviesDao favoriteMoviesDao) {
            this.favoriteMoviesDao = favoriteMoviesDao;
        }

        @Override
        protected Void doInBackground(Long... longs) {
            favoriteMoviesDao.deleteFavoriteMovie(longs[0]);
            return null;
        }
    }


}
