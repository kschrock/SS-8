package edu.iastate.coms309.project309;

import  androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.iastate.coms309.project309.util.AppController;
import edu.iastate.coms309.project309.util.Const;
import edu.iastate.coms309.project309.util.RequestController;


public class RegisterActivity extends AppCompatActivity {

    RequestQueue rq;
    JsonObjectRequest jor;
    EditText firstname, lastname, address, phone ,username, password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rq = Volley.newRequestQueue(this);

        firstname = findViewById(R.id.textInputFirstname);
        lastname = findViewById(R.id.textInputLastname);
        address = findViewById(R.id.textInputAddress);
        phone = findViewById(R.id.textInputPhone);
        username = findViewById(R.id.textInputUsername);
        password = findViewById(R.id.textInputPassword);

        findViewById(R.id.buttonCreateAcct).setOnClickListener(new View.OnClickListener() {
            boolean success = false;
            @Override
            public void onClick(View view) {
                JSONObject js = new JSONObject();
                try {
                    js.put("id", "");
                    js.put("firstName", firstname.getText().toString());
                    js.put("lastName", lastname.getText().toString());
                    js.put("address", address.getText().toString());
                    js.put("telephone", phone.getText().toString());
                    js.put("username", username.getText().toString());
                    js.put("password", password.getText().toString());
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestController rc = new RequestController(getApplicationContext());
                Response.Listener<JSONObject> r = new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String a = "";
                        try {
                            a = response.getString("verify");
                        } catch (JSONException e) {
                            Log.e("JSON", "JSON Error: " + e.toString());
                            e.printStackTrace();
                        }

                        if (!a.equals("Added")) {
                            Toast.makeText(getApplicationContext(), "Error creating account", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                rc.requestJsonObject(Request.Method.POST, Const.URL_REGISTER, js, r);
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));


                /*
                jor = new JsonObjectRequest(Request.Method.POST, Const.URL_REGISTER, js, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(AppController.TAG, response.toString());
                        try {
                            if (response.getString("verify").equals("Added"))
                                success = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e(AppController.TAG, "Error: " + error.getMessage());
                        Log.e(AppController.TAG, "Error: " + error.getMessage());
                    }
                });

                rq.add(jor);

                /**
                Toast t;
                if(success) {
                    t = Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT);
                } else{
                    t = Toast.makeText(getApplicationContext(), "Error creating account, please try again later",  Toast.LENGTH_SHORT );
                }
                t.show();
                 */


            }
        });


    }
}