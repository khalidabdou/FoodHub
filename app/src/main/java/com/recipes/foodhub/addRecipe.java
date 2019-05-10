package com.recipes.foodhub;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.recipes.foodhub.model.recipe;
import com.recipes.foodhub.presenter.IRescips_manager;
import com.recipes.foodhub.presenter.recipes_manager;

public class addRecipe extends AppCompatActivity {

    private EditText title_recipe,ingdedient,how_to;
    private IRescips_manager myrecipe;
    private recipe recipe;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        context=this;

        title_recipe=findViewById(R.id.id_title);
        ingdedient=findViewById(R.id.id_ingdedient);
        how_to=findViewById(R.id.id_how_to);
        myrecipe =new recipes_manager();
        recipe=new recipe("title1","ingr1","howto","url");


    }

    public void image_add(View view) {
        //myrecipe.addRecipe(recipe,context);
    }
}
