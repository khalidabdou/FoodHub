package com.recipes.foodhub.presenter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.recipes.foodhub.R;
import com.recipes.foodhub.model.recipe;
import com.squareup.picasso.Picasso;

public class RecipeAdapter  extends FirestoreRecyclerAdapter<recipe, RecipeAdapter.recipeHolder> {
    private OnItemClickListener listener;
    public RecipeAdapter(@NonNull FirestoreRecyclerOptions<recipe> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull recipeHolder holder, int position, @NonNull recipe model) {
        holder.txt_title.setText(model.getTitle());
        holder.Ranking.setText(String.valueOf(model.getRanking()));
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
        TextView Ranking;
        ImageView imageRecipe;

        public recipeHolder(View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.id_name_recipe);
            Ranking=itemView.findViewById(R.id.id_ranking);
            imageRecipe=itemView.findViewById(R.id.id_image_recipe);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   int position = getAdapterPosition();
                   if (position !=RecyclerView.NO_POSITION && listener != null){
                       listener.onItemClick(getSnapshots().getSnapshot(position), position);
                   }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
