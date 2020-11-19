package com.k.WallApp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.karan.churi.PermissionManager.PermissionManager;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class WallpaperActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageButton setBack;
    private ImageButton downLoad;
    private String url,url1;
    PermissionManager permissionManager;



    WallpaperManager wallpaperManager ;
    Bitmap bitmap1, bitmap2 ;
    DisplayMetrics displayMetrics ;
    int width, height;
    BitmapDrawable bitmapDrawable ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);
        imageView=findViewById(R.id.bigImageView);
        setBack=findViewById(R.id.setBack);
        downLoad=findViewById(R.id.loadToGallery);
        permissionManager=new PermissionManager() {};
        url=getIntent().getExtras().getString("url");
        url1=getIntent().getExtras().getString("url1");
       if(url!=null && url1==null){
            Glide.with(this).load(url).into(imageView);
        } else if(url1!=null && url==null) {
            Glide.with(this).load(url1).into(imageView);
        }


        setBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBack();
            }
        });

        downLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissionManager.checkAndRequestPermissions(WallpaperActivity.this);
                downLoadImage();
            }
        });
    }




    private void setBack(){
        wallpaperManager  = WallpaperManager.getInstance(getApplicationContext());

        bitmapDrawable = (BitmapDrawable) imageView.getDrawable();

        bitmap1 = bitmapDrawable.getBitmap();

        GetScreenWidthHeight();
        SetBitmapSize();

        wallpaperManager = WallpaperManager.getInstance(WallpaperActivity.this);

        try {

            wallpaperManager.setBitmap(bitmap2);

            wallpaperManager.suggestDesiredDimensions(width, height);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Bitmap viewToBitmap(View view,int width,int height){
        Bitmap bm=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bm);
        view.draw(canvas);
        return bm;
    }
    private void downLoadImage() {
        FileOutputStream fileOutputStream=null;
        File file=getDisc();
        if (!file.exists()&&!file.mkdirs()){
            Toast.makeText(this,"Can't create directory to save image!",Toast.LENGTH_SHORT).show();
            return;
        }
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyymmhhssmm");
        String date=simpleDateFormat.format(new Date());
        String name="Img"+date+".jpg";
        String file_name=file.getAbsolutePath()+"/"+name;
        File new_file=new File(file_name);
        try {
            fileOutputStream=new FileOutputStream(new_file);
            Bitmap bitmap=viewToBitmap(imageView, imageView.getWidth(),imageView.getHeight());
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            Toast.makeText(this,"Galeriye Kaydedildi!",Toast.LENGTH_SHORT).show();
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshGallery(new_file);
    }
    public void refreshGallery(File file){
        Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        sendBroadcast(intent);
    }
    private File getDisc(){
        File file= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        return new File(file,"Wallzone");
    }


    public void GetScreenWidthHeight(){

        displayMetrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        width = displayMetrics.widthPixels;

        height = displayMetrics.heightPixels;

    }
    public void SetBitmapSize(){

        bitmap2 = Bitmap.createScaledBitmap(bitmap1, width, height, false);

    }



}









