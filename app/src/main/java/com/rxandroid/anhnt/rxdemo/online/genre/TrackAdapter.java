package com.rxandroid.anhnt.rxdemo.online.genre;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rxandroid.anhnt.rxdemo.R;
import com.rxandroid.anhnt.rxdemo.Track;

import java.util.ArrayList;
import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<Track> mTracks;
    private OnTrackClickListener mListener;
    private int mPosition = -1;
    private boolean mIsDownload;

    public TrackAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mTracks = new ArrayList<>();
    }

    public void setListener(OnTrackClickListener listener) {
        mListener = listener;
    }

    public void setPosition(int position, boolean isDownload) {
        mPosition = position;
        mIsDownload = isDownload;
        notifyItemChanged(position);
    }

    public int getPosition() {
        return mPosition;
    }

    public void updateData(List<Track> tracks) {
        if (tracks == null) {
            return;
        }
        mTracks.addAll(tracks);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_track_online, parent,
                false), mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Track track = mTracks.get(position);
        holder.bindData(track, mPosition, mIsDownload);
    }

    @Override
    public int getItemCount() {
        return mTracks == null ? 0 : mTracks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageAlbumCover;
        private ImageView mImageDownload;
        private TextView mTextArtist;
        private TextView mTextTitle;
        private OnTrackClickListener mListener;

        public ViewHolder(View itemView, OnTrackClickListener listener) {
            super(itemView);

            mImageAlbumCover = itemView.findViewById(R.id.image_album_cover);
            mImageDownload = itemView.findViewById(R.id.image_download);
            mTextArtist = itemView.findViewById(R.id.text_track_artist);
            mTextTitle = itemView.findViewById(R.id.text_track_title);
            mImageDownload.setOnClickListener(this);
            mListener = listener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener == null) return;
                    mListener.onClick(getAdapterPosition());
                }
            });
        }

        void bindData(Track track, int position, boolean isDownload) {
            if (track == null) {
                return;
            }
            mTextArtist.setText(track.getUsername());
            mTextTitle.setText(track.getTitle());
            Glide.with(mImageAlbumCover.getContext())
                    .load(track.getArtworkUrl())
                    .into(mImageAlbumCover);
            if (position == getAdapterPosition()) {
                if (isDownload) {
                    mImageDownload.setBackgroundResource(R.drawable.ic_cancel);
                } else {
                    mImageDownload.setBackgroundResource(R.drawable.ic_cloud_download);
                }
            }

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_download:
                    if (mListener == null) return;
                    mListener.onClickDownload(getAdapterPosition());
                    break;
            }
        }
    }

    public interface OnTrackClickListener {
        void onClick(int position);

        void onClickDownload(int position);
    }
}
