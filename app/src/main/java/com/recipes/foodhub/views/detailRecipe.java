package com.recipes.foodhub.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.recipes.foodhub.R;


public class detailRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);
        String id=getIntent().getExtras().getString("id");
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
    }
}
