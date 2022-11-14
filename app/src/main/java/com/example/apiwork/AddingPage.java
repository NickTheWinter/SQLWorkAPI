package com.example.apiwork;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddingPage extends AppCompatActivity {

    private ImageView addPhoto;
    private String encodedImage;
    TextInputLayout editName;
    TextInputLayout editWebsite;
    String Name;
    String Website;
    String Image;
    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_page);
        addPhoto = findViewById(R.id.addPhoto);
        editName = findViewById(R.id.addName);
        editWebsite = findViewById(R.id.addWebsite);
    }

    public void AddItems(View v){
        encodedImage = EncodeImage(((BitmapDrawable)addPhoto.getDrawable()).getBitmap());
        Image = encodedImage;
        Name = editName.getEditText().getText().toString();
        Website = editWebsite.getEditText().getText().toString();
        if(Name.isEmpty() || Website.isEmpty() || Image.isEmpty())
        {
            Toast.makeText(this, "Введите все значения",Toast.LENGTH_SHORT).show();
            return;
        }
        postData(Name, Website, Image);

    }
    private void goMain(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    private void postData(String airline_name, String airline_website, String image) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/Зименковни/api/airlines/")

                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Mask modal = new Mask(0,airline_name, airline_website,image);

        Call<Mask> call = retrofitAPI.createPost(modal);

        call.enqueue(new Callback<Mask>() {
            @Override
            public void onResponse(Call<Mask> call, Response<Mask> response) {
                Toast.makeText(AddingPage.this,"Данные добавлены",Toast.LENGTH_SHORT).show();
                Back(v);
            }

            @Override
            public void onFailure(Call<Mask> call, Throwable t) {
                Toast.makeText(AddingPage.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void ImageChoose(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImg.launch(intent);
    }
    //отдельный метод для открытия
    private final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri uri = result.getData().getData();
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    addPhoto.setImageBitmap(bitmap);
                    encodedImage = EncodeImage(bitmap);
                } catch (Exception e) {

                }
            }
        }
    });
    public String EncodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(bytes);
        }
        return "";
    }

    public void Back (View v) {
        goMain();
    }
}
