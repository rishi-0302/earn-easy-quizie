package com.rishipm.earneasyquizie.Activities.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.rishipm.earneasyquizie.databinding.ActivityLoginBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth auth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");


//        binding.btnSubmit.setOnClickListener(v -> {
//            String email, pw;
//            email = binding.editTextEmail.getText().toString();
//            pw = binding.editTextPassword.getText().toString();
//
//            progressDialog.show();
//
//
//            if (TextUtils.isEmpty(email)) {
//                binding.editTextEmail.setError("Please Enter valid Email");
//                return;
//            }
//            if(TextUtils.isEmpty(pw)){
//                binding.editTextPassword.setError("Minimum length of Password should be 6 characters");
//                return;
//            }
//
//            auth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(task -> {
//                if(task.isSuccessful()){
//                    progressDialog.dismiss();
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    finish();
//                }else{
//                    progressDialog.dismiss();
//                    Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        });

        binding.btnSubmit.setOnClickListener(v-> login_user());

        binding.tvSignup.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            finish();
        });
        binding.btnForgotpw.setOnClickListener(v -> forgetPw());

    }

    void forgetPw(){
        String email = binding.editTextEmail.getText().toString();

        if (TextUtils.isEmpty(email)) {
            binding.editTextEmail.setError("Please Enter valid Email");
            return;
        }


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.sendPasswordResetEmail(email);

        Toast.makeText(this, "A password reset email has been sent to your email address.", Toast.LENGTH_SHORT).show();

    }

    boolean validateData(String email,String password){

        // validate the data which are input by the user
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.editTextEmail.setError("Email is invalid");
            return false;
        }
        if (password.length() < 6){
            binding.editTextPassword.setError("Password must be atleast 6 characters long");
            return false;
        }
        return true;
    }

    void LoginAccInFirebase(String email, String password) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
//                    logedin sucessfully
                if (Objects.requireNonNull(firebaseAuth.getCurrentUser()).isEmailVerified()){
//                        go to main Activity
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "\"Email is not verified, Please verify your email!", Toast.LENGTH_SHORT).show();
                }
            }else{
//                    login failed
                Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void login_user() {

        String email = binding.editTextEmail.getText().toString();
        String password = binding.editTextPassword.getText().toString();

        boolean isValidateData = validateData(email, password);
        if (!isValidateData){
            return;
        }
        LoginAccInFirebase(email, password);
    }
}