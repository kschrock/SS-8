package edu.iastate.coms309.project309;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.iastate.coms309.project309.util.AppController;
import edu.iastate.coms309.project309.util.Const;
import edu.iastate.coms309.project309.util.RequestController;

import static edu.iastate.coms309.project309.util.Const.URL_COMPANY_LOGIN;

public class CompanyLoginActivity extends AppCompatActivity {

    EditText user, pass;
    Button reg, login, normal;

    RequestQueue rq;
    JsonObjectRequest jor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_login);

        user = findViewById(R.id.loginInputUsernameCompany);
        pass = findViewById(R.id.loginInputPasswordCompany);
        reg = findViewById(R.id.buttonMakeAcctCompany);
        login = findViewById(R.id.buttonLoginCompany);

        normal = findViewById(R.id.buttonGotoUser);

        normal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CompanyLoginActivity.this, LoginActivity.class));
            }
        });

        rq = Volley.newRequestQueue(this);


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CompanyLoginActivity.this, CompanyRegisterActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getText().toString().equals("a") && pass.getText().toString().equals("a")) {
                    //Bypass login for testing
                    Toast t = Toast.makeText(getApplicationContext(), "Bypassing Login!", Toast.LENGTH_SHORT);
                    t.show();
                    startActivity(new Intent(CompanyLoginActivity.this, CompanyHomeActivity.class));
                } else {
                    Response.Listener<JSONObject> r = new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String v = "";
                            try {
                                v = response.getString("verify");
                            } catch (JSONException e) {
                                Log.e("JSON", "JSON Error: " + e.toString());
                                e.printStackTrace();
                            }

                            if (v.equals("true")) {
                                Const.username = user.getText().toString();
                                Const.password = pass.getText().toString();
                                startActivity(new Intent(CompanyLoginActivity.this, CompanyHomeActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    RequestController rc = new RequestController(getApplicationContext());
                    rc.requestJsonObject(Request.Method.GET, URL_COMPANY_LOGIN + user.getText().toString() + "/" + pass.getText().toString(), r);
                }

            }
        });

        /*


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getText().toString().equals("a") && pass.getText().toString().equals("a")) {
                    //Bypass login for testing
                    Toast t = Toast.makeText(getApplicationContext(), "Bypassing Login!", Toast.LENGTH_SHORT);
                    t.show();
                    startActivity(new Intent(CompanyLoginActivity.this, CompanyHomeActivity.class));
                } else {

                    String url = Const.URL_COMPANY_LOGIN  + user.getText().toString() + "/" + pass.getText().toString();

                    jor2 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        String verify = "false";

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(AppController.TAG, "Response:" + response.toString());
                            try {
                                verify = response.getString("verify");
                            } catch (JSONException e) {
                                Toast t = Toast.makeText(getApplicationContext(), "JSON Exception", Toast.LENGTH_SHORT);
                                t.show();
                                e.printStackTrace();
                            }

                            if (verify.equals("true")) {
                                Const.username = user.getText().toString();
                                Const.password = pass.getText().toString();
                                startActivity(new Intent(CompanyLoginActivity.this, CompanyHomeActivity.class));
                            } else {
                                Toast t = Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.e(AppController.TAG, "Error: " + error.getMessage());
                            Log.e(AppController.TAG, "Error: " + error.getMessage());
                            Toast t = Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_SHORT);
                            t.show();
                        }
                    });

                    rq.add(jor2);
                }

            }
        });


         */


    }

}
