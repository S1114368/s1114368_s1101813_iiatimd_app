package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private Button toSecondScreenButton;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toSecondScreenButton = findViewById(R.id.buttonLogin);
        toSecondScreenButton.setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerViewId);
        //recycler view heeft een layout manager nodig zodat alles netjes onder elkaar komt
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();
    }

    @Override
    public void onClick(View view) {
        Intent toSecondScreenIntent = new Intent(this, HomePage.class);
        startActivity(toSecondScreenIntent);
    }
}