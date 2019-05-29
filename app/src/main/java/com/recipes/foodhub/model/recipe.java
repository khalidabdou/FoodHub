package com.recipes.foodhub.model;

public class recipe {
    private String Id_recp;
    private String title;
    private String ingredient;
    private String how_to;
    private String url_image;

    public recipe(){}
    public recipe(String title, String ingredient, String how_to, String url_image,String Id_recp) {
        this.Id_recp=Id_recp;
        this.title = title;
        this.ingredient = ingredient;
        this.how_to = how_to;
        this.url_image = url_image;
    }

    public String getId_recp() {
        return Id_recp;
    }

    public void setId_recp(String id_recp) {
        Id_recp = id_recp;
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
