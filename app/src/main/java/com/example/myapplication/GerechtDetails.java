package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.List;

public class GerechtDetails extends AppCompatActivity {
    private TextView gerechtTitle;
    private TextView instructiesText;
    private TextView ingredientenText;
    private ImageView imageViewDetails;
    private List<String> ingredienten;
    private List<String> instructies;
    private StringBuilder ingredientBuild;
    private StringBuilder instructiesBuild;
    private Bundle bundleForHomepage;
    private int user_ID;
    private String user_naam;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GerechtenCard gerechtenCard = getIntent().getParcelableExtra("geselecteerde_gerecht");

        Bundle myBundle = getIntent().getExtras();
        user_ID = myBundle.getInt("user_ID");
        user_naam = myBundle.getString("user_naam");
        bundleForHomepage = new Bundle();

        setContentView(R.layout.gerecht_details);
        TextView appBarTitlex = findViewById(R.id.applicationBarTitle);
        String titletekst = gerechtenCard.getGerechtNaam();
        appBarTitlex.setText(gerechtenCard.getGerechtNaam());

        imageViewDetails = findViewById(R.id.imageViewDetails);
        imageViewDetails.setImageResource(gerechtenCard.getImg());

        gerechtTitle = findViewById(R.id.gerechtTitleDetails);
        gerechtTitle.setText(gerechtenCard.getGerechtNaam());

        //refactor code hierbeneden later, DRY
        ingredientenText = findViewById(R.id.ingredienten);
        ingredienten = gerechtenCard.getIngredienten();
        ingredientBuild = new StringBuilder();
        for(int i = 0; i < ingredienten.size(); i++){
            ingredientBuild.append(i + ". " + ingredienten.get(i));
            ingredientBuild.append("\n");
        }
        ingredientenText.setText(String.valueOf(ingredientBuild));

        instructiesText = findViewById(R.id.instructies);
        instructies = gerechtenCard.getInstructies();
        instructiesBuild = new StringBuilder();
        for(int i = 0; i < instructies.size(); i++){
            instructiesBuild.append(i + ". " + instructies.get(i));
            instructiesBuild.append("\n");
        }
        instructiesText.setText(String.valueOf(instructiesBuild));

    }
    public void terugNaarHome(View v){
        Intent intentForHomePage = new Intent(this, HomePage.class);
        bundleForHomepage.putString("user_naam", user_naam);
        bundleForHomepage.putInt("user_ID", user_ID);
        intentForHomePage.putExtras(bundleForHomepage);
        startActivity(intentForHomePage);
    }
}
