package ar.com.sifir.scrapfood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sifir on 16/11/2017.
 */

public class AdaptadorStep extends RecyclerView.Adapter<AdaptadorStep.ViewHolder> {
    Context context;
    Step[] steps;
    ImageLoader loader;

    public AdaptadorStep(Context context, Step[] steps){
        this.context = context;
        this.steps = steps;
        RequestQueue r = Volley.newRequestQueue(context);
        this.loader = new ImageLoader(r, new ImageLoader.ImageCache() {
            private final android.support.v4.util.LruCache<String, Bitmap> mcache = new android.support.v4.util.LruCache<>(10);
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

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Step step = steps[position];
        if (step.getImg() != null)
            holder.mNetworkImage.setImageUrl(step.getImg(),loader);
            holder.mStepText.setText(step.getText());
    }

    @Override
    public int getItemCount() {
        return steps.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.stepTextView)
        TextView mStepText;

        @BindView(R.id.stepNetworkImageView)
        NetworkImageView mNetworkImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

