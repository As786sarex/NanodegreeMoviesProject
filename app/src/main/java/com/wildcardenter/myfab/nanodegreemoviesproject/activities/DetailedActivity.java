package com.wildcardenter.myfab.nanodegreemoviesproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.wildcardenter.myfab.nanodegreemoviesproject.R;
import com.wildcardenter.myfab.nanodegreemoviesproject.models.Movie;

import java.util.Objects;

import static com.wildcardenter.myfab.nanodegreemoviesproject.utils.Constants.IMAGE_URL_PREFIX;
import static com.wildcardenter.myfab.nanodegreemoviesproject.utils.Constants.INTENT_MOVIE_DATA;


public class DetailedActivity extends AppCompatActivity {

    private ImageView detailsThumbnailIv;
    private TextView detailsOriginalTitleTv, detailsReleaseDateTv, detailsAvgRatingTv, detailsOverviewTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Objects.requireNonNull(getSupportActionBar()).setTitle("MovieDetail");

        detailsThumbnailIv = findViewById(R.id.details_thumbnail_iv);
        detailsOriginalTitleTv = findViewById(R.id.details_original_title_tv);
        detailsReleaseDateTv = findViewById(R.id.details_release_date_tv);
        detailsAvgRatingTv = findViewById(R.id.details_avg_vote_tv);
        detailsOverviewTv = findViewById(R.id.details_overview_tv);

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
        }


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

}
