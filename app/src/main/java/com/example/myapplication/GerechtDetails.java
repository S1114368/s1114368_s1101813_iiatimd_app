package com.example.myapplication;

import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.List;

public class GerechtDetails extends AppCompatActivity {
    private TextView gerechtTitle;
    private TextView instructiesText;
    private TextView ingredientenText;
    private List<String> ingredienten;
    private List<String> instructies;
    private StringBuilder ingredientBuild;
    private StringBuilder instructiesBuild;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GerechtenCard gerechtenCard = getIntent().getParcelableExtra("geselecteerde_gerecht");

        setContentView(R.layout.gerecht_details);
        TextView appBarTitlex = findViewById(R.id.applicationBarTitle);
        String titletekst = gerechtenCard.getGerechtNaam();
        appBarTitlex.setText(gerechtenCard.getGerechtNaam());

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
}
