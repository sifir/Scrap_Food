package ar.com.sifir.scrapfood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
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
    String url;

    @BindView(R.id.recipeTitle2)
    TextView mRecipeTitle;
    @BindView(R.id.recipeImage2)
    NetworkImageView mRecipeImage;

    Context context;
    String imgPlaceholder;
    ImageLoader loader;
    //ejemplo de url https://enigmatic-everglades-29402.herokuapp.com/api/recipe/3609699-papas-rellenas

    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        final Activity activity = this;
        context = this;

        ButterKnife.bind(this);
        final Gson gson = new Gson();

        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        url = bundle.getString("url");
        //saco la url limpia
        String[] separate = this.url.split("/");
        url = separate[3];


        //hago la query con volley
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET,
                // si aca lo hago con URL que es lo correcto, crashea
                getQuery(url),
                //1er callback - respuesta
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Recipe recipe = gson.fromJson(response, Recipe.class);
                        mRecipeTitle.setText(recipe.getTitle());
                        imgPlaceholder = recipe.getImg();

                        //creo el cache de la imagen y la seteo
                        RequestQueue r = Volley.newRequestQueue(context);
                        loader = new ImageLoader(r, new ImageLoader.ImageCache() {
                            private final LruCache<String, Bitmap> mcache = new LruCache<>(10);
                            @Override
                            public Bitmap getBitmap(String url) {
                                return mcache.get(url);
                            }

                            @Override
                            public void putBitmap(String url, Bitmap bitmap) {
                                mcache.put(url,bitmap);
                            }
                        });
                        mRecipeImage.setImageUrl(recipe.getImg(), loader);

                        //uso de la lista
                        AdaptadorStep adaptador = new AdaptadorStep(activity, R.layout.activity_step,recipe.getStepsText(),recipe.getStepsImg());
                        ListView listView = (ListView)findViewById(R.id.listSteps);
                        listView.setAdapter(adaptador);


/*                        Log.d("agarro steps text: ", recipe.getStepsText().toString());
                        Log.d("agarro step imgs: ", recipe.getStepsImg().toString());*/
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
