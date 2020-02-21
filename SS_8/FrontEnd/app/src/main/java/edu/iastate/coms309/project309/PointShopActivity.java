package edu.iastate.coms309.project309;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.iastate.coms309.project309.util.Const;
import edu.iastate.coms309.project309.util.RequestController;
import edu.iastate.coms309.project309.util.ShopAdapter;

public class PointShopActivity extends AppCompatActivity {

    GridView grid;
    RequestQueue rq;
    ArrayList<String[]> data;
    Context context;
    ShopAdapter adapter;

    TextView pts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_shop);

        grid = findViewById(R.id.gridView);
        data = new ArrayList<>();

        pts = findViewById(R.id.textPoints);

        context = getApplicationContext();

        RequestController rc = new RequestController(getApplicationContext());

        /*
        for (int i = 0 ; i < ja.length() ; i++) {
            try {
                JSONObject j = ja.getJSONObject(i);
                String[] s = {j.getString("item"), j.getString("price")};
                data.add(s);
            } catch (JSONException e) {
                Log.e("JSON", "JSON Error: " + e.toString());
                e.printStackTrace();
            }
        }

         */




/*

                }
            }
        };
        rc.requestJsonObject(Request.Method.GET, Const.URL_SHOW_USERS + Const.username, r0);

*/

        Response.Listener<JSONArray> r = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject j = response.getJSONObject(i);

                        String[] s = {j.getString("prizename"), j.getString("cost")};

                        data.add(s);
                    }
                } catch (JSONException e) {
                    Log.e("JSON", "JSON Error: " + e.toString());
                    e.printStackTrace();
                }

                adapter = new ShopAdapter(getApplicationContext(), data);
                grid.setAdapter(adapter);
            }
        };
        rc.requestJsonArray(Request.Method.GET, Const.URL_SHOP, r);

        /*
        try {
            p.setText("Points: " + j.getString("points"));
        } catch (JSONException e) {
            Log.e("JSON", "JSON Error: " + e.toString());
            e.printStackTrace();
        }

         */


        /*
        rq = Volley.newRequestQueue(this);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, Const.URL_SHOP, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Volley", response.toString());
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
        /*
        rq = Volley.newRequestQueue(this);
        JsonObjectRequest jor2 = new JsonObjectRequest(Request.Method.GET, Const.URL_SHOW_USERS + Const.username, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    TextView p = findViewById(R.id.textPoints);
                    p.setText("Points: " + response.getString("points"));
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
        rq.add(jor2);

         */



        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String item = adapter.getObject(position);
                String price = adapter.getPrice(position);

                new AlertDialog.Builder(PointShopActivity.this)
                        .setTitle("Confirm Purchase")
                        .setMessage("Purchase " + item + " for " + price + " points?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //Execute transaction
                                String url = Const.URL_REDEEM + Const.company + "/" + item + "/" + Const.username + "/1";
                                RequestController rc = new RequestController(getApplicationContext());
                                rc.requestJsonObject(Request.Method.POST, url, null);

                                /*
                                JsonObjectRequest jor3 = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Toast.makeText(PointShopActivity.this, "confirmed", Toast.LENGTH_SHORT).show();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError e) {
                                        Toast.makeText(PointShopActivity.this, "bad", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                rq.add(jor3);

                                 */
                            }})
                        .setNegativeButton(android.R.string.no, null).show();


            }
        });
    }
}
