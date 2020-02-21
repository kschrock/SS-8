package edu.iastate.coms309.project309;

import  androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.coms309.project309.util.Const;
import edu.iastate.coms309.project309.util.RequestController;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;


public class LoginActivity extends AppCompatActivity {

    EditText user, pass;
    Button reg, login, comp;

    private WebSocketClient cc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.loginInputUsername);
        pass = findViewById(R.id.loginInputPassword);
        reg = findViewById(R.id.buttonMakeAcct);
        login = findViewById(R.id.buttonLogin);

        comp = findViewById(R.id.buttonGotoCompany);

        comp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CompanyLoginActivity.class));
            }
        });


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().toString().equals("a") && pass.getText().toString().equals("a")) {
                    //Bypass login for testing
                    Toast t = Toast.makeText(getApplicationContext(), "Bypassing Login!", Toast.LENGTH_SHORT);
                    t.show();
                    startActivity(new Intent(LoginActivity.this, DevHomeActivity.class));
                } else {

                    String url = Const.URL_LOGIN + "/" + user.getText().toString() + "/" + pass.getText().toString();
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
                                findOwnerID(Const.username);
                                startActivity(new Intent(LoginActivity.this, UserHomeActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                    };

                    RequestController rc = new RequestController(getApplicationContext());
                    rc.requestJsonObject(Request.Method.GET, url, r);
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
                    startActivity(new Intent(LoginActivity.this, DevHomeActivity.class));
                } else {

                    String url = Const.URL_LOGIN + "/" + user.getText().toString() + "/" + pass.getText().toString();

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
                                //connectWS(user.getText().toString());
                                startActivity(new Intent(LoginActivity.this, UserHomeActivity.class));
                            } else {
                                Toast t = Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.e(AppController.TAG, "Error: " + error.getMessage());
                            Log.e(AppController.TAG, "Error: "n n + error.getMessage());
                            Toast t = Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_SHORT);
                        }
                    });
                    rq.add(jor2);
                }
            }
        });

        */


    }

    private void connectWS(String username) {
        Draft[] drafts = {new Draft_6455()};


        String w = "ws://coms-309-ss-8.misc.iastate.edu:8080/notify/" + username;

        try {

            cc = new WebSocketClient(new URI(w),(Draft) drafts[0]) {

                @Override
                public void onMessage(String message) {
                    Log.d("", "run() returned: " + message);

                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                }

                @Override
                public void onError(Exception e)
                {
                    Log.d("Exception:", e.toString());
                }



            };
        }
        catch (URISyntaxException e) {
            Log.d("Exception:", e.getMessage().toString());
            e.printStackTrace();
        }
        cc.connect();
    }

    public void findOwnerID(String username)
    {
        Response.Listener<JSONObject> r = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Const.userID = response.getInt("id");
                } catch (JSONException e) {
                    Log.e("JSON", "JSON Error: " + e.toString());
                    e.printStackTrace();
                }
            }
        };

        RequestController rc = new RequestController(getApplicationContext());
        rc.requestJsonObject(Request.Method.GET, Const.URL_SHOW_USERS + Const.username, r);

    }
}
