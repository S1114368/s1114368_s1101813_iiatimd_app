package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView appBarTitle =findViewById(R.id.applicationBarTitle);
        appBarTitle.setText("Registreren");
    }


    public void setLoginContent(View view){
        finish();
    }

    public void postRegisterRequest(View view){
        TextView myAwesomeTextView = (TextView)findViewById(R.id.myAwesomeTextView);
        EditText registerName = (EditText)findViewById((R.id.registerName));
        EditText registerEmail = (EditText)findViewById((R.id.registerEmail));
        EditText registerPassword = (EditText)findViewById((R.id.registerPassword));
        EditText registerConfirmPassword = (EditText)findViewById((R.id.registerConfirmPassword));

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Map<String, String> params = new HashMap();
        params.put("name", String.valueOf(registerName.getText()));
        params.put("email", String.valueOf(registerEmail.getText()));
        params.put("password", String.valueOf(registerPassword.getText()));
        params.put("password_confirmation", String.valueOf(registerConfirmPassword.getText()));

        JSONObject parameters = new JSONObject(params);

        String url = "http://10.0.2.2:9000/api/auth/registration";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String stringValue = "";
                String errors= "";
                //myAwesomeTextView.setText(response.toString());
                try {
                    stringValue = response.getString("message");
                    errors = response.getString("error");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(stringValue.equals("Valid details")){
                   myAwesomeTextView.setText("Geregistreerd!");
                   finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myAwesomeTextView.setText("Verkeerde gegevens");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}