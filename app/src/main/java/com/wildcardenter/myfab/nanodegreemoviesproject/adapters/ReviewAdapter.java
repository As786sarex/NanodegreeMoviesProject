package com.wildcardenter.myfab.nanodegreemoviesproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wildcardenter.myfab.nanodegreemoviesproject.R;
import com.wildcardenter.myfab.nanodegreemoviesproject.models.Review;

import java.util.List;


/*
                                #  #           #  #     
    Created by Asif Mondal on 05-05-2020 at 21:59
*/


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private Context context;
    private List<Review> reviews;

    public ReviewAdapter(Context context) {
        this.context = context;
    }

    public void refreshReviewList(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.review_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        if (reviews != null) {
            Review review = reviews.get(position);
            holder.reviewAuthorTv.setText(review.getAuthor());
            holder.reviewContentTv.setText(review.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return reviews == null ? 0 : reviews.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView reviewContentTv, reviewAuthorTv;

        ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewAuthorTv = itemView.findViewById(R.id.review_author_tv);
            reviewContentTv = itemView.findViewById(R.id.review_content_tv);
        }
    }
}
