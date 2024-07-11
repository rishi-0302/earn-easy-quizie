package com.rishipm.earneasyquizie.Activities.Activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rishipm.earneasyquizie.Activities.SpinWheel.LuckyWheelView;
import com.rishipm.earneasyquizie.Activities.SpinWheel.model.LuckyItem;
import com.rishipm.earneasyquizie.R;
import com.rishipm.earneasyquizie.databinding.ActivitySpinnerBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpinnerActivity extends AppCompatActivity {

    ActivitySpinnerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySpinnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.purple_700));
            View decorView = getWindow().getDecorView();
            if (Build.VERSION.SDK_INT >= 26) {
                getWindow().getDecorView().setSystemUiVisibility(decorView.getSystemUiVisibility() & -17);
            }
            if (Build.VERSION.SDK_INT >= 23) {
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & -8193);
            }
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.purple_700));
        }

        List<LuckyItem> data = new ArrayList<>();




        data.add(new LuckyItem("10", "Coins", Color.parseColor("#A90506"), Color.parseColor("#F2FFFA")));
        data.add(new LuckyItem("20", "Coins", Color.parseColor("#F2FFFA"), Color.parseColor("#A90506")));
        data.add(new LuckyItem("50", "Coins", Color.parseColor("#010379"), Color.parseColor("#F2FFFA")));
        data.add(new LuckyItem("30", "Coins", Color.parseColor("#F2FFFA"), Color.parseColor("#010379")));
        data.add(new LuckyItem("40", "Coins", Color.parseColor("#A90506"), Color.parseColor("#F2FFFA")));
        data.add(new LuckyItem("5", "Coins", Color.parseColor("#F2FFFA"), Color.parseColor("#A90506")));
        data.add(new LuckyItem("25", "Coins", Color.parseColor("#010379"), Color.parseColor("#F2FFFA")));
        data.add(new LuckyItem("0", "Coins", Color.parseColor("#F2FFFA"), Color.parseColor("#010379")));

        binding.wheelView.setData(data);
        binding.wheelView.setRound(5);

        binding.btnSpin.setOnClickListener(v -> {
            Random r = new Random();
            int randNo = r.nextInt(8);

            binding.wheelView.startLuckyWheelWithTargetIndex(randNo);
        });

        binding.wheelView.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {
                updateCoin(index);
            }
        });

    }

    void updateCoin(int index) {

        long cash = 0;

        switch (index) {
            case 0:
                cash = 10;
                break;
            case 1:
                cash = 20;
                break;
            case 2:
                cash = 50;
                break;
            case 3:
                cash = 30;
                break;
            case 4:
                cash = 40;
                break;
            case 5:
                cash = 5;
                break;
            case 6:
                cash = 25;
                break;
            case 7:
                cash = 0;
                break;
        }

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(cash)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(SpinnerActivity.this, "Coins Added Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}