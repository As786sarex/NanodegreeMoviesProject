package com.wildcardenter.myfab.nanodegreemoviesproject.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.wildcardenter.myfab.nanodegreemoviesproject.models.Movie;

import java.util.List;


/*
    Created by Asif Mondal on 05-05-2020 at 17:30
*/


public class FavoriteMoviesViewModel extends AndroidViewModel {

    public boolean FAVORITE_TRIGGER;
    private FavoriteMoviesRepository favoriteMoviesRepository;
    private LiveData<List<Movie>> listLiveData;

    public FavoriteMoviesViewModel(@NonNull Application application) {
        super(application);
        FAVORITE_TRIGGER = false;
        favoriteMoviesRepository = new FavoriteMoviesRepository(application);
        listLiveData = favoriteMoviesRepository.getMoviesListLiveData();
    }

    public LiveData<List<Movie>> getAllFavoriteMovies() {
        return this.listLiveData;
    }

    public void addFavoriteMovie(Movie movie) {
        favoriteMoviesRepository.saveFavoriteMovie(movie);
    }

    public void deleteFavoriteMovie(long id) {
        favoriteMoviesRepository.deleteFavoriteMovie(id);
    }

    public LiveData<Long> isFabPresent(long id) {
        return favoriteMoviesRepository.isFavoritePresent(id);
    }

}
