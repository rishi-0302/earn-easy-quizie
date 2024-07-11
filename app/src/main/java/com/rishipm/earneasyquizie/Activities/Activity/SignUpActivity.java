package com.rishipm.earneasyquizie.Activities.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rishipm.earneasyquizie.Activities.Model.User;
import com.rishipm.earneasyquizie.databinding.ActivitySignUpBinding;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing Create Account");



        binding.btnSubmit.setOnClickListener(v -> {
            String email, pw, name, referCode;

            email = binding.editTextEmail.getText().toString();
            pw = binding.editTextPassword.getText().toString();
            name = binding.editTextName.getText().toString();
            referCode = binding.editTextRefer.getText().toString();
            

            final User user = new User(name, email, pw, referCode);

            progressDialog.show();


            if (TextUtils.isEmpty(email)) {
                binding.editTextEmail.setError("Please Enter valid Email");
                return;
            }
            if (TextUtils.isEmpty(pw)) {
                binding.editTextPassword.setError("Minimum length of Password should be 6 characters");
                return;
            }

            auth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        String uid = task.getResult().getUser().getUid();

                        database
                                .collection("users")
                                .document(uid)
                                .set(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(SignUpActivity.this, "Account is Successfully created,please verify your email", Toast.LENGTH_SHORT).show();
                                                    progressDialog.dismiss();
                                                    auth.getCurrentUser().sendEmailVerification();
                                                    auth.signOut();
                                                    finish();
                                                }else{
                                                    progressDialog.dismiss();
                                                    Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        });

        binding.tvSignup.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        });

    }
}