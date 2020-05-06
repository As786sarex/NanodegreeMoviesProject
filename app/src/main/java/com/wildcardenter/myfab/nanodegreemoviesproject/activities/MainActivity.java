package com.wildcardenter.myfab.nanodegreemoviesproject.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wildcardenter.myfab.nanodegreemoviesproject.R;
import com.wildcardenter.myfab.nanodegreemoviesproject.adapters.MovieAdapter;
import com.wildcardenter.myfab.nanodegreemoviesproject.database.FavoriteMoviesViewModel;
import com.wildcardenter.myfab.nanodegreemoviesproject.models.Movie;
import com.wildcardenter.myfab.nanodegreemoviesproject.utils.JsonParse;
import com.wildcardenter.myfab.nanodegreemoviesproject.utils.UrlConnection;

import java.lang.ref.WeakReference;
import java.util.List;

import static com.wildcardenter.myfab.nanodegreemoviesproject.utils.Constants.API_KEY;
import static com.wildcardenter.myfab.nanodegreemoviesproject.utils.Constants.API_KEY_QUERY;
import static com.wildcardenter.myfab.nanodegreemoviesproject.utils.Constants.BY_POPULAR;
import static com.wildcardenter.myfab.nanodegreemoviesproject.utils.Constants.BY_RATING;
import static com.wildcardenter.myfab.nanodegreemoviesproject.utils.Constants.INTENT_MOVIE_DATA;
import static com.wildcardenter.myfab.nanodegreemoviesproject.utils.Constants.MOVIES_API_URL;

public class MainActivity extends AppCompatActivity {
    private List<Movie> movies;
    private List<Movie> favoriteMovies;
    private MovieAdapter movieAdapter;
    private ProgressBar load_movies_pb;
    private RecyclerView moviesGridRecyclerView;
    private FavoriteMoviesViewModel favViewModel;
    private boolean isFavShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesGridRecyclerView = findViewById(R.id.movies_grid_recycler_view);
        load_movies_pb = findViewById(R.id.load_progressbar);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL,
                false);
        movieAdapter = new MovieAdapter(this);
        moviesGridRecyclerView.setLayoutManager(gridLayoutManager);
        moviesGridRecyclerView.setAdapter(movieAdapter);
        movieAdapter.setOnMovieClickListener(pos -> {
            if (movies != null) {
                Intent msgIntent = new Intent(getApplicationContext(), DetailedActivity.class);
                msgIntent.putExtra(INTENT_MOVIE_DATA, movies.get(pos));
                startActivity(msgIntent);
            }
        });
        favViewModel = new ViewModelProvider(this).get(FavoriteMoviesViewModel.class);
        favViewModel.getAllFavoriteMovies().observe(this, list -> {
            favoriteMovies = list;
            if (isFavShown)
                movieAdapter.notifyDataSetChanged();
        });
        new LoadMoviesAsync(this).execute(MOVIES_API_URL + BY_POPULAR + API_KEY_QUERY + API_KEY);
    }

    public void showLoadingProgressBar() {
        this.moviesGridRecyclerView.setVisibility(View.GONE);
        this.load_movies_pb.setVisibility(View.VISIBLE);
    }

    public void hideLoadingProgressBar() {
        this.load_movies_pb.setVisibility(View.GONE);
        this.moviesGridRecyclerView.setVisibility(View.VISIBLE);
    }

    private void refreshMovieList(List<Movie> movies) {
        this.movies = movies;
        movieAdapter.refreshMovies(movies);
        movieAdapter.notifyDataSetChanged();
    }

    private void sortByMostPopular() {
        isFavShown = false;
        new LoadMoviesAsync(this).execute(MOVIES_API_URL + BY_POPULAR + API_KEY_QUERY + API_KEY);
    }

    private void sortByMostRating() {
        isFavShown = false;
        new LoadMoviesAsync(this).execute(MOVIES_API_URL + BY_RATING + API_KEY_QUERY + API_KEY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sorting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sort_by_popularity) {
            sortByMostPopular();
        } else if (id == R.id.sort_by_rating) {
            sortByMostRating();
        } else if (id == R.id.show_favorites) {
            isFavShown = true;
            refreshMovieList(favoriteMovies);
        }
        return true;
    }


    private static class LoadMoviesAsync extends AsyncTask<String, Void, List<Movie>> {
        private WeakReference<MainActivity> weakReference;

        LoadMoviesAsync(MainActivity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.weakReference.get().showLoadingProgressBar();
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            this.weakReference.get().hideLoadingProgressBar();
            weakReference.get().refreshMovieList(movies);
        }

        @Override
        protected List<Movie> doInBackground(String... strings) {
            String jsonString = UrlConnection.loadMoviesFromUrl(strings[0]);
            return JsonParse.parseMoviesFromJson(jsonString);
        }
    }
}
