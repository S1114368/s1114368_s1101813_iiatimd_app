package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //EditText username = (EditText)findViewById(R.id.loginName);
    //EditText password = (EditText)findViewById(R.id.loginPassword);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void postLoginRequest(View view){
        TextView myAwesomeTextView = (TextView)findViewById(R.id.myAwesomeTextView);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:9000/api/auth/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                myAwesomeTextView.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myAwesomeTextView.setText("error"+error);
            }
        });

        requestQueue.add(stringRequest);
    }

}




