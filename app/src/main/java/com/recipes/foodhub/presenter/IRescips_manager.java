package com.recipes.foodhub.presenter;

import android.content.Context;

import com.recipes.foodhub.model.recipe;

public interface IRescips_manager {
    void addRecipe(recipe recipe, Context context);
    recipe getRecipe(String id);
    void incrementView(String id,int ranaking);
}
