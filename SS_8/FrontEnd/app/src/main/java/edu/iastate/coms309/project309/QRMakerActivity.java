package edu.iastate.coms309.project309;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import edu.iastate.coms309.project309.util.RequestController;

import static edu.iastate.coms309.project309.util.Const.DOMAIN;

public class QRMakerActivity extends AppCompatActivity {
    EditText points,quantity,expireTime,expireDate;
    Button makeCode;
    RequestQueue rq;
    JsonObjectRequest jor;
    JSONObject object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_codes);


        points=findViewById(R.id.points);
        quantity=findViewById(R.id.quantity);
        expireDate=findViewById(R.id.expireDate);
        expireTime=findViewById(R.id.expireTime);
        makeCode=findViewById(R.id.MakeCode);

        makeCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object=new JSONObject();
                try {
                    object.put("company",Const.username);
                    object.put("points", points.getText().toString());
                    object.put("quantity", quantity.getText().toString());
                    object.put("expireDate", expireDate.getText().toString());
                    object.put("expireTime", expireTime.getText().toString());
                }
                catch (JSONException e){
                    e.printStackTrace();
                }

                RequestController rc = new RequestController(getApplicationContext());
                Response.Listener<JSONObject> r = new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String r="";
                        try {
                            r=response.getString("");
                          r=response.toString();
                          Log.e("response",r);
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                };
                rc.requestJsonObject(Request.Method.POST, "http://"+DOMAIN+"product/create", object, r);
            }
        });
    }
}
