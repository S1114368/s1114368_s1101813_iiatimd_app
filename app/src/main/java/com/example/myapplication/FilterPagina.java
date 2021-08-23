package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FilterPagina extends AppCompatActivity {
    private int user_ID;
    private String user_naam;
    private Bundle bundleForHomepage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_page);
        TextView appBarTitle =findViewById(R.id.applicationBarTitle);
        appBarTitle.setText("Filter Page");

        Bundle myBundle = getIntent().getExtras();
        user_ID = myBundle.getInt("user_ID");
        user_naam = myBundle.getString("user_naam");
        bundleForHomepage = new Bundle();
    }

    public void filterGerecht(View v){
        Intent intentHomepage = new Intent(this, HomePage.class);
        bundleForHomepage.putInt("user_ID", user_ID);
        bundleForHomepage.putString("user_naam", user_naam);
        String categorie = (String) v.getTag();
        bundleForHomepage.putString("categorie", categorie);
        intentHomepage.putExtras(bundleForHomepage);
        startActivity(intentHomepage);
    }

}
