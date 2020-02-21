package edu.iastate.coms309.project309;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.iastate.coms309.project309.util.AppController;
import edu.iastate.coms309.project309.util.Const;

public class QrScanner extends AppCompatActivity {
    private Button scanQr;
    private TextView Resulttext;

    private IntentIntegrator qrScan;
    RequestQueue rq;
    JsonObjectRequest jor, jor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_scanner);

        rq = Volley.newRequestQueue(this);

        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result= IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
            if(result!=null){
                if (result.getContents() == null) {

                } else {

                    Log.e(AppController.TAG, result.getContents());
                    String url = Const.URL_QR + "/" + result.getContents();

                    jor = new JsonObjectRequest(Request.Method.GET, "http://coms-309-ss-8.misc.iastate.edu:8080/" + result.getContents(), null, new Response.Listener<JSONObject>() {  //need qrURL
                        String company, points;

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(AppController.TAG, response.toString());


                            try {
                                points = response.getString("points");

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast t = Toast.makeText(getApplicationContext(), "JSON Error", Toast.LENGTH_SHORT);
                                t.show();
                            }
                            /*

                            String url2 = Const.URL_ADD_POINTS + "/" + points.substring(0, points.length() - 2) + "/" + Const.company + "/" + Const.username;

                            jor2 = new JsonObjectRequest(Request.Method.POST, url2, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.e(AppController.TAG, points + "Points added");

                                    Toast t = Toast.makeText(getApplicationContext(), points + "points added!", Toast.LENGTH_SHORT);
                                    t.show();

                                    startActivity(new Intent(QrScanner.this, DevHomeActivity.class));
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast t = Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_SHORT);
                                    t.show();
                                }
                            });

                            rq.add(jor2);

                             */


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(AppController.TAG, "Error: " + error.getMessage());
                            Toast t = Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_SHORT);
                            t.show();
                        }
                    });

                    try {
                        rq.add(jor);
                    } catch (NullPointerException e) {
                        Toast t = Toast.makeText(getApplicationContext(), "RQ Error", Toast.LENGTH_SHORT);
                        t.show();
                    }
                }
            } else{
                super.onActivityResult(requestCode,resultCode,data);
            }



    }

}
