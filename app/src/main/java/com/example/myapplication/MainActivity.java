package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button buttonLogWithEmail;
    private TextView textViewSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        launchRegistrationActivity();
        launchLoginActivity();
    }

    private void launchLoginActivity() {
        buttonLogWithEmail.setOnClickListener(v -> {
            Intent intent = LoginActivity.newIntent(this);
            startActivity(intent);
        });
    }

    private void launchRegistrationActivity() {
       textViewSignUp.setOnClickListener(v -> {
            Intent intent = RegistrationActivity.newIntent(this);
            startActivity(intent);
        });
    }

    private void initViews() {
        buttonLogWithEmail = findViewById(R.id.buttonLogWithEmail);
        textViewSignUp = findViewById(R.id.textViewSignUp);
    }
}