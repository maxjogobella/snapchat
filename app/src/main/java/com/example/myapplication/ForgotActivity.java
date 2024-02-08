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

import com.example.myapplication.ViewModel.ForgetViewModel;

public class ForgotActivity extends AppCompatActivity {

    private Button buttonSendRecovery;
    private EditText editTextRecovery;
    private ForgetViewModel viewModel;
    private static final String EXTRA_KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        viewModel = new ViewModelProvider(this).get(ForgetViewModel.class);
        initViews();
        setEmailIntoEditText();
        send();
        createObserveViews();
    }


    private void createObserveViews() {
        viewModel.getMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                Toast.makeText(ForgotActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void send() {
        buttonSendRecovery.setOnClickListener(v -> {
            String email = editTextRecovery.getText().toString().trim();
            viewModel.sendRecovery(email);
        });
    }

    private void setEmailIntoEditText() {
        String email = getIntent().getStringExtra(EXTRA_KEY_EMAIL);
        editTextRecovery.setText(email);
    }

    public static Intent newIntent(Context context, String email) {
        Intent intent = new Intent(context, ForgotActivity.class);
        intent.putExtra(EXTRA_KEY_EMAIL, email);
        return intent;
    }

    private void initViews() {
        buttonSendRecovery = findViewById(R.id.buttonSendRecovery);
        editTextRecovery = findViewById(R.id.editTextRecovery);
    }

}