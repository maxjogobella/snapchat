package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ViewModel.LoginViewModel;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextView textViewForgotPassword;
    private TextView textViewSignUp;
    private EditText editTextEmailLogin;
    private EditText editTextPasswordLogin;
    private Button buttonLogin;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activtiy);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        initViews();
        launchForgotActivity();
        launchRegistrationActivity();
        createObserveViews();
        signIn();
    }

    private void signIn() {
        buttonLogin.setOnClickListener(v -> {
            String email = getStringData(editTextEmailLogin);
            String password = getStringData(editTextPasswordLogin);
            viewModel.logIn(email, password);
        });
    }

    private String getStringData(EditText editText) {
        return editText.getText().toString().trim();
    }

    private void createObserveViews() {
        viewModel.getUserLv().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                Intent intent = UserActivity.newIntent(LoginActivity.this, firebaseUser.getUid());
                startActivity(intent);
                finish();
            }
        });

        viewModel.getMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void launchRegistrationActivity() {
        textViewSignUp.setOnClickListener(v -> {
            Intent intent = RegistrationActivity.newIntent(this);
            startActivity(intent);
        });
    }

    private void launchForgotActivity() {
        textViewForgotPassword.setOnClickListener(v -> {
            String email = editTextEmailLogin.getText().toString().trim();
            Intent intent = ForgotActivity.newIntent(this, email);
            startActivity(intent);
        });
    }

    private void initViews() {
        textViewForgotPassword = findViewById(R.id.textViewForgotPasswordL);
        textViewSignUp = findViewById(R.id.textViewSignUpL);
        editTextEmailLogin = findViewById(R.id.editTextEmailLogin);
        editTextPasswordLogin = findViewById(R.id.editTextPasswordLogin);
        buttonLogin = findViewById(R.id.buttonLogin);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }
}