package com.example.apiwork;

import android.content.Intent;
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
    AddingPage addingPage;
    int airline_id;
    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editing_page);

        editPhoto = findViewById(R.id.editPhoto);
        editName = findViewById(R.id.editName);
        editWebsite = findViewById(R.id.editWebsite);

        editName.getEditText().setText(MainActivity.nameText);
        editWebsite.getEditText().setText(MainActivity.webSiteText);
        editPhoto.setImageBitmap(MainActivity.imageBm);

        addingPage = new AddingPage();
        encodedImage = addingPage.EncodeImage(((BitmapDrawable)editPhoto.getDrawable()).getBitmap());

        airline_id = MainActivity.currentId;
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
    public void deleteRow(View v){
        deleteData();
    }
    private void deleteData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl
                        ("https://ngknn.ru:5001/NGKNN/Зименковни/Api/airlines/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<Mask> call = retrofitAPI.deletePost(airline_id);
        call.enqueue(new Callback<Mask>() {
            @Override
            public void onResponse(@NonNull Call<Mask> call, @NonNull Response<Mask> response) {
                Toast.makeText(EditClass.this, "Data was deleted", Toast.LENGTH_SHORT).show();
                back(v);
            }

            @Override
            public void onFailure(@NonNull Call<Mask> call, @NonNull Throwable t) {
                Toast.makeText(EditClass.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void ImageChoose(View v){
        addingPage.ImageChoose(v);
    }
    private void putData(String airline_name, String airline_website, String image) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl
                        ("https://ngknn.ru:5001/NGKNN/Зименковни/Api/airlines/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);


        Mask modal = new Mask(airline_id, airline_name, airline_website, image);

        Call<Mask> call = retrofitAPI.updatePost(modal.getAirline_id(), modal);

        call.enqueue(new Callback<Mask>() {
            @Override
            public void onResponse(@NonNull Call<Mask> call, @NonNull Response<Mask> response) {
                Toast.makeText(EditClass.this, "Data updated", Toast.LENGTH_SHORT).show();
                back(v);
            }

            @Override
            public void onFailure(@NonNull Call<Mask> call, @NonNull Throwable t) {
                Toast.makeText(EditClass.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goMain(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    public void back (View v) {
        goMain();
    }
}
