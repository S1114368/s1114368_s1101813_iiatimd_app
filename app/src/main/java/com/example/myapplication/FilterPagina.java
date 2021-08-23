package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FilterPagina extends AppCompatActivity {
    private int user_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_page);
        TextView appBarTitle =findViewById(R.id.applicationBarTitle);
        appBarTitle.setText("Filter Page");

        Bundle myBundle = getIntent().getExtras();
        user_ID = myBundle.getInt("user_ID");
    }

}
