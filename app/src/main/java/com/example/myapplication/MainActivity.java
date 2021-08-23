package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private AppDatabase database;
    private Button toSecondScreenButton;
    private Intent intent;
    private Bundle bundleForHomepage;
    private String user_naam;
    private String user;
    private int user_ID;
    private JSONObject userObject;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView appBarTitle =findViewById(R.id.applicationBarTitle);
        appBarTitle.setText("Inloggen");
        bundleForHomepage = new Bundle();
        intent = new Intent(this, HomePage.class);
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
                    try {
                        user = response.getString("user");
                        userObject = new JSONObject(user);
                        user_ID = (int) userObject.get("id");
                        user_naam = (String) userObject.get("name");
                        bundleForHomepage.putString("user_naam", user_naam);
                        bundleForHomepage.putInt("user_ID", user_ID);
                        intent.putExtras(bundleForHomepage);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myAwesomeTextView.setText(String.valueOf(error));
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}


 

