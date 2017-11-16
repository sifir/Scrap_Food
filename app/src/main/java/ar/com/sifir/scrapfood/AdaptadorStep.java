package ar.com.sifir.scrapfood;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import butterknife.BindView;

/**
 * Created by Sifir on 16/11/2017.
 */

public class AdaptadorStep extends ArrayAdapter<String>{

    @BindView(R.id.stepTextView)
    TextView mStepText;
    @BindView(R.id.stepNetworkImageView)
    NetworkImageView mStepView;

    Activity context;
    String[] img;
    String[] text;

    public AdaptadorStep(Activity context, int resource, String[] text, String[] img) {
        super(context, resource);
        this.context = context;
        this.text = text;
        this.img = img;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.activity_step, null);

        //seteo
        ImageLoader loader;
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
        mStepView.setImageUrl(img[position],loader);
        mStepText.setText(text[position]);

        return item;
    }
}
