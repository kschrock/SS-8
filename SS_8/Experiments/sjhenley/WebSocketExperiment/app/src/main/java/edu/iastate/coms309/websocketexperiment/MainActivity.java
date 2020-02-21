package edu.iastate.coms309.websocketexperiment;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import android.media.session.PlaybackState;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toolbar;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity {

    ListView list;
    RequestQueue rq;
    ArrayList<String> events, companies;

    private WebSocketClient wc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        list = findViewById(R.id.listView);

        events = new ArrayList<>();
        companies = new ArrayList<>();

        final CustomAdapter adapter = new CustomAdapter(getApplicationContext(), events, companies);
        list.setAdapter(adapter);

        String url = "https://api.myjson.com/bins/1h7m20";


        rq = Volley.newRequestQueue(this);
        JsonObjectRequest jar = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray ja = new JSONArray();
                try {
                    ja = response.getJSONArray("events");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < ja.length(); i++) {
                    try {

                        JSONObject j = ja.getJSONObject(i);

                        adapter.add(j.getString("event"), j.getString("company"));

                        Log.d("VOLLEY", j.getString("event") + "," + j.getString("company"));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        rq.add(jar);


        String w = "ws://demos.kaazing.com/echo";
        Draft[] drafts = {new Draft_6455()};

        try {
            Log.d("Socket", "Trying socket");
            wc = new WebSocketClient(new URI(w), drafts[0]) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {

                }

                @Override
                public void onMessage(String s) {
                    Log.d("", "run() returned: " + s);

                    int n = s.indexOf('$');
                    if (n < 0) {
                        Log.e("WebSocket Client", "Bad format from server");
                    }

                    //adapter.add(s.substring(0, n), s.substring(n + 1));
                    add(adapter, s.substring(0, n), s.substring(n + 1) );

                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    Log.d("CLOSE", "onClose() returned: " + s);
                }

                @Override
                public void onError(Exception e) {
                    Log.e("Exception:", e.toString());
                    e.printStackTrace();
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        wc.connect();

        findViewById(R.id.buttonAddEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    wc.send("Fun and Games$Apple");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

    }

    private void add(CustomAdapter a, String e, String c) {
        final CustomAdapter ad = a;
        final String ev = e;
        final String co = c;
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                ad.add(ev,co);
            }
        });
    }

}
