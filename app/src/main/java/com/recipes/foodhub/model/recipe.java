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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getHow_to() {
        return how_to;
    }

    public void setHow_to(String how_to) {
        this.how_to = how_to;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }
}
