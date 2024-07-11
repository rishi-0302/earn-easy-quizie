package com.rishipm.earneasyquizie.Activities.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.rishipm.earneasyquizie.Activities.Adapters.LeaderboardAdapter;
import com.rishipm.earneasyquizie.Activities.Model.User;
import com.rishipm.earneasyquizie.databinding.FragmentLeaderboardBinding;

import java.util.ArrayList;


public class LeaderboardFragment extends Fragment {


    public LeaderboardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentLeaderboardBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLeaderboardBinding.inflate(inflater, container, false);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        final ArrayList<User> users = new ArrayList<>();
        LeaderboardAdapter adapter = new LeaderboardAdapter(getContext(), users);

        binding.rvLeaderboard.setAdapter(adapter);
        binding.rvLeaderboard.setLayoutManager(new LinearLayoutManager(getContext()));

        database.collection("users")
                .orderBy("coins", Query.Direction.DESCENDING)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {

                            User user = snapshot.toObject(User.class);
                            users.add(user);
                        }
                        adapter.notifyDataSetChanged();

                    }
                });

        return binding.getRoot();
    }
}