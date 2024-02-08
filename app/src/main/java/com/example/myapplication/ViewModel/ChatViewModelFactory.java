package com.example.myapplication.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.ViewModel.ChatViewModel;

public class ChatViewModelFactory implements ViewModelProvider.Factory {

    private String currentUserId;
    private String otherUserId;

    public ChatViewModelFactory(String currentUserId, String otherUserId) {
        this.currentUserId = currentUserId;
        this.otherUserId = otherUserId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ChatViewModel(currentUserId, otherUserId);
    }
}
