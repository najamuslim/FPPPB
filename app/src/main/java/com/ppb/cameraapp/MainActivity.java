package com.ppb.cameraapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ppb.cameraapp.Response.ApiResponse;
import com.ppb.cameraapp.Retrofit.ApiClient;
import com.ppb.cameraapp.Retrofit.ApiServer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button b1;
    ImageView iv;
    Context context;
    private static final int kodekamera = 222;
    private static final int MY_PERMISSIONS_REQUEST_WRITE = 223;
    String nmFile;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        askWritePermission();
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.button);
        iv = (ImageView) findViewById(R.id.imageView);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(it, kodekamera);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case (kodekamera):
                    try {
                        prosesKamera(data);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
    private void prosesKamera(Intent datanya) throws IOException{
        Bitmap bm;
        bm = (Bitmap) datanya.getExtras().get("data");
        iv.setImageBitmap(bm);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        // convert camera photo to byte array
        // save it in your external storage.

        /*File dir= new File(Environment.getExternalStorageDirectory(), "HasilFoto");
        dir.mkdirs();
        Date d = new Date();
        CharSequence s = DateFormat.format("MM-dd-yy hh-mm-ss", d.getTime());
        File output=new File(dir, s.toString() + ".png");
        FileOutputStream fo = new FileOutputStream(output);
        fo.write(byteArray);
        fo.flush();
        fo.close();

        Toast.makeText(this,"Data Telah Terload ke ImageView",Toast.LENGTH_SHORT).show();*/

        //Store dataset
        ApiClient api = ApiServer.builder().create(ApiClient.class);
        Call<ApiResponse> call = api.dataset(3,"jamur","data:image/png;base64,"+ Base64.encodeToString(byteArray, Base64.DEFAULT));
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.code()==201)
                {
                    gson.fromJson(gson.toJson(response.body().getMessage()),ApiResponse.class);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
        Toast.makeText(this,"Data Telah diupload",Toast.LENGTH_SHORT).show();
    }

    private void askWritePermission() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
        int cameraPermission = this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE);}
        }
    }

}
