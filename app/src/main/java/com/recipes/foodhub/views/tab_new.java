package com.recipes.foodhub.views;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link tab_new.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link tab_new#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tab_new extends Fragment implements Serializable {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference recipesRef = db.collection("Recipes");
    private RecipeAdapter adapter;
    recipes_manager mangerR;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public tab_new() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tab_new.
     */
    // TODO: Rename and change types and number of parameters
    public static tab_new newInstance(String param1, String param2) {
        tab_new fragment = new tab_new();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View tab_new_view=inflater.inflate(R.layout.fragment_new_recipe, container, false);
        mangerR=new recipes_manager(getActivity());
        Query query = recipesRef.orderBy("title").limit(100);

        FirestoreRecyclerOptions<recipe> options = new FirestoreRecyclerOptions.Builder<recipe>()
                .setQuery(query, recipe.class)
                .build();

        adapter = new RecipeAdapter(options);

        final RecyclerView recyclerView = tab_new_view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                recipe Mrecipe=documentSnapshot.toObject(recipe.class);
                Intent intent=new Intent(getActivity(),detailRecipe.class);
                Bundle detail=new Bundle();
                detail.putString("title",Mrecipe.getTitle());
                detail.putString("Ingredient",Mrecipe.getIngredient());
                detail.putString("How_to",Mrecipe.getHow_to());
                detail.putString("Image",Mrecipe.getUrl_image());
                detail.putString("id",documentSnapshot.getId());
                int Ranking=Mrecipe.getRanking();
                Ranking++;
                Toast.makeText(getActivity(), String.valueOf(Ranking), Toast.LENGTH_SHORT).show();
                mangerR.incrementView(documentSnapshot.getId(),Ranking);
                intent.putExtras(detail);
                startActivity(intent);

            }
        });

        return tab_new_view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

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
