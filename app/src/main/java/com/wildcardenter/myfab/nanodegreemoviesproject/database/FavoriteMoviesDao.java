package com.wildcardenter.myfab.nanodegreemoviesproject.database;

/*
                                #  #           #  #     
    Created by Asif Mondal on 05-05-2020 at 16:57
*/


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.wildcardenter.myfab.nanodegreemoviesproject.models.Movie;

import java.util.List;

@Dao
public interface FavoriteMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFavoriteMovie(Movie movie);

    @Query("delete from favorite_movies where id=:id")
    void deleteFavoriteMovie(long id);

    @Query("select * from favorite_movies")
    LiveData<List<Movie>> getAllFavoriteMovies();

    @Query("select id from favorite_movies where id=:id limit 1")
    LiveData<Long> isFavPresent(long id);
}
