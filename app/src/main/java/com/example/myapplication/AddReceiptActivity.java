package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receipt);

        String[] arraySpinner = new String[] {
                "Vegatarisch", "Vis", "Kip", "Varken", "Rund", "Oven", "Chinees"
        };
        Spinner s = (Spinner) findViewById(R.id.categorie);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
    }

    public void setLoginContent(View view){
        finish();
    }

    public void postCreateRequest(View view){
        EditText name = (EditText)findViewById(R.id.dishName);
        EditText amountOfPeople = (EditText)findViewById(R.id.amountOfPeople);
        EditText ingredient1 = (EditText)findViewById(R.id.ingredient1);
        EditText ingredient2 = (EditText)findViewById(R.id.ingredient2);
        EditText ingredient3 = (EditText)findViewById(R.id.ingredient3);
        EditText amount1 = (EditText)findViewById(R.id.amount1);
        EditText amount2 = (EditText)findViewById(R.id.amount2);
        EditText amount3 = (EditText)findViewById(R.id.amount3);
        EditText step1 = (EditText)findViewById(R.id.step1);
        EditText step2 = (EditText)findViewById(R.id.step2);
        Spinner spinner = (Spinner)findViewById(R.id.categorie);
        String categorie = spinner.getSelectedItem().toString();

        TextView txt = (TextView)findViewById(R.id.textView2) ;

        JSONObject ingr1 = new JSONObject();
        JSONObject ingr2 = new JSONObject();
        JSONObject ingr3 = new JSONObject();
        JSONObject stap1 = new JSONObject();
        JSONObject stap2 = new JSONObject();

        try{
            ingr1.put("ingredient", String.valueOf(ingredient1.getText()));
            ingr1.put("aantal", String.valueOf(amount1.getText()));
            ingr2.put("ingredient", String.valueOf(ingredient2.getText()));
            ingr2.put("aantal", String.valueOf(amount2.getText()));
            ingr3.put("ingredient", String.valueOf(ingredient3.getText()));
            ingr3.put("aantal", String.valueOf(amount3.getText()));
            stap1.put("beschrijving", String.valueOf(step1.getText()));
            stap1.put("stap", 1);
            stap2.put("beschrijving", String.valueOf(step2.getText()));
            stap2.put("stap", 2);
        }catch(JSONException e){
            e.printStackTrace();
        }

        JSONArray ingredienten = new JSONArray();
        JSONArray steps = new JSONArray();
        ingredienten.put(ingr1);
        ingredienten.put(ingr2);
        ingredienten.put(ingr3);
        steps.put(stap1);
        steps.put(stap2);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Map<String, String> params = new HashMap();
        params.put("naam", String.valueOf(name.getText()));
        params.put("aantal_personen", String.valueOf(amountOfPeople.getText()));
        params.put("categorie", categorie);
        params.put("instructies", String.valueOf(steps));
        params.put("ingredienten", String.valueOf(ingredienten));



        JSONObject parameters = new JSONObject(params);
        txt.setText(String.valueOf(parameters));

        String url = "http://10.0.2.2:9000/api/gerecht/create";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                txt.setText("aaaaaaa");
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //txt.setText(String.valueOf(error));
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



}