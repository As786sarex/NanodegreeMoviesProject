package com.wildcardenter.myfab.nanodegreemoviesproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wildcardenter.myfab.nanodegreemoviesproject.R;
import com.wildcardenter.myfab.nanodegreemoviesproject.models.Trailer;

import java.util.List;

import static com.wildcardenter.myfab.nanodegreemoviesproject.utils.Constants.YOUTUBE_THUMBNAIL_PREFIX;


/*
                                #  #           #  #     
    Created by Asif Mondal on 05-05-2020 at 20:59
*/


public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private List<Trailer> trailers;
    private Context mContext;
    private OnTrailerClickListener listener;

    public TrailerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void refreshTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    public void setOnTrailerClickListener(OnTrailerClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrailerViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.trailer_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        if (trailers != null) {
            Trailer trailer = trailers.get(position);
            Picasso.get()
                    .load(YOUTUBE_THUMBNAIL_PREFIX
                            + trailer.getKey() + "/0.jpg")
                    .into(holder.trailerThumbnailIv);
            holder.trailerTitleTv.setText(trailer.getName());
        }
    }

    @Override
    public int getItemCount() {
        return trailers == null ? 0 : trailers.size();
    }

    public interface OnTrailerClickListener {
        void onClick(int pos);
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {

        TextView trailerTitleTv;
        ImageView trailerThumbnailIv;

        TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            trailerTitleTv = itemView.findViewById(R.id.trailer_title_tv);
            trailerThumbnailIv = itemView.findViewById(R.id.trailer_thumbnail_Iv);
            itemView.findViewById(R.id.trailer_item_container).setOnClickListener(i -> {
                if (listener != null) {
                    int pos = getAdapterPosition();
                    listener.onClick(pos);
                }
            });
        }
    }
}
