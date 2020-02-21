package edu.iastate.coms309.project309.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Controller for requests with a server
 * @author Sam Henley
 */
public class RequestController {

    private static RequestController instance;
    private RequestQueue rq;
    private static Context c;
    private JSONArray ra;

    /**
     * Initialize Request Controller
     * @param context Context of the application
     */
    public RequestController(Context context)
    {
        c = context;
        rq = getRequestQueue();

    }

    public static synchronized RequestController getInstance(Context context)
    {
        if (instance == null) {
            instance = new RequestController(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (rq == null) {
            rq = Volley.newRequestQueue(c.getApplicationContext());
        }
        return rq;
    }



    /**
     * Make a JsonObjectRequest
     * @param method type of request (Request.Method.GET, Request.Method.POST)
     * @param url address to make request with
     * @return response from server
     */
    public void requestJsonObject(int method, String url, Response.Listener<JSONObject> responseListener)
    {
        requestJsonObject(method, url, null, responseListener);
    }

    /**
     * Make a JsonObjectRequest
     * @param method type of request (Request.Method.GET, Request.Method.POST)
     * @param url address to make request with
     * @param jsonRequest JSONObject sent to server with request
     * @return response from server
     */
    public void requestJsonObject(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> responseListener)
    {
        Log.d("Volley", "Making Json " + ((method == 0) ? "GET" : "POST") + " Request to " + url);
        if (jsonRequest != null) {
            Log.d("Volley", "JSONObject sent to server: " + jsonRequest.toString());
        }

        JsonObjectRequest j = new JsonObjectRequest(method, url, jsonRequest, responseListener,
         new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("Volley", "Volley Error: " + e.toString());
                e.printStackTrace();
            }
        });

        rq.add(j);
    }

    /**
     * Make a JsonArrayRequest
     * @param method type of request (Request.Method.GET, Request.Method.POST)
     * @param url address to make request with
     * @return response from server
     */
    public void requestJsonArray(int method, String url, Response.Listener<JSONArray> responseListener)
    {
        requestJsonArray(method, url, null, responseListener);
    }

    /**
     * Make a JsonArrayRequest
     * @param method type of request (Request.Method.GET, Request.Method.POST
     * @param url address to make request with
     * @param jsonRequest JSONArray sent to server with request
     * @return response from server
     */
    public void requestJsonArray(int method, String url, JSONArray jsonRequest, Response.Listener<JSONArray> responseListener)
    {
        ra = null;
        Log.d("Volley", "Making Json " + ((method == 0) ? "GET" : "POST") + " Request to " + url);
        if (jsonRequest != null) {
            Log.d("Volley", "JSONObject sent to server: " + jsonRequest.toString());
        }

        JsonArrayRequest j = new JsonArrayRequest(method, url, jsonRequest, responseListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("Volley", "Volley Error: " + e.toString());
                e.printStackTrace();
            }
        });

        rq.add(j);

    }

}
