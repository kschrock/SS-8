package edu.iastate.coms309.project309;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
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

import edu.iastate.coms309.project309.util.AppController;
import edu.iastate.coms309.project309.util.Const;

public class PaymentDetails extends AppCompatActivity {
    RequestQueue rq;
    JsonObjectRequest jor2;
    TextView txtId,txtAmount,txtStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        txtId=(TextView)findViewById(R.id.txtId);
        txtAmount=(TextView)findViewById(R.id.txtAmount);
        txtStatus=(TextView)findViewById(R.id.txtStatus);
        rq = Volley.newRequestQueue(this);
        Intent intent=getIntent();
        try {
            JSONObject jsonObject=new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        String url=Const.URL_SHOW_USERS+"/"+Const.username+"/"+Const.password+"/";
        Log.d("confirmation",txtStatus.getText().toString());


        if(txtStatus.getText().toString().equals("approved")){
            url=Const.URL_SHOW_USERS+"/"+Const.username+"/"+Const.password+"/"+Const.companyForDiscount+"/paid";
            //url=Const.URL_SHOW_USERS+"/i/i/"+Const.companyForDiscount+"/paid";  //need to connect to rest of front end

        }
        jor2 = new JsonObjectRequest(Request.Method.GET, url, null , new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {


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
    private void showDetails(JSONObject response, String paymentAmount){
        try {
            txtId.setText(response.getString("id"));
            txtStatus.setText((response.getString("state")));
            txtAmount.setText("$"+paymentAmount);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
