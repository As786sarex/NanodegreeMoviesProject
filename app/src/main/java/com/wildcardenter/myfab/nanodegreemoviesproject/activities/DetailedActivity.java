package com.wildcardenter.myfab.nanodegreemoviesproject.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wildcardenter.myfab.nanodegreemoviesproject.R;
import com.wildcardenter.myfab.nanodegreemoviesproject.adapters.ReviewAdapter;
import com.wildcardenter.myfab.nanodegreemoviesproject.adapters.TrailerAdapter;
import com.wildcardenter.myfab.nanodegreemoviesproject.database.FavoriteMoviesViewModel;
import com.wildcardenter.myfab.nanodegreemoviesproject.models.Movie;
import com.wildcardenter.myfab.nanodegreemoviesproject.models.Review;
import com.wildcardenter.myfab.nanodegreemoviesproject.models.Trailer;
import com.wildcardenter.myfab.nanodegreemoviesproject.utils.JsonParse;
import com.wildcardenter.myfab.nanodegreemoviesproject.utils.UrlConnection;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

import static com.wildcardenter.myfab.nanodegreemoviesproject.utils.Constants.API_KEY;
import static com.wildcardenter.myfab.nanodegreemoviesproject.utils.Constants.API_KEY_QUERY;
import static com.wildcardenter.myfab.nanodegreemoviesproject.utils.Constants.IMAGE_URL_PREFIX;
import static com.wildcardenter.myfab.nanodegreemoviesproject.utils.Constants.INTENT_MOVIE_DATA;
import static com.wildcardenter.myfab.nanodegreemoviesproject.utils.Constants.MOVIES_API_URL;
import static com.wildcardenter.myfab.nanodegreemoviesproject.utils.Constants.REVIEW_API_SUFFIX;
import static com.wildcardenter.myfab.nanodegreemoviesproject.utils.Constants.TRAILER_API_SUFFIX;
import static com.wildcardenter.myfab.nanodegreemoviesproject.utils.Constants.TRAILER_URL_PREFIX;


public class DetailedActivity extends AppCompatActivity {

    private ImageView detailsThumbnailIv;
    private TextView detailsOriginalTitleTv, detailsReleaseDateTv, detailsAvgRatingTv, detailsOverviewTv;
    private Button addFavBtn;
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;

    private List<Trailer> trailers;
    private List<Review> reviews;

    private FavoriteMoviesViewModel viewModel;
    private Observer<Long> isFavObserver;

    private LoadTrailersAsync loadTrailersAsync;
    private LiveData<Long> isFavLiveData;
    private LoadReviewsAsync loadReviewsAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        viewModel = new ViewModelProvider(this).get(FavoriteMoviesViewModel.class);

        Objects.requireNonNull(getSupportActionBar()).setTitle("MovieDetail");

        detailsThumbnailIv = findViewById(R.id.details_thumbnail_iv);
        detailsOriginalTitleTv = findViewById(R.id.details_original_title_tv);
        detailsReleaseDateTv = findViewById(R.id.details_release_date_tv);
        detailsAvgRatingTv = findViewById(R.id.details_avg_vote_tv);
        detailsOverviewTv = findViewById(R.id.details_overview_tv);
        addFavBtn = findViewById(R.id.detailed_favorite_button);

        RecyclerView trailerRecycler = findViewById(R.id.trailer_recycler);
        trailerRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        trailerAdapter = new TrailerAdapter(this);
        trailerRecycler.setAdapter(trailerAdapter);

