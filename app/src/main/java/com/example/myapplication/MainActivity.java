package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Gerecht gerecht;
    private AppDatabase database;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView appBarTitle =findViewById(R.id.applicationBarTitle);
        appBarTitle.setText("Inloggen");
        database = AppDatabase.getInstance(getApplicationContext());
        updateDatabase();
    }
    
    public void setRegisterContent(View view){
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
    }
    public void postLoginRequest(View view){
        TextView myAwesomeTextView = (TextView)findViewById(R.id.myAwesomeTextView);
        EditText loginEmail = (EditText)findViewById((R.id.loginEmail));
        EditText loginPassword = (EditText)findViewById((R.id.loginPassword));

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Map<String, String> params = new HashMap();
        params.put("password", String.valueOf(loginPassword.getText()));
        params.put("email", String.valueOf(loginEmail.getText()));
        JSONObject parameters = new JSONObject(params);

        String url = "http://10.0.2.2:9000/api/auth/login";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String stringValue = "";
                try {
                    stringValue = response.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(stringValue.equals("Invalid details")){
                    myAwesomeTextView.setText("verkeerde inlog gegevens");
                }
                if(stringValue.equals("Valid details")){
                    myAwesomeTextView.setText("Ingelogd");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myAwesomeTextView.setText("Voer uw gegevens in");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void updateDatabase(){
        List<Gerecht> gerechten = database.gerechtDao().getAll();
        if(gerechten.size()==0){
            database.gerechtDao().insert(new Gerecht());
        }
    }

}




