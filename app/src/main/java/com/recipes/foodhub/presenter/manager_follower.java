package com.recipes.foodhub.presenter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class manager_follower implements InterfaceFollowers {
    Context context;
    manager_follower(Context context){
        this.context=context;
    }
    @Override
    public void getCoutFollwers(String id) {

    }

    @Override
    public void getCountFollowing(String id) {

    }

    @Override
    public void addfollower(String idUser,String  idfollower) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("user").document(idUser)
                .set(idfollower)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
