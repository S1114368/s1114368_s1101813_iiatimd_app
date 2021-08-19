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

public class HomePage extends AppCompatActivity implements GerechtenCardsAdapter.OnNoteListener{
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private GerechtenCard[] gerechtenOntDekken;

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
        gerechtenOntDekken = new GerechtenCard[4];
        gerechtenOntDekken[0] = new GerechtenCard(R.drawable.kip, "Lekker kippie", 0);
        gerechtenOntDekken[1] = new GerechtenCard(R.drawable.rund, "Lekker rundvlees", 1);
        gerechtenOntDekken[2] = new GerechtenCard(R.drawable.vega, "Lekker vegatarisch", 2);
        gerechtenOntDekken[3] = new GerechtenCard(R.drawable.vis, "Lekker vissie", 3);

        recyclerViewAdapter = new GerechtenCardsAdapter(gerechtenOntDekken, this);
        recyclerView.setAdapter(recyclerViewAdapter);

//        recyclerViewAdapter = new GerechtenCardsAdapter(grechtenOntdekken);

    }

    @Override
    public void onNoteClick(int position) {
        Log.d("test123", "clicked");
        Intent intentGerechtDetails = new Intent(this, GerechtDetails.class);
        Gson gson = new Gson();
        String myJson = gson.toJson(gerechtenOntDekken[position]);
        intentGerechtDetails.putExtra("gerecht", myJson);
        startActivity(intentGerechtDetails);
    }

//    @Override
//    public void onNoteClick(int position) {
////        gerechtenOntDekken[position].getUuid();
////        Intent intent = new Intent(this, GerechtDetails.class);
////        startActivity(intent);
//
//    }
}
