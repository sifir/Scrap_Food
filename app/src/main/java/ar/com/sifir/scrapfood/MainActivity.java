package ar.com.sifir.scrapfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    String query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void search (View v){
        EditText t = (EditText) findViewById(R.id.searchField);
        query = t.getText().toString();

        Intent myIntent = new Intent(this, RecipeListActivity.class);
        myIntent.putExtra("query", query);
        startActivity(myIntent);
    }
}
