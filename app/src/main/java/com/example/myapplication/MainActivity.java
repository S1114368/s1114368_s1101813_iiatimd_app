package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button toSecondScreenButton;
    private String json;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toSecondScreenButton = findViewById(R.id.buttonLogin);
        toSecondScreenButton.setOnClickListener(this);
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open("data.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            inputStream.read(buffer);
            inputStream.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("jsontest", json);
        try {
            JSONObject object = new JSONObject(json);
            JSONArray arrGerechten = object.getJSONArray("gerechtData");
            for (int i = 0; i < arrGerechten.length(); i++){
                Log.d("werkpls", arrGerechten.getJSONObject(i).getString("gerecht_naam"));
                String ingredienten;
                ingredienten = arrGerechten.getJSONObject(i).getString("ingredienten");
                List<String> myList = new ArrayList<String>(Arrays.asList(ingredienten.split(",")));
                for (int x = 0; x < myList.size(); x++){
                    myList.set(x, myList.get(x).replaceAll("[^\\w\\s]",""));
                    Log.d("werkpls2", myList.get(x));
                }

            }
//            JSONArray jsonArray = (JSONArray) object.get("ingredienten");
//            List<String> list = new ArrayList<String>();
//            for(int i = 0; i < jsonArray.length(); i++){
//                list.add(jsonArray.get(i).toString());
//            }
//            Log.d("werkpls", list.toString());
            Log.d("werkpls", "xd");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        Intent toSecondScreenIntent = new Intent(this, HomePage.class);
        startActivity(toSecondScreenIntent);
    }
}