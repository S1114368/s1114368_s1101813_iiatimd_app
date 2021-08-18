package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button toSecondScreenButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toSecondScreenButton = findViewById(R.id.buttonLogin);
        toSecondScreenButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent toSecondScreenIntent = new Intent(this, HomePage.class);
        startActivity(toSecondScreenIntent);
    }
}