package com.rishipm.earneasyquizie.Activities.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.rishipm.earneasyquizie.Activities.Activity.SpinnerActivity;
import com.rishipm.earneasyquizie.Activities.Adapters.CategoryAdapter;
import com.rishipm.earneasyquizie.Activities.Model.CategoryModel;
import com.rishipm.earneasyquizie.databinding.FragmentHomeBinding;

import java.util.ArrayList;


public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    FragmentHomeBinding binding;
    FirebaseFirestore database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment;
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        database = FirebaseFirestore.getInstance();

        final ArrayList<CategoryModel> categories = new ArrayList<>();
        final CategoryAdapter adapter = new CategoryAdapter(getContext(), categories);

        database.collection("categories").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    // Handle the Firestore exception here if needed
                    Log.e("FirestoreError", "Error fetching categories: " + error.getMessage());
                    return;
                }

                if (value != null) {
                    categories.clear();
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        CategoryModel model = snapshot.toObject(CategoryModel.class);
                        model.setCatId(snapshot.getId());
                        categories.add(model);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    // Handle the case when value is null
                    Log.e("FirestoreError", "QuerySnapshot value is null.");
                }
            }
        });


        binding.categoryRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.categoryRv.setAdapter(adapter);

        binding.spinWheel.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), SpinnerActivity.class);
            startActivity(i);
        });

        binding.inviteFrnds.setOnClickListener(v -> {

            // Create a link to the Play Store.
            String playStoreLink = "https://play.google.com/store/apps/details?id=com.example.myapp";

            // Create an intent to open the Play Store.
            Intent openPlayStoreIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(playStoreLink));

            // Start the intent.
            startActivity(openPlayStoreIntent);
        });
        return binding.getRoot();
    }
}