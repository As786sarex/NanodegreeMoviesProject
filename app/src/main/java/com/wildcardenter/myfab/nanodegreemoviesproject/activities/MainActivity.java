package com.wildcardenter.myfab.nanodegreemoviesproject.activities;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wildcardenter.myfab.nanodegreemoviesproject.R;
import com.wildcardenter.myfab.nanodegreemoviesproject.adapters.MovieAdapter;
import com.wildcardenter.myfab.nanodegreemoviesproject.models.Movie;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView moviesGridRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moviesGridRecyclerView = findViewById(R.id.movies_grid_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL,
                false);
        MovieAdapter movieAdapter = new MovieAdapter(this);
        movieAdapter.refreshMovies(getDummyData());
        moviesGridRecyclerView.setLayoutManager(gridLayoutManager);
        moviesGridRecyclerView.setAdapter(movieAdapter);
    }

    private List<Movie> getDummyData() {
        List<Movie> movies = new ArrayList<>(4);
        movies.add(null);
        movies.add(null);
        movies.add(null);
        movies.add(null);
        movies.add(null);
        return movies;
    }

    private static class LoadMoviesAsync extends AsyncTask<String,Void,String> {
        WeakReference<MainActivity> weakReference;

        public LoadMoviesAsync(MainActivity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}
