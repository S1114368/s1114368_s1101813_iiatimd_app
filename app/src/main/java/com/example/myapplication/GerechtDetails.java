package com.example.myapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class GerechtDetails extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gson gson = new Gson();
        GerechtenCard gerechtenCard = gson.fromJson(getIntent().getStringExtra("gerecht"), GerechtenCard.class);
        Log.d("nextscreen", gerechtenCard.getGerechtNaam());
        setContentView(R.layout.gerecht_details);
    }
}
