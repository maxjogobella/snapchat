package com.example.myapplication.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<FirebaseUser> userLv = new MutableLiveData<>();
    private final FirebaseDatabase firebaseDatabase;
    private final DatabaseReference usersReference;

    public RegistrationViewModel() {
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        usersReference = firebaseDatabase.getReference("Users");

        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    userLv.setValue(firebaseUser);
                }
            }
        });
    }

    public void signUp(String email,
                       String password,
                       String userName,
                       String age) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser firebaseUser = authResult.getUser();
                if (firebaseUser == null) {
                    return;
                } else {
                    User user = new User(
                            firebaseUser.getUid(),
                            age,
                            userName,
                            false
                    );
                    usersReference.child(user.getId()).setValue(user);
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

    public LiveData<FirebaseUser> getUserLv() {
        return userLv;
    }
}
