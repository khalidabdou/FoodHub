package com.recipes.foodhub.presenter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.recipes.foodhub.R;
import com.recipes.foodhub.model.recipe;
import com.squareup.picasso.Picasso;

public class RecipeAdapter  extends FirestoreRecyclerAdapter<recipe, RecipeAdapter.recipeHolder> {
    public RecipeAdapter(@NonNull FirestoreRecyclerOptions<recipe> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull recipeHolder holder, int position, @NonNull recipe model) {
        holder.txt_title.setText(model.getTitle());
        //holder.txt_ingredient.setText(model.getIngredient());
       // holder.txt_how.setText(String.valueOf(model.getHow_to()));
        Picasso.get().load(model.getUrl_image()).into(holder.imageRecipe);

    }

    @NonNull
    @Override
    public recipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe,
                parent, false);
        return new recipeHolder(v);
    }

    class recipeHolder extends RecyclerView.ViewHolder {
        TextView txt_title;
       // TextView txt_ingredient;
       // TextView txt_how;
        ImageView imageRecipe;

        public recipeHolder(View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.id_name_recipe);
           // txt_ingredient = itemView.findViewById(R.id.id_name_recipe);
            //txt_how = itemView.findViewById(R.id.text_view_priority);
            imageRecipe=itemView.findViewById(R.id.image_recipe);
        }
    }
}
