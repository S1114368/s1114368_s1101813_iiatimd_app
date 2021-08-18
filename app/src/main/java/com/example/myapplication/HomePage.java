package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomePage extends AppCompatActivity {
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;

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
        final GerechtenCard[] gerechtenOntDekken = new GerechtenCard[4];
        gerechtenOntDekken[0] = new GerechtenCard(R.drawable.kip, "Lekker kippie", 0);
        gerechtenOntDekken[1] = new GerechtenCard(R.drawable.rund, "Lekker rundvlees", 1);
        gerechtenOntDekken[2] = new GerechtenCard(R.drawable.vega, "Lekker vegatarisch", 2);
        gerechtenOntDekken[3] = new GerechtenCard(R.drawable.vis, "Lekker vissie", 3);

        recyclerViewAdapter = new GerechtenCardsAdapter(gerechtenOntDekken);
        recyclerView.setAdapter(recyclerViewAdapter);

//        recyclerViewAdapter = new GerechtenCardsAdapter(grechtenOntdekken);

    }
}
