package com.example.myapplication.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserViewModel extends ViewModel {

    private final FirebaseDatabase firebaseDatabase;
    private final DatabaseReference userReference;
    private final FirebaseAuth mAuth;

    private MutableLiveData<List<User>> userList = new MutableLiveData<List<User>>();
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public UserViewModel() {
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userReference = firebaseDatabase.getReference("Users");

        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                firebaseUserMutableLiveData.setValue(firebaseUser);
            }
        });

        addUsersFromDbToLiveData();
    }

    private void addUsersFromDbToLiveData() {
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser == null) {
                    return;
                }

                List<User> users = new ArrayList<>();
                User user;
                for (DataSnapshot snap : snapshot.getChildren()) {
                    user = snap.getValue(User.class);
                    if (user != null && !user.getId().equals(currentUser.getUid())) {
                        users.add(user);
                    }
                }
                userList.setValue(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorMessage.setValue(error.getMessage());
            }
        });
    }

    public void setUserOnline(boolean isOnline) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            return;
        }
        userReference.child(currentUser.getUid()).child("status").setValue(isOnline);
    }

    public LiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public LiveData<List<User>> getUserList() {
        return userList;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
