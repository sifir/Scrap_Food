package ar.com.sifir.scrapfood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sifir on 02/11/2017.
 */

public class AdaptadorList extends RecyclerView.Adapter<AdaptadorList.ViewHolder> {
    Context context;
    Recipe[] recipes;
    ImageLoader loader;

    public AdaptadorList(Context context, Recipe[] recipes){
        this.context = context;
        this.recipes = recipes;
        RequestQueue r = Volley.newRequestQueue(context);
        this.loader = new ImageLoader(r, new ImageLoader.ImageCache() {
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
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Recipe receta = recipes[position];
        holder.mRecipeTitle.setText(receta.getTitle());
        holder.mRecipeIngredients.setText(receta.getIngredientsString());
        holder.mRecipeImage.setImageUrl(receta.getImg(),loader);
        holder.mRecipeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RecipeActivity.class);
                intent.putExtra("url", receta.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.recipeTitle)
        TextView mRecipeTitle;

        @BindView(R.id.recipeIngredients)
        TextView mRecipeIngredients;

        @BindView(R.id.recipeImage)
        NetworkImageView mRecipeImage;

        @BindView(R.id.itemCont)
        LinearLayout mRecipeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
