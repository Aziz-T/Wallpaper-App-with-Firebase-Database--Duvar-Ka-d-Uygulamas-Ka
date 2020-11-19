package com.k.WallApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class KategoriWallActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private String kategoriAdi;
    private RecyclerView recyclerView;
    private List<WallpaperModel> wallpaperModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_wall);


        recyclerView=findViewById(R.id.recycleView);
        wallpaperModelList = new ArrayList<>();
        kategoriAdi=getIntent().getExtras().getString("kategoriAdi");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        final WallpaperAdapter wallpaperAdapter = new WallpaperAdapter(wallpaperModelList);
        recyclerView.setAdapter(wallpaperAdapter);
        Toast.makeText(getApplicationContext(),kategoriAdi,Toast.LENGTH_SHORT).show();

        myRef.child("Wallpapers").child(kategoriAdi).child("Walls").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    wallpaperModelList.add(dataSnapshot.getValue(WallpaperModel.class));
                }
                wallpaperAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}
