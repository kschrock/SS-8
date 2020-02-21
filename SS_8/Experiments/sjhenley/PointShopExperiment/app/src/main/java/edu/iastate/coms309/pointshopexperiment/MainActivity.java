package edu.iastate.coms309.pointshopexperiment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.le.AdvertiseData;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView grid;
    RequestQueue rq;
    ArrayList<String[]> data;
    Context context;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid = findViewById(R.id.gridView);
        data = new ArrayList<>();

        context = getApplicationContext();

        String url = "";

        /*
        rq = Volley.newRequestQueue(this);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("coupons");

                    for (int i = 0 ; i < jsonArray.length() ; i++) {
                        JSONObject j = jsonArray.getJSONObject(i);
                        String[] s = {j.getString("item"), j.getString("price")};
                        data.add(s);
                    }
                } catch (JSONException e) {
                    Log.e("Volley", "Volley Error");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
            }
        });

        rq.add(jor);
        */

        //hard-coding entities for testing
        for (int i = 0 ; i < 20 ; i++) {
            int n = 10 + i;
            String[] s = {"item " + i, n + " points"};
            data.add(s);
        }


        adapter = new CustomAdapter(getApplicationContext(), data);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getObject(position);
                String price = adapter.getObject(position);

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Confirm Purchase")
                        .setMessage("Purchase " + item + " for " + price + " points?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //Execute transaction
                                Toast.makeText(MainActivity.this, "confirmed", Toast.LENGTH_SHORT).show();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();


            }
        });
    }
}
