package com.example.apiwork;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import android.widget.Toast;


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

        postData(editName.getEditText().getText().toString(), editWebsite.getEditText().getText().toString(), encodedImage);
    }
    private void postData(String name, String website, String encodedImage) {

        // below line is for displaying our progress bar.

        // on below line we are creating a retrofit
        // builder and passing our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/%D0%97%D0%B8%D0%BC%D0%B5%D0%BD%D0%BA%D0%BE%D0%B2%D0%BD%D0%B8/api/")
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        // passing data from our text fields to our modal class.
        DataModal modal = new DataModal(name, website,encodedImage);

        // calling a method to create a post and passing our modal class.
        Call<DataModal> call = retrofitAPI.createPost(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                // this method is called when we get response from our api.

                // below line is for hiding our progress bar.

                // on below line we are setting empty text
                // to our both edit text.
                editWebsite.getEditText().setText("");
                editName.getEditText().setText("");

                // we are getting response from our body
                // and passing it to our modal class.
                DataModal responseFromAPI = response.body();

                // on below line we are getting our data from modal class and adding it to our string.
                String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getName() + "\n" + "Website : " + responseFromAPI.getWebSite();

                // below line we are setting our
                // string to our text view.
            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
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
        this.finish();
    }
}
