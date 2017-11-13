package ar.com.sifir.scrapfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by joaco on 11/6/17.
 */

public class RecipeActivity extends Activity {
    private final String HOST="https://enigmatic-everglades-29402.herokuapp.com";
    private final String RECIPE = "/api/recipe/";
    String query;
    String url;

    @BindView(R.id.recipeTitle2)
    TextView mRecipeTitle;
    @BindView(R.id.recipeImage2)
    NetworkImageView mRecipeImage;;
    //ejemplo de url https://enigmatic-everglades-29402.herokuapp.com/api/recipe/3609699-papas-rellenas

    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ButterKnife.bind(this);
        final Gson gson = new Gson();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        url = bundle.getString("url");
        //saco la url limpia
        String[] separate = this.url.split("/");
        url = separate[3];


        //hago la query con volley
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET,
                // si aca lo hago con URL que es lo correcto, crashea
                getQuery(query),
                //1er callback - respuesta
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Recipe recipe = gson.fromJson(response, Recipe.class);
                        mRecipeTitle.setText(recipe.getTitle());

                        Log.d("agarro objeto: ", recipe.toString());
                        Log.d("agarro query: ", getQuery(url));
                        Log.d("agarro url: ", url);
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

    private String getQuery(String query){
        return HOST+RECIPE+query;
    }
}
