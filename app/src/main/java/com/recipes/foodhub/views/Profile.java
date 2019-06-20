package com.recipes.foodhub.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.recipes.foodhub.R;
import com.recipes.foodhub.model.recipe;
import com.recipes.foodhub.presenter.RecipeAdapter;
import com.recipes.foodhub.presenter.recipes_manager;
import com.recipes.foodhub.presenter.user_manager;

public class Profile extends AppCompatActivity {


    private ImageView image_user;
    private TextView name_user;
    private user_manager userInfo;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference recipesRef = db.collection("Recipes");
    private RecipeAdapter adapter;
    private Context mContext;
    private FloatingActionButton bt_add;
    recipes_manager mangerR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mContext=this;
        mangerR=new recipes_manager(mContext);
        userInfo = new user_manager(this);
        image_user=findViewById(R.id.id_image_user);
        name_user=findViewById(R.id.txt_nameUser);
        bt_add=findViewById(R.id.fab_add);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,addRecipe.class));
            }
        });

        image_user.setImageURI(Uri.parse(userInfo.getInfoFromShared().getImage_url()));
        name_user.setText(userInfo.getInfoFromShared().getName());

        Query query = recipesRef.orderBy("title").whereEqualTo("user_id",userInfo.getInfoFromShared().getUserid()).limit(100);

        FirestoreRecyclerOptions<recipe> options = new FirestoreRecyclerOptions.Builder<recipe>()
                .setQuery(query, recipe.class)
                .build();

        adapter = new RecipeAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_myRe);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                recipe Mrecipe=documentSnapshot.toObject(recipe.class);
                Intent intent=new Intent(mContext,detailRecipe.class);
                Bundle detail=new Bundle();
                detail.putString("title",Mrecipe.getTitle());
                detail.putString("Ingredient",Mrecipe.getIngredient());
                detail.putString("How_to",Mrecipe.getHow_to());
                detail.putString("Image",Mrecipe.getUrl_image());
                detail.putString("id",documentSnapshot.getId());
                int Ranking=Mrecipe.getRanking();
                Ranking++;

                mangerR.incrementView(documentSnapshot.getId(),Ranking);
                intent.putExtras(detail);
                startActivity(intent);

            }
        });

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
