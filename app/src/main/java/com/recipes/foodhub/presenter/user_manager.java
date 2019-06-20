package com.recipes.foodhub.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.recipes.foodhub.model.user;
import  com.recipes.foodhub.data.*;
import com.recipes.foodhub.views.MainActivity;
import com.recipes.foodhub.views.Splash;


public class user_manager implements Iuser {
    Context context;
    public  user_manager(Context context){
        this.context=context;
    }
    @Override
    public void addUser(user user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("user").document(user.getUserid())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "okkk", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void getuserInfo(String id) {

    }

    @Override
    public void adduserShered(user user) {

        SharedPref save=new SharedPref(context);
        //save.saveString("email",user.getEmail());
        save.saveString("name",user.getName());
        save.saveString("image",user.getImage_url());
        save.saveString("idUsr",user.getUserid());
        save.saveInt("saved",1);
        Intent intent=new Intent(Splash.mContext, MainActivity.class);
        Splash.mContext.startActivity(intent);

    }

    @Override
    public user getInfoFromShared() {
        SharedPref get=new SharedPref(context);
        user user=new user();
        user.setName(get.getString("name"));
        user.setEmail(get.getString("email"));
        user.setImage_url(get.getString("image"));
        user.setUserid(get.getString("idUsr"));
        return user;
    }
}
