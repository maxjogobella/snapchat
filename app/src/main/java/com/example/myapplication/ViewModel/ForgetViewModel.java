package com.example.myapplication.ViewModel;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    private MutableLiveData<String> message = new MutableLiveData<>();

    public ForgetViewModel() {

        mAuth = FirebaseAuth.getInstance();

    }

    public void sendRecovery(String email) {

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    message.setValue("Сообщение отправлено");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                message.setValue(e.getMessage());
            }
        });
    }

    public LiveData<String> getMessage() {
        return message;
    }
}
