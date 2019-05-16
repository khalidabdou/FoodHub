package com.recipes.foodhub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.recipes.foodhub.model.recipe;
import com.recipes.foodhub.presenter.RecipeAdapter;

public class getRecipesActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference recipesRef = db.collection("Recipes");

    private RecipeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_recipes);
        setUpRecyclerView();

    }
    private void setUpRecyclerView() {
        Query query = recipesRef.orderBy("title").limit(3);

        FirestoreRecyclerOptions<recipe> options = new FirestoreRecyclerOptions.Builder<recipe>()
                .setQuery(query, recipe.class)
                .build();

        adapter = new RecipeAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        Toast.makeText(this, "recycle", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
