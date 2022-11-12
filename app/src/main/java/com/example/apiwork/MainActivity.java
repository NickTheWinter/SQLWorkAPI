package com.example.apiwork;



import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AdapterMask pAdapter;
    View v;
    ListView airlinesList;
    private List<Mask> listAirlines = new ArrayList<>();
    Intent edit_page;
    public static String nameText;
    public static String webSiteText;
    public static ImageView image;
    public static Bitmap imageBm;
    public static int currentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        airlinesList = findViewById(R.id.AirList);
        pAdapter = new AdapterMask(MainActivity.this, listAirlines);

        edit_page = new Intent(MainActivity.this, EditClass.class);
        edit_page.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        airlinesList.setAdapter(pAdapter);
        updateList(v);

        airlinesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //listView.setOnItemClickListener((parent, view, position, id) ->{



                TextView nameTv = view.findViewById(R.id.AirlineName);
                nameText = nameTv.getText().toString();

                TextView webSiteTv = view.findViewById(R.id.AirlineWebsite);
                webSiteText = webSiteTv.getText().toString();

                image = view.findViewById(R.id.imageView);
                imageBm = ((BitmapDrawable)image.getDrawable()).getBitmap();



                TextInputLayout editName = findViewById(R.id.editName);
                edit_page.putExtra("editName",nameText);

                TextInputLayout editWebSite = findViewById(R.id.editWebsite);
                //editWebSite.getEditText().setText(webSiteText);
                edit_page.putExtra("editWebSite",webSiteText);


                edit_page.putExtra("editPhoto",imageBm);

                startActivity(edit_page);
            };
        });

    }
    public void updateList(View v){
        listAirlines.clear();
        try {

            new GetAirlines().execute();
        }
        catch (Exception ex){
            Log.e("Error: ",ex.getMessage());
        }
    }
    public void goAdd(View v){
        startActivity(new Intent(MainActivity.this,AddingPage.class));
    }
    private class GetAirlines extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://ngknn.ru:5001/NGKNN/%D0%97%D0%B8%D0%BC%D0%B5%D0%BD%D0%BA%D0%BE%D0%B2%D0%BD%D0%B8/api/airlines");
                //URL url = new URL("https://ngknn.ru:5001/NGKNN/%D0%9C%D0%B0%D0%BC%D1%88%D0%B5%D0%B2%D0%B0%D0%AE%D0%A1/api/Products");//Строка подключения к нашей API
                HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //вызываем нашу API

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                /*
                BufferedReader успрощает чтение текста из потока символов
                InputStreamReader преводит поток байтов в поток символов
                connection.getInputStream() получает поток байтов
                */
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    result.append(line);//кладет строковое значение в потоке
                }
                return result.toString();

            } catch (Exception exception) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                JSONArray tempArray = new JSONArray(s);//преоброзование строки в json массив
                for (int i = 0;i<tempArray.length();i++)
                {

                    JSONObject airlinesJson = tempArray.getJSONObject(i);//Преобразование json объекта в нашу структуру
                    Mask tempProduct = new Mask(
                            airlinesJson.getInt("ID"),
                            airlinesJson.getString("Name"),
                            airlinesJson.getString("Website"),
                            airlinesJson.getString("Image")
                    );
                    listAirlines.add(tempProduct);
                    pAdapter.notifyDataSetInvalidated();
                }
            } catch (Exception ignored) {


            }
        }


    }
}