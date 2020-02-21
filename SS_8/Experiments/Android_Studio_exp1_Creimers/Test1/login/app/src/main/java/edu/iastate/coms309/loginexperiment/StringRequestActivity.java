package edu.iastate.coms309.loginexperiment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import edu.iastate.coms309.loginexperiment.net_utils.Const;

public class StringRequestActivity extends AppCompatActivity {

    Button b1;
    TextView text;
    RequestQueue q;
    StringRequest sr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string_request);

        b1 = findViewById(R.id.btnStringRequest2);
        text = findViewById(R.id.StringResponse);
        q = Volley.newRequestQueue(this);

        sr = new StringRequest(Request.Method.GET, Const.URL_STRING_REQ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        text.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                text.setText("That didn't work!");
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q.add(sr);
            }
        });


    }
}
