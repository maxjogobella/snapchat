package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.ViewModel.RegistrationViewModel;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    private Button buttonRegister;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextAge;
    private RegistrationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        initViews();
        regUser();
        createObserveViews();
    }

    private void regUser() {
        buttonRegister.setOnClickListener(v -> {
            String email = getStringData(editTextEmail);
            String password = getStringData(editTextPassword);
            String name = getStringData(editTextName);
            String age = getStringData(editTextAge);
            viewModel.signUp(email, password, name, age);
        });
    }

    private void createObserveViews() {
        viewModel.getMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getUserLv().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                Intent intent = UserActivity.newIntent(RegistrationActivity.this, firebaseUser.getUid());
                startActivity(intent);
                finish();
            }
        });
    }


    private String getStringData(EditText editTextEmail) {
        return editTextEmail.getText().toString().trim();
    }

    private void initViews() {
        editTextAge = findViewById(R.id.editTextAge);
        buttonRegister = findViewById(R.id.buttonRegister);
        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmailAddress);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, RegistrationActivity.class);
        return intent;
    }
}