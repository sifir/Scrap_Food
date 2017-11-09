package ar.com.sifir.scrapfood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Sifir on 02/11/2017.
 */

public class RecipeList extends Activity {
    private final String HOST = "https://enigmatic-everglades-29402.herokuapp.com";
    private final String GETLIST = "/api/search/";
    int page = 1;
    String query;
    AdaptadorList adapter;
    @BindView(R.id.recyclerList)
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        final Gson gson = new Gson();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        query = bundle.getString("query");
        final Context ctx = this;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //hago la query con volley
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET,
                getQuery(query, page),
                //1er callback - respuesta
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Recipe[] list = gson.fromJson(response, Recipe[].class);
                        adapter = new AdaptadorList(ctx,list);
                        recyclerView.setAdapter(adapter);
                        Log.d("agarro: ", list[0].toString());
                    }
                },
                //2do callback - error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error de volley: ", error.getLocalizedMessage());
                    }
                });

        queue.add(request);
    }

    private String getQuery(String query, int page){

        return HOST+GETLIST+query+"/"+page;
    }

    private void getRecepies() {
        // llamar a request
    }
}
