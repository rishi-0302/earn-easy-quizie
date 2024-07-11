package com.rishipm.earneasyquizie.Activities.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rishipm.earneasyquizie.Activities.Model.User;
import com.rishipm.earneasyquizie.Activities.Model.WithdrawReq;
import com.rishipm.earneasyquizie.databinding.FragmentWalletBinding;

public class WalletFragment extends Fragment {


    public WalletFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentWalletBinding  binding;
    FirebaseFirestore database;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentWalletBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        database = FirebaseFirestore.getInstance();

        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(User.class);
                        binding.tvCoinBalance.setText(String.valueOf(user.getCoins()));
                    }
                });

        binding.sendReqBtn.setOnClickListener(v -> {
            if (user.getCoins() >= 50000) {

                String uid = FirebaseAuth.getInstance().getUid();
                String paypal = binding.edtPaypalMail.getText().toString();

                WithdrawReq req = new WithdrawReq(uid, paypal, user.getName());

                database.collection("withdraws")
                        .document(uid)
                        .set(req).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getContext(), "Request Sent Successfully.", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(getContext(), "You don't have enough coins to withdraw.", Toast.LENGTH_SHORT).show();
            }

        });


        return binding.getRoot();
    }



}