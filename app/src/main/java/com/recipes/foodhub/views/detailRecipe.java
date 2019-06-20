package com.recipes.foodhub.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.recipes.foodhub.R;
import com.recipes.foodhub.model.recipe;
import com.recipes.foodhub.presenter.IRescips_manager;
import com.recipes.foodhub.presenter.recipes_manager;
import com.squareup.picasso.Picasso;

import java.io.Serializable;


public class detailRecipe extends AppCompatActivity implements Serializable {
    recipes_manager manager;
    recipe detail;
    TextView title,Ingredient,Howto;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);
        title=findViewById(R.id.id_title);
        Ingredient=findViewById(R.id.id_Ingredient);
        Howto=findViewById(R.id.id_Howto);
        image=findViewById(R.id.id_image_recipe);
        manager=new recipes_manager(this);
        Bundle Mrecipe=getIntent().getExtras();

        title.setText(Mrecipe.getString("title"));
        Ingredient.setText(Mrecipe.getString("Ingredient"));
        Howto.setText(Mrecipe.getString("How_to"));
        Picasso.get().load(Mrecipe.getString("Image")).into(image);



    }
}
