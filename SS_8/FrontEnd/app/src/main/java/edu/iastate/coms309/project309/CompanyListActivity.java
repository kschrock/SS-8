package edu.iastate.coms309.project309;

import  androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.iastate.coms309.project309.util.Const;
import edu.iastate.coms309.project309.util.EventAdapter;
import edu.iastate.coms309.project309.util.RequestController;

public class CompanyListActivity extends AppCompatActivity {


    private ListView list;

    private RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);

        list = findViewById(R.id.listView2);

        rq = Volley.newRequestQueue(this);

        /*

        JsonArrayRequest jor = new JsonArrayRequest(Request.Method.GET, Const.URL_COMPANIES, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                Log.d("VOLLEY", response.toString());
                for (int i = 0 ; i < response.length() ; i++) {

                    try {
                        JSONObject j = response.getJSONObject(i);
                        companies.add(j.getString("companyName"));
                        Log.d("VOLLEY", "added " + j.getString("companyName"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

        Response.Listener<JSONArray> r = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                ArrayList<String> companies = new ArrayList<>();
                ArrayList<String> usernames = new ArrayList<>();
                for (int i = 0 ; i < response.length() ; i++) {

                    try {
                        JSONObject j = response.getJSONObject(i);
                        companies.add(j.getString("companyName"));
                        usernames.add(j.getString("username"));
                    } catch (JSONException e) {
                        Log.e("JSON", "JSON Error: " + e.toString());
                        e.printStackTrace();
                    }



                    initializeList(companies, usernames);
                    Log.e("list response",list.toString());

                }
                initializeList(companies, usernames);
            }
        };
        RequestController rc = new RequestController(getApplicationContext());
        rc.requestJsonArray(Request.Method.GET, Const.URL_COMPANIES, r);



    }

    private void initializeList(ArrayList<String> items, ArrayList<String> usernames) {
        EventAdapter adapter = new EventAdapter(getApplicationContext(), items, usernames);
        list.setAdapter(adapter);
        final ArrayList<String> arrayList = usernames;

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*
                JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, Const.URL_SUBSCRIBE + Const.username + "/" + companies.get(position) , null, null, null);
                rq.add(jor);

                 */
                RequestController rc = new RequestController(getApplicationContext());
                rc.requestJsonObject(Request.Method.POST, Const.URL_SUBSCRIBE + Const.username + "/" + arrayList.get(position), null);
            }
        });
    }
}
