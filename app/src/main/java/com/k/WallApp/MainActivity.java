package com.k.WallApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();



    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<KategoriModel> kategoriModelList;
    private List<WallpaperModel> wallpaperModelList;
    private ImageView imageView;
    private ImageButton right , left;
    private int position;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Duvar Kağıtları");
        imageView=findViewById(R.id.imageView);
        recyclerView = findViewById(R.id.recycleView);
        right=findViewById(R.id.right);
        left = findViewById(R.id.left);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        progressBar=findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(linearLayoutManager);
        kategoriModelList= new ArrayList<>();
        wallpaperModelList = new ArrayList<>();

        final KategoriAdapter adaptor= new KategoriAdapter(kategoriModelList);
        recyclerView.setAdapter(adaptor);


        kategoriModelList.add(new KategoriModel(R.drawable.nat,"Doğa"));
        kategoriModelList.add(new KategoriModel(R.drawable.car,"Araba"));
        kategoriModelList.add(new KategoriModel(R.drawable.epic,"Epik"));
        kategoriModelList.add(new KategoriModel(R.drawable.spor,"Spor"));
        kategoriModelList.add(new KategoriModel(R.drawable.flm,"Sinema"));
        kategoriModelList.add(new KategoriModel(R.drawable.game,"Oyun"));



        myRef.child("Wallpaper").child("Wall").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    wallpaperModelList.add(dataSnapshot1.getValue(WallpaperModel.class));
                }

                Random random = new Random();
                position=random.nextInt(wallpaperModelList.size());

                Glide.with(getApplicationContext()).load(wallpaperModelList.get(position).getUrl()).into(imageView);
                right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(position==wallpaperModelList.size()-1){
                            Glide.with(getApplicationContext()).load(wallpaperModelList.get(position).getUrl()).into(imageView);
                            position++;
                            if(position==wallpaperModelList.size()){
                                position=0;
                                Glide.with(getApplicationContext()).load(wallpaperModelList.get(position).getUrl()).into(imageView);
                            }
                        }
                        else if(position<wallpaperModelList.size()){
                            position++;
                            Glide.with(getApplicationContext()).load(wallpaperModelList.get(position).getUrl()).into(imageView);

                        }

                    }
                });
                left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       if(position==0 ){
                           position=wallpaperModelList.size()-1;
                           Glide.with(getApplicationContext()).load(wallpaperModelList.get(position).getUrl()).into(imageView);
                       } else if(position>0){
                           position--;
                           Glide.with(getApplicationContext()).load(wallpaperModelList.get(position).getUrl()).into(imageView);
                       }

                    }
                });
                progressBar.setVisibility(imageView.getVisibility());

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, WallpaperActivity.class);
                        intent.putExtra("url1",wallpaperModelList.get(position).getUrl());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }
}
