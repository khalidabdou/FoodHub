package com.recipes.foodhub.model;

public class recipe {
    public String title;
    public String ingredient;
    public String how_to;
    public String url_image;

    public recipe(){}
    public recipe(String title, String ingredient, String how_to, String url_image) {
        this.title = title;
        this.ingredient = ingredient;
        this.how_to = how_to;
        this.url_image = url_image;
    }
}
