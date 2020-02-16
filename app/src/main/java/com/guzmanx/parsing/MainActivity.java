package com.guzmanx.parsing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;

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

public class MainActivity extends AppCompatActivity {

    // https://jsonplaceholder.typicode.com/todos/1
    //private RequestQueue requestQueue;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //requestQueue = Volley.newRequestQueue(this);
        queue = VolleySingleton.getInstance(this.getApplicationContext())
                .getRequestQueue();

        // Make a JsonObjectRequest
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://jsonplaceholder.typicode.com/todos/1", (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("JSON", "onResponse: " + response.getString("title"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "onErrorResponse: " + error.getMessage());
            }
        });
        //requestQueue.add(jsonObjectRequest);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                "https://jsonplaceholder.typicode.com/todos", (JSONArray) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("JsonArray", "onResponse: " + response);

                        for(int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                Log.d("JsonArray", "onResponse: "
                                        + jsonObject.getString("id") +
                                        " " + jsonObject.getString("title"));
                                boolean d = jsonObject.getBoolean("completed");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", "onErrorResponse: " + error.getMessage());
                    }
        });
        //requestQueue.add(jsonArrayRequest);
        queue.add(jsonArrayRequest);
    }
}
