package com.example.apiwork;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EditClass extends AppCompatActivity {
    private ImageView editPhoto;
    private String encodedImage;
    TextInputLayout editName;
    TextInputLayout editWebsite;
    String name;
    String webSite;
    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editing_page);
        editPhoto = findViewById(R.id.editPhoto);
        editName = findViewById(R.id.editName);
        editWebsite = findViewById(R.id.editWebsite);

        AddingPage addingPage = new AddingPage();

        editName.getEditText().setText(MainActivity.nameText);
        editWebsite.getEditText().setText(MainActivity.webSiteText);
        editPhoto.setImageBitmap(MainActivity.imageBm);
        encodedImage = addingPage.EncodeImage(((BitmapDrawable)editPhoto.getDrawable()).getBitmap());
    }
    public void editRow(View v){
        if(editName.getEditText().getText().toString().isEmpty() ||
           editWebsite.getEditText().getText().toString().isEmpty()){
            Toast.makeText(this,"Введите все значения",Toast.LENGTH_SHORT).show();
            return;
        }
        putData(editName.getEditText().getText().toString(),editWebsite.getEditText().getText().toString(),
                encodedImage);
    }
    private void putData(String name, String webSite, String image) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl
                        ("https://ngknn.ru:5001/NGKNN/Зименковни/Api/airlines/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Mask modal = new Mask(MainActivity.currentId, name, webSite, image);

        //Call<Mask> call = retrofitAPI.updateData(modal.getId(), modal);

        /*call.enqueue(new Callback<Mask>() {
            @Override
            public void onResponse(@NonNull Call<Mask> call, @NonNull Response<Mask> response) {
                Toast.makeText(EditClass.this, "Data updated to API", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<Mask> call, @NonNull Throwable t) {
                Toast.makeText(EditClass.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