        RecyclerView reviewRecycler = findViewById(R.id.review_recycler);
        reviewRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        reviewAdapter = new ReviewAdapter(this);
        reviewRecycler.setAdapter(reviewAdapter);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        Movie movie = null;
        if (intent != null) {
            movie = (Movie) intent.getSerializableExtra(INTENT_MOVIE_DATA);
        }
        if (movie == null) {
            closeOnError();
        } else {
            populateUI(movie);
            Movie finalMovie = movie;
            isFavLiveData = viewModel.isFabPresent(movie.getId());
            isFavObserver = aLong -> {
                viewModel.FAVORITE_TRIGGER = (aLong != null && aLong.equals(finalMovie.getId()));
                if (viewModel.FAVORITE_TRIGGER) {
                    addFavBtn.setText(R.string.remove_favorite);
                } else {
                    addFavBtn.setText(R.string.add_favorite);
                }
            };
            isFavLiveData.observe(this, isFavObserver);

            addFavBtn.setOnClickListener(i -> {
                setFavoriteState(viewModel.FAVORITE_TRIGGER, finalMovie);
            });
            loadTrailersAsync = new LoadTrailersAsync(this);
            loadTrailersAsync.execute(MOVIES_API_URL + movie.getId() + TRAILER_API_SUFFIX + API_KEY_QUERY + API_KEY);
            loadReviewsAsync = new LoadReviewsAsync(this);
            loadReviewsAsync.execute(MOVIES_API_URL + movie.getId() + REVIEW_API_SUFFIX + API_KEY_QUERY + API_KEY);
            trailerAdapter.setOnTrailerClickListener(pos -> {
                if (trailers != null) {
                    String key = trailers.get(pos).getKey();
                    Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(TRAILER_URL_PREFIX + key));
                    try {
                        startActivity(appIntent);
                    } catch (ActivityNotFoundException ex) {
                        startActivity(webIntent);
                    }
                }

            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy: ");
        loadTrailersAsync.cancel(true);
        loadReviewsAsync.cancel(true);
    }

    private void setFavoriteState(boolean already, Movie movie) {
        if (!already) {
            addFavBtn.setText(R.string.remove_favorite);
            viewModel.addFavoriteMovie(movie);
        } else {
            addFavBtn.setText(R.string.add_favorite);
            viewModel.deleteFavoriteMovie(movie.getId());
        }
        viewModel.FAVORITE_TRIGGER = !already;
    }

    private void populateUI(Movie movie) {
        Picasso.get()
                .load(IMAGE_URL_PREFIX + movie.getPosterPath())
                .into(detailsThumbnailIv);

        if (movie.getOriginalTitle() == null || movie.getOriginalTitle().isEmpty())
            detailsOriginalTitleTv.setText(R.string.detail_error_message);
        else
            detailsOriginalTitleTv.setText(movie.getOriginalTitle());

        if (movie.getReleaseDate() == null || movie.getReleaseDate().isEmpty())
            detailsReleaseDateTv.setText(R.string.detail_error_message);
        else
            detailsReleaseDateTv.setText(movie.getReleaseDate());

        if (movie.getOverview() == null || movie.getOverview().isEmpty())
            detailsOverviewTv.setText(R.string.detail_error_message);
        else
            detailsOverviewTv.setText(movie.getOverview());

        String avgVoteS = movie.getVoteAverage() + "/10";
        detailsAvgRatingTv.setText(avgVoteS);

    }


    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    //Util methods
    void refreshTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        trailerAdapter.refreshTrailers(trailers);
    }


    void refreshReviews(List<Review> reviews) {
        this.reviews = reviews;
        reviewAdapter.refreshReviewList(reviews);
    }

    //AsyncTasks
    private static class LoadTrailersAsync extends AsyncTask<String, Void, List<Trailer>> {

        private WeakReference<DetailedActivity> reference;

        LoadTrailersAsync(DetailedActivity activity) {
            this.reference = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Trailer> trailers) {
            super.onPostExecute(trailers);
            reference.get().refreshTrailers(trailers);
        }

        @Override
        protected List<Trailer> doInBackground(String... strings) {
            final String respJson = UrlConnection.loadMoviesFromUrl(strings[0]);
            return JsonParse.parseTrailerFromJson(respJson);
        }
    }

    //AsyncTasks
    private static class LoadReviewsAsync extends AsyncTask<String, Void, List<Review>> {

        private WeakReference<DetailedActivity> reference;

        LoadReviewsAsync(DetailedActivity activity) {
            this.reference = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Review> reviews) {
            super.onPostExecute(reviews);
            Log.e("", "onPostExecute: " + reviews);
            reference.get().refreshReviews(reviews);
        }

        @Override
        protected List<Review> doInBackground(String... strings) {
            final String respJson = UrlConnection.loadMoviesFromUrl(strings[0]);
            return JsonParse.parseReviewFromJson(respJson);
        }
    }

}
