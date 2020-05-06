package com.wildcardenter.myfab.nanodegreemoviesproject.database;

/*
                                #  #           #  #     
    Created by Asif Mondal on 05-05-2020 at 16:45
*/


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.wildcardenter.myfab.nanodegreemoviesproject.models.Movie;

import static com.wildcardenter.myfab.nanodegreemoviesproject.utils.Constants.FAVORITE_DATABASE_NAME;

@Database(entities = Movie.class, version = 1, exportSchema = true)
public abstract class FavoriteMoviesDatabase extends RoomDatabase {
    private static FavoriteMoviesDatabase favoriteMoviesDatabaseInstance = null;

    public static FavoriteMoviesDatabase getFavoriteDatabase(final Context context) {
        if (favoriteMoviesDatabaseInstance == null) {
            favoriteMoviesDatabaseInstance = Room.databaseBuilder(context,
                    FavoriteMoviesDatabase.class, FAVORITE_DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return favoriteMoviesDatabaseInstance;
    }

    public abstract FavoriteMoviesDao getFavoriteMoviesDao();


}
