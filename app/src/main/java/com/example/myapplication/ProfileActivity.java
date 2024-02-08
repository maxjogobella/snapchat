package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.Enum.NavigationItemId;
import com.example.myapplication.ViewModel.ProfileViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profilePicture;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;
    private static final String EXTRA_CURRENT_USER_ID = "current_id";
    private String currentUserId;
    private BottomNavigationView navigationUserMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();

        profilePicture.setOnClickListener(v -> {
            if (checkPermission()) {
                openGallery();
            } else {
                requestPermission();
            }
        });

        observeMenuItem();
    }


    private void observeMenuItem() {
        navigationUserMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                switch (NavigationItemId.fromId(itemId)) {
                    case HOME:
                        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
                        Intent intent = UserActivity.newIntent(ProfileActivity.this, currentUserId);
                        startActivity(intent);
                        break;
                    case SETTINGS:
                        break;
                }
                return true;
            }
        });
    }


    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);ч
    }

    private void requestPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            profilePicture.setImageURI(data.getData());
        }
    }

    public static Intent newIntent(Context context, String currentUserId) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId);
        return intent;
    }


    private void initViews() {
        profilePicture = findViewById(R.id.profilePicture);
        navigationUserMenu = findViewById(R.id.navigationUserMenu);
    }

}