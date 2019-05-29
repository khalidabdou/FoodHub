package com.recipes.foodhub.presenter;

public interface InterfaceFollowers {
    void getCoutFollwers(String id);
    void getCountFollowing(String id);
    void addfollower(String idUser,String  IdRes);
}
