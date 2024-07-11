package com.rishipm.earneasyquizie.Activities.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rishipm.earneasyquizie.Activities.Fragments.HomeFragment;
import com.rishipm.earneasyquizie.databinding.ActivityResultBinding;

import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

    ActivityResultBinding binding;
    int POINTS = 12;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int correctAns = getIntent().getIntExtra("correct", 0);
        int totalQue = getIntent().getIntExtra("total", 0);

        long pointCoins = (long) correctAns * POINTS;

        binding.tvScore.setText(String.format("%d/%d", correctAns, totalQue));
        binding.tvCoins.setText(String.valueOf(pointCoins));

        FirebaseFirestore databse = FirebaseFirestore.getInstance();
        databse.collection("users")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .update("coins", FieldValue.increment(pointCoins));

        binding.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, "I just scored " + correctAns + " out of " + totalQue + " in the EarnEasyQuiz! and earned " + pointCoins + " Coins.");
                startActivity(Intent.createChooser(i, "Earn Eay Quizie"));
                finish();
            }
        });
        binding.btnClose.setOnClickListener(v -> {
            startActivity(new Intent(ResultActivity.this, HomeFragment.class));
            finish();
        });


    }

}