package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class AddReceiptActivity extends AppCompatActivity {
    private int user_ID;
    private Bundle bundleForHomepage;
    private Intent intentHomepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receipt);

        String[] arraySpinner = new String[] {
                "vega", "vis", "kip", "varken", "rund", "oven"
        };
        Spinner s = (Spinner) findViewById(R.id.categorie);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        TextView appBarTitle =findViewById(R.id.applicationBarTitle);
        appBarTitle.setText("Gerecht toevoegen");

        Bundle myBundle = getIntent().getExtras();
        user_ID = myBundle.getInt("user_ID");
        bundleForHomepage = new Bundle();
        intentHomepage = new Intent(this, HomePage.class);
    }

    public void setLoginContent(View view){
        finish();
    }

    public void postCreateRequest(View view) throws JSONException {
        EditText name = (EditText)findViewById(R.id.dishName);
        EditText amountOfPeople = (EditText)findViewById(R.id.amountOfPeople);
        EditText ingredient1 = (EditText)findViewById(R.id.ingredient1);
        EditText ingredient2 = (EditText)findViewById(R.id.ingredient2);
        EditText ingredient3 = (EditText)findViewById(R.id.ingredient3);
        EditText ingredient4 = (EditText)findViewById(R.id.ingredient4);
        EditText ingredient5 = (EditText)findViewById(R.id.ingredient5);

        EditText amount1 = (EditText)findViewById(R.id.amount1);
        EditText amount2 = (EditText)findViewById(R.id.amount2);
        EditText amount3 = (EditText)findViewById(R.id.amount3);
        EditText amount4 = (EditText)findViewById(R.id.amount3);
        EditText amount5 = (EditText)findViewById(R.id.amount3);

        EditText step1 = (EditText)findViewById(R.id.step1);
        EditText step2 = (EditText)findViewById(R.id.step2);
        EditText step3 = (EditText)findViewById(R.id.step3);
        Spinner spinner = (Spinner)findViewById(R.id.categorie);
        String categorie = spinner.getSelectedItem().toString();

        TextView txt = (TextView)findViewById(R.id.textView9) ;

        JSONObject ingr1 = new JSONObject();
        JSONObject ingr2 = new JSONObject();
        JSONObject ingr3 = new JSONObject();
        JSONObject ingr4 = new JSONObject();
        JSONObject ingr5 = new JSONObject();

        JSONObject stap1 = new JSONObject();
        JSONObject stap2 = new JSONObject();
        JSONObject stap3 = new JSONObject();

        try{
            ingr1.put("ingredient", String.valueOf(ingredient1.getText()));
            ingr1.put("aantal", String.valueOf(amount1.getText()));
            ingr2.put("ingredient", String.valueOf(ingredient2.getText()));
            ingr2.put("aantal", String.valueOf(amount2.getText()));
            ingr3.put("ingredient", String.valueOf(ingredient3.getText()));
            ingr3.put("aantal", String.valueOf(amount3.getText()));
            ingr4.put("ingredient", String.valueOf(ingredient4.getText()));
            ingr4.put("aantal", String.valueOf(amount4.getText()));
            ingr5.put("ingredient", String.valueOf(ingredient5.getText()));
            ingr5.put("aantal", String.valueOf(amount5.getText()));
            stap1.put("beschrijving", String.valueOf(step1.getText()));
            stap1.put("stap", 1);
            stap2.put("beschrijving", String.valueOf(step2.getText()));
            stap2.put("stap", 2);
            stap3.put("beschrijving", String.valueOf(step3.getText()));
            stap3.put("stap", 3);
        }catch(JSONException e){
            e.printStackTrace();
        }

        JSONArray ingredienten = new JSONArray();
        JSONArray steps = new JSONArray();
        if(!ingr1.toString().equals("{\"ingredient\":\"\",\"aantal\":\"\"}")){
            ingredienten.put(ingr1);
        }
        if(!ingr2.toString().equals("{\"ingredient\":\"\",\"aantal\":\"\"}")){
            ingredienten.put(ingr2);
        }
        if(!ingr3.toString().equals("{\"ingredient\":\"\",\"aantal\":\"\"}")){
            ingredienten.put(ingr3);
        }
        if(!ingr4.toString().equals("{\"ingredient\":\"\",\"aantal\":\"\"}")){
            ingredienten.put(ingr4);
        }
        if(!ingr5.toString().equals("{\"ingredient\":\"\",\"aantal\":\"\"}")){
            ingredienten.put(ingr5);
        }
        if(!stap1.toString().equals("{\"beschrijving\":\"\",\"stap\":1}")){
            steps.put(stap1);
        }
        if(!stap2.toString().equals("{\"beschrijving\":\"\",\"stap\":2}")){
            steps.put(stap2);
        }
        if(!stap3.toString().equals("{\"beschrijving\":\"\",\"stap\":3}")){
            steps.put(stap3);
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Bundle myBundle = getIntent().getExtras();


        JSONObject request = new JSONObject();
        request.put("naam", String.valueOf(name.getText()));
        request.put("aantal_personen", String.valueOf(amountOfPeople.getText()));
        request.put("categorie", categorie);
        request.put("instructies", steps);
        request.put("ingredienten", ingredienten);
        request.put("user_name", String.valueOf(myBundle.getString("user_naam")));

        String url = "http://10.0.2.2:9000/api/gerecht/create";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                txt.setText("Gerecht toegevoegd!");
                bundleForHomepage.putInt("user_ID", user_ID);
                intentHomepage.putExtras(bundleForHomepage);
                startActivity(intentHomepage);
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txt.setText("Er is wat fouts gegaan, kijk alle velden goed na.");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type","application/json");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    private boolean isEmpty(EditText txt){
        if(txt.getText().toString().trim().length() > 0){
            return false;
        }
        return true;
    }
}