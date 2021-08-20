package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomePage extends AppCompatActivity implements GerechtenCardsAdapter.OnNoteListener{
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private GerechtenCard[] gerechtenOntDekken;
    private String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        TextView appBarTitle =findViewById(R.id.applicationBarTitle);
        appBarTitle.setText("Home Page");

        recyclerView = findViewById(R.id.recyclerViewId);
        //recycler view heeft een layout manager nodig zodat alles netjes onder elkaar komt
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();

        //Standaard ontdek gerechten
        getGerechtenOntdekken();

        recyclerViewAdapter = new GerechtenCardsAdapter(gerechtenOntDekken, this);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    public void onNoteClick(int position) {
        Log.d("test123", "clicked");
        Intent intentGerechtDetails = new Intent(this, GerechtDetails.class);
        intentGerechtDetails.putExtra("geselecteerde_gerecht", gerechtenOntDekken[position]);
        startActivity(intentGerechtDetails);
    }

    private void getGerechtenOntdekken() {
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
            gerechtenOntDekken = new GerechtenCard[arrGerechten.length()];
            for (int i = 0; i < arrGerechten.length(); i++){
                //vars voor gerechtenontdekken object
                String gerecht_naam = arrGerechten.getJSONObject(i).getString("gerecht_naam");
                int aantal_personen = arrGerechten.getJSONObject(i).getInt("aantal_personen");
                String categorie = arrGerechten.getJSONObject(i).getString("categorie");

                //lists die in gerechten ontdekken gaan
                String ingredienten = arrGerechten.getJSONObject(i).getString("ingredienten");
                String instructies = arrGerechten.getJSONObject(i).getString("instructies");
                List<String> myListIngredienten = new ArrayList<String>(Arrays.asList(ingredienten.split(",")));
                List<String> myListInstructies = new ArrayList<String>(Arrays.asList(instructies.split(",")));
                for (int x = 0; x < myListIngredienten.size(); x++){
                    myListIngredienten.set(x, myListIngredienten.get(x).replaceAll("[^\\w\\s]",""));
                }
                for (int x = 0; x < myListInstructies.size(); x++){
                    myListInstructies.set(x, myListInstructies.get(x).replaceAll("[^\\w\\s]",""));
                }
                gerechtenOntDekken[i] = new GerechtenCard(gerecht_naam, aantal_personen, categorie, myListIngredienten, myListInstructies);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
