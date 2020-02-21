package edu.iastate.coms309.project309;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;

import java.net.URI;
import java.net.URISyntaxException;


import edu.iastate.coms309.project309.util.Const;
import edu.iastate.coms309.project309.util.EventAdapter;
import edu.iastate.coms309.project309.util.RequestController;

public class EventListActivity extends AppCompatActivity {


    ListView list;
    RequestQueue rq;

    private WebSocketClient wc;
    EventAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        list = findViewById(R.id.listView);



        /*


        rq = Volley.newRequestQueue(this);
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, Const.URL_EVENT_LIST, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    ja = response.getJSONArray("events");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.d("Volley", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject j = response.getJSONObject(i);

                        Log.e("Volley", j.toString());

                        adapter.add(j.getString("eventname"), j.getString("company"));



                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(jar);

        */

        Response.Listener<JSONArray> r = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0 ; i < response.length() ; i++) {
                    ArrayList<String> companies = new ArrayList<>();
                    ArrayList<String> events = new ArrayList<>();
                    try {
                        JSONObject j = response.getJSONObject(i);
                        events.add(j.getString("eventname"));
                        companies.add(j.getString("company"));
                    } catch (JSONException e) {
                        Log.e("JSON", "JSON Error: " + e.toString());
                        e.printStackTrace();
                    }
                    initializeList(events, companies);
                }
            }
        };
        RequestController rc = new RequestController(getApplicationContext());
        rc.requestJsonArray(Request.Method.GET, Const.URL_EVENT_LIST, r);

        /*
        for (int i = 0 ; i < ja.length(); i++) {
            try {
                JSONObject j = ja.getJSONObject(i);
                adapter.add(j.getString("eventName"), j.getString("username"));
            } catch (JSONException e) {
                Log.e("JSON", "JSON Error: " + e.toString());
                e.printStackTrace();
            }
        }

         */


        String w = Const.WS_EVENT_UPDATE + Const.username;
        Draft[] drafts = {new Draft_6455()};

        try {
            Log.d("Socket", "Trying socket ");
            wc = new WebSocketClient(new URI(w), drafts[0]) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Log.d("WS", "Connected to " + this.getURI().toString());
                }

                @Override
                public void onMessage(String s) {
                    Log.d("WS", "Recieved message:" + s);

                    if(!s.contains("has Joined the Chat") && !s.contains("Disconnected")) {
                        try {
                            JSONObject j = new JSONObject(s);

                            add(adapter, j.getString("eventname"), "");
                            //adapter.add(j.getString("event"),j.getString("company"));
                        }catch (JSONException e) {
                            Log.e("JSON", "JSON Error: " + e.toString());
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    Log.d("WS", "Closed connection to " + this.getURI().toString());
                }

                @Override
                public void onError(Exception e) {
                    Log.e("WS", "Websocket Error: " + e.toString());
                    e.printStackTrace();
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        wc.connect();

    }

    private void add(EventAdapter a, String e, String c) {
        final EventAdapter ad = a;
        final String ev = e;
        final String co = c;
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                ad.add(ev,co);
            }
        });
    }

    private void initializeList(ArrayList<String> e, ArrayList<String> c)
    {
        adapter = new EventAdapter(getApplicationContext(), e, c);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Const.event = adapter.getEvent(position);
                Const.company = adapter.getCompany(position);
                startActivity(new Intent(EventListActivity.this, EventViewActivity.class));
            }
        });
    }

}
