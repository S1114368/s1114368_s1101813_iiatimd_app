package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomePage extends AppCompatActivity implements GerechtenCardsAdapter.OnNoteListener, EigenGerechtenCardsAdapter.OnNoteListener{
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManager2;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.Adapter recyclerViewAdapter2;
    private GerechtenCard[] gerechtenOntDekken;
    private GerechtenCard[] eigenGerechten;
    private GerechtenCard[] offlineEigenGerechten;
    private String json;
    private JSONArray userGerechten;
    private List<List<String>> userIngredienten = new ArrayList<List<String>>();
    private List<List<String>> userInstructies = new ArrayList<List<String>>();
    private RequestQueue queue;
    private Bundle bundleForHomepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        TextView appBarTitle =findViewById(R.id.applicationBarTitle);
        appBarTitle.setText("Home Page");
        Bundle myBundle = getIntent().getExtras();

        recyclerView = findViewById(R.id.recyclerViewId);
        //recycler view heeft een layout manager nodig zodat alles netjes onder elkaar komt
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();

        //Standaard ontdek gerechten
        getGerechtenOntdekken(myBundle.getString("categorie"));;
        getUserGerechten(myBundle.getInt("user_ID"), myBundle.getString("categorie"));

        recyclerViewAdapter = new GerechtenCardsAdapter(gerechtenOntDekken, this);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView2 = findViewById(R.id.recyclerViewId2);
        //recycler view heeft een layout manager nodig zodat alles netjes onder elkaar komt
        layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.hasFixedSize();
        recyclerView2.setAdapter(null);

        bundleForHomepage = new Bundle();
        bundleForHomepage.putInt("user_ID", myBundle.getInt("user_ID"));

    }

    private void getUserGerechten(int user_ID, String categorieFilter) {
        queue = Volley.newRequestQueue(this);
        String url ="http://10.0.2.2:8000/api/gerechten/" + String.valueOf(user_ID);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        userGerechten = response;
                        eigenGerechten = new GerechtenCard[userGerechten.length()];
                        setIngredientenList(userGerechten, categorieFilter);
                        }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", String.valueOf(error));
            }
        });
        queue.add(jsonArrayRequest);
    }

    public void setIngredientenList(JSONArray userGerechten, String categorieFilter) {
        int i = 0;
        for(i = 0; i < userGerechten.length(); i++){
            try {
                String url3 = "http://10.0.2.2:8000/api/gerechten/" + String.valueOf(userGerechten.getJSONObject(i).getString("gerecht_ID") + "/ingredient");
                JsonArrayRequest jsonArrayRequest3 = new JsonArrayRequest(Request.Method.GET, url3, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                userIngredienten.add(new ArrayList<String>());
                                int currentPosition = userIngredienten.size() - 1;
                                for(int x = 0; x < response.length(); x++){
                                    try {
                                        userIngredienten.get(currentPosition).add(String.valueOf(response.getJSONObject(x).getString("beschrijving_ingredient")));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                setInstructiesList(userGerechten, currentPosition, categorieFilter);
//                                userIngredienten
//                                try {
//                                    Log.d("testdietest", String.valueOf(instructieGerechtenList.get(0).getJSONObject(0).getString("beschrijving_ingredient")));
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", String.valueOf(error));
                    }
                });
                queue.add(jsonArrayRequest3);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setInstructiesList(JSONArray userGerechten, int counter, String categorieFilter) {
            try {
                String url3 = "http://10.0.2.2:8000/api/gerechten/" + String.valueOf(userGerechten.getJSONObject(counter).getString("gerecht_ID") + "/instructie");
                JsonArrayRequest jsonArrayRequest3 = new JsonArrayRequest(Request.Method.GET, url3, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                userInstructies.add(new ArrayList<String>());
                                int currentPosition = userInstructies.size() - 1;
                                for(int x = 0; x < response.length(); x++){
                                    try {
                                        userInstructies.get(currentPosition).add(String.valueOf(response.getJSONObject(x).getString("beschrijving_instructie")));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                try {
                                    String gerechtNaam = userGerechten.getJSONObject(currentPosition).getString("gerecht_naam");
                                    int aantal_personen = userGerechten.getJSONObject(currentPosition).getInt("aantal_personen");
                                    String categorie = userGerechten.getJSONObject(currentPosition).getString("categorie");
                                    List<String> instructies = userInstructies.get(currentPosition);
                                    List<String> ingredienten = userIngredienten.get(currentPosition);
                                    eigenGerechten[currentPosition] = new GerechtenCard(gerechtNaam, aantal_personen, categorie, ingredienten, instructies,  "false");
                                    if(userInstructies.size() == userGerechten.length()){
                                        if(categorieFilter != null && categorieFilter != "geen filter"){
                                            eigenGerechten = filterGerechten(categorieFilter, userGerechten, eigenGerechten);
                                        }
                                        maakEigenGerechten(eigenGerechten);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
//                                gerechtenOntDekken[i] = new GerechtenCard(gerecht_naam, aantal_personen, categorie, myListIngredienten, myListInstructies);
//                                instructieGerechtenList.add(response);
//                                try {
//                                    Log.d("supertest2", instructieGerechtenList.get(0).getJSONObject(0).getString("beschrijving_instructie"));
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", String.valueOf(error));
                    }
                });
                queue.add(jsonArrayRequest3);
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onNoteClick(int position) {
        Log.d("test123", "clicked");
        Intent intentGerechtDetails = new Intent(this, GerechtDetails.class);
        intentGerechtDetails.putExtras(bundleForHomepage);

        intentGerechtDetails.putExtra("geselecteerde_gerecht", gerechtenOntDekken[position]);
        startActivity(intentGerechtDetails);
    }

    private void maakEigenGerechten(GerechtenCard[] eigenGerechten) {
        AppDatabase database = AppDatabase.getInstance(this);
        if(offlineEigenGerechten.length == 0){
            for(int i=0; i>eigenGerechten.length; i++){
                database.gerechtenCardDao().insertGerechtCard(eigenGerechten[i]);
            }
            offlineEigenGerechten = database.gerechtenCardDao().getAll();
            recyclerViewAdapter2 = new EigenGerechtenCardsAdapter(offlineEigenGerechten, this);
            recyclerView2.setAdapter(recyclerViewAdapter2);
        }
        else{
            recyclerViewAdapter2 = new EigenGerechtenCardsAdapter(offlineEigenGerechten, this);
            recyclerView2.setAdapter(recyclerViewAdapter2);
        }
    }

    public void naarFilterPagina(View v){
        Intent intentFilter = new Intent(this, FilterPagina.class);
        intentFilter.putExtras(bundleForHomepage);
        startActivity(intentFilter);
    }

    @Override
    public void onNoteClickEigenGerecht(int position) {
        Log.d("test123", "clicked");
        Intent intentGerechtDetails = new Intent(this, GerechtDetails.class);
        intentGerechtDetails.putExtras(bundleForHomepage);
        intentGerechtDetails.putExtra("geselecteerde_gerecht", eigenGerechten[position]);
        startActivity(intentGerechtDetails);
    }

    private void getGerechtenOntdekken(String categorieFilter) {
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
                gerechtenOntDekken[i] = new GerechtenCard(gerecht_naam, aantal_personen, categorie, myListIngredienten, myListInstructies, "true");
            }
            Log.d("heyheyhey", categorieFilter + " emtpy");
            if(categorieFilter != null && categorieFilter != "geen filter"){
                gerechtenOntDekken = filterGerechten(categorieFilter, arrGerechten, gerechtenOntDekken);
            }
//            if(categorieFilter != null && categorieFilter != "geen filter"){
//                int indexCounter = 0;
//                for(int x = 0; x < arrGerechten.length(); x++){
//                    if(gerechtenOntDekken[x].getCategorie().equals(categorieFilter)){
//                        indexCounter++;
//                    }
//                }
//                GerechtenCard[] gerechtenOntDekkenFilter = new GerechtenCard[indexCounter];
//                indexCounter = 0;
//                for(int x = 0; x < arrGerechten.length(); x++){
//                    if(gerechtenOntDekken[x].getCategorie().equals(categorieFilter)){
//                        gerechtenOntDekkenFilter[indexCounter] = gerechtenOntDekken[x];
//                        indexCounter++;
//                    }
//                }
//                gerechtenOntDekken = gerechtenOntDekkenFilter;
//            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public GerechtenCard[] filterGerechten(String categorieFilter, JSONArray arrGerechten, GerechtenCard[] gerechten){
            int indexCounter = 0;
            for(int x = 0; x < arrGerechten.length(); x++){
                if(gerechten[x].getCategorie().equals(categorieFilter)){
                    indexCounter++;
                }
            }
            GerechtenCard[] gerechtenOntDekkenFilter = new GerechtenCard[indexCounter];
            indexCounter = 0;
            for(int x = 0; x < arrGerechten.length(); x++){
                if(gerechten[x].getCategorie().equals(categorieFilter)){
                    gerechtenOntDekkenFilter[indexCounter] = gerechten[x];
                    indexCounter++;
                }
            }
            gerechten = gerechtenOntDekkenFilter;
            return gerechten;
    }
}
