package com.k.WallApp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.ViewHolder> {
    private List<KategoriModel> kategoriModelist;
    public KategoriAdapter(List<KategoriModel> kategoriModelis) {
        this.kategoriModelist = kategoriModelis;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kategori_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(kategoriModelist.get(position).getKategoriAdi(),kategoriModelist.get(position).getResimUrl());
    }

    @Override
    public int getItemCount() {
        return kategoriModelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageView;
        private TextView baslik;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.circleImage);
            baslik=itemView.findViewById(R.id.baslik);
        }

        public void setData(final String kategoriAdi, int resimUrl) {
            this.imageView.setImageResource(resimUrl);
            this.baslik.setText(kategoriAdi);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent setIntent = new Intent(itemView.getContext(),KategoriWallActivity.class);
                    setIntent.putExtra("kategoriAdi",kategoriAdi);
                    itemView.getContext().startActivity(setIntent);
                }
            });
        }
    }
}
