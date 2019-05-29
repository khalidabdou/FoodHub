package com.recipes.foodhub.presenter;

import android.content.Context;

import com.recipes.foodhub.model.user;

public interface Iuser {
    void addUser(user user);
    void getuserInfo(String id);
    void adduserShered(user user);
    user getInfoFromShared();
}
