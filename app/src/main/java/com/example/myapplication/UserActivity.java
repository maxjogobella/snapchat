package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.Adapter.UsersAdapter;
import com.example.myapplication.Enum.NavigationItemId;
import com.example.myapplication.Model.User;
import com.example.myapplication.ViewModel.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class UserActivity extends AppCompatActivity {

    private UserViewModel viewModel;
    private RecyclerView recyclerViewUsers;
    private UsersAdapter adapter;
    private static final String EXTRA_CURRENT_USER_ID = "current_id";
    private String currentUserId;
    private BottomNavigationView navigationUserMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        initViews();
        adapter = new UsersAdapter();
        recyclerViewUsers.setAdapter(adapter);
        observeViewModel();
        observeMenuItem();


        adapter.setOnUserClickListener(user -> {
            String otherUserId = user.getId();
            currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);

            Intent intent = ChatActivity.newIntent(this, currentUserId, otherUserId);
            startActivity(intent);
        });

    }

    private void observeMenuItem() {
        navigationUserMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                switch (NavigationItemId.fromId(itemId)) {
                    case PROFILE:
                        Intent intent = ProfileActivity.newIntent(UserActivity.this, currentUserId);
                        startActivity(intent);
                        break;
                    case SETTINGS:
                        break;
                }
                return true;
            }
        });
    }

    private void observeViewModel() {

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                Toast.makeText(UserActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getUserList().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adapter.setUsers(users);
            }
        });

        viewModel.getFirebaseUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser == null) {
                    Intent intent = LoginActivity.newIntent(UserActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    public static Intent newIntent(Context context, String currentUserId) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId);
        return intent;
    }

    private void initViews() {
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        navigationUserMenu = findViewById(R.id.navigationUserMenu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.setUserOnline(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.setUserOnline(false);
    }
}