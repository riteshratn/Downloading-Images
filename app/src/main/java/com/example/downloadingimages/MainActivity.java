package com.example.downloadingimages;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static androidx.appcompat.widget.AppCompatDrawableManager.get;

public class MainActivity extends AppCompatActivity {

    ImageView downloadedImg;

    @SuppressLint("RestrictedApi")
    public void downloadImage(View view){

       //

        imageDownloader task = new imageDownloader();
        Bitmap myImage;

        try {
            myImage = task.execute("https://vignette.wikia.nocookie.net/ben10/images/4/48/Gwen_Tennyson_AF_S3.png/revision/latest?cb=20190306022239").get();
            downloadedImg.setImageBitmap(myImage);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("interaction","Button Tapped");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadedImg =(ImageView)findViewById(R.id.imageView);
    }

      public class imageDownloader extends AsyncTask<String,Void, Bitmap>{

          @Override
          protected Bitmap doInBackground(String... urls) {
              try {

                  URL url =new URL(urls[0]);
                  HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                  connection.connect();
                  InputStream inputStream = connection.getInputStream();
                  Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                  return myBitmap;

              }
              catch (MalformedURLException e) {
                  e.printStackTrace();
              } catch (IOException e) {
                  e.printStackTrace();
              }

              return null;
          }
      }
}