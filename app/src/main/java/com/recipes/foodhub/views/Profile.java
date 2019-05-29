package com.recipes.foodhub.views;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.recipes.foodhub.R;
import com.recipes.foodhub.model.recipe;
import com.recipes.foodhub.presenter.RecipeAdapter;
import com.recipes.foodhub.presenter.user_manager;

public class Profile extends AppCompatActivity {


    private ImageView image_user;
    private TextView name_user;
    private user_manager userInfo;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference recipesRef = db.collection("Recipes");
    private RecipeAdapter adapter;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mContext=this;
        userInfo = new user_manager(this);
        image_user=findViewById(R.id.id_image_user);
        name_user=findViewById(R.id.txt_nameUser);

        image_user.setImageURI(Uri.parse(userInfo.getInfoFromShared().getImage_url()));
        name_user.setText(userInfo.getInfoFromShared().getName());

        Query query = recipesRef.orderBy("title").limit(3);

        FirestoreRecyclerOptions<recipe> options = new FirestoreRecyclerOptions.Builder<recipe>()
                .setQuery(query, recipe.class)
                .build();

        adapter = new RecipeAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_myRe);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart(){
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop(){
        super.onStop();
        adapter.stopListening();
    }
}
