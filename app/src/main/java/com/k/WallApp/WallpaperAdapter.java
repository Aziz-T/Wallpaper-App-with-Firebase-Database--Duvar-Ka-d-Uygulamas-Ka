package com.k.WallApp;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.ViewHolder> {

   private List<WallpaperModel> WallpaperModelList;

    public WallpaperAdapter(List<WallpaperModel> wallpaperModelList) {
        WallpaperModelList = wallpaperModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaper_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(WallpaperModelList.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return WallpaperModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.wallpaperImageView);
            progressBar=itemView.findViewById(R.id.progressBarImage);
        }

        public void setData(final String url) {
            progressBar.setVisibility(View.VISIBLE);
            Glide.with(itemView.getContext()).load(url).into(imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent setIntent = new Intent(itemView.getContext(), WallpaperActivity.class);
                    setIntent.putExtra("url", url);
                    itemView.getContext().startActivity(setIntent);
                }
            });
        }
    }
}


