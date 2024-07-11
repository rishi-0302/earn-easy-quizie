package com.rishipm.earneasyquizie.Activities.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.rishipm.earneasyquizie.Activities.Fragments.HomeFragment;
import com.rishipm.earneasyquizie.Activities.Fragments.LeaderboardFragment;
import com.rishipm.earneasyquizie.Activities.Fragments.ProfileFragment;
import com.rishipm.earneasyquizie.Activities.Fragments.WalletFragment;
import com.rishipm.earneasyquizie.R;
import com.rishipm.earneasyquizie.databinding.ActivityMainBinding;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new HomeFragment());
        transaction.commit();

        binding.bottomBar.setOnItemSelectedListener((OnItemSelectedListener) i -> {

            FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
            switch (i) {
                case 0 :
                    transaction1.replace(R.id.content, new HomeFragment());
                    transaction1.commit();
                    break;
                case 1 :
                    transaction1.replace(R.id.content, new LeaderboardFragment());
                    transaction1.commit();
                    break;
                case 2 :
                    transaction1.replace(R.id.content, new WalletFragment());
                    transaction1.commit();
                    break;
                case 3 :
                    transaction1.replace(R.id.content, new ProfileFragment());
                    transaction1.commit();
                    break;
            }

            return false;
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logout){
            // Create an alert dialog
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("Logout");
            dialog.setMessage("Are you sure you want to logout?");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Logout
                    FirebaseAuth.getInstance().signOut();
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            });
            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}