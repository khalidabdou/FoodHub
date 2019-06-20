package com.recipes.foodhub.model;

public class user {

    public String userid;
    public String name;
    public String email;
    public String image_url;
    public int followers;
    public int following;

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public  user(){};
    public user(String userid, String name, String email, String image_url) {
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.image_url = image_url;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
