package com.recipes.foodhub.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.recipes.foodhub.firebase.manager_firebase;
import com.recipes.foodhub.model.recipe;

import java.util.HashMap;
import java.util.Map;

public class recipes_manager implements IRescips_manager {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    public void addRecipe(recipe recipe, final Context context) {

        db.collection("Recipes")
                .add(recipe)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(context,  "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


    }

    @Override
    public void getRecipe(String id) {
        DocumentReference ref=db.collection("Recipes").document(id);
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        recipe recipe=documentSnapshot.toObject(recipe.class);
                    }
                });
    }
}
