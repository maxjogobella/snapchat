package com.example.myapplication.Enum;

import com.example.myapplication.R;

public enum NavigationItemId {
    HOME(R.id.user_home),
    PROFILE(R.id.user_profile),
    SETTINGS(R.id.user_settings);

    private final int id;

    NavigationItemId(int id) {
        this.id = id;
    }

    public static NavigationItemId fromId(int id) {
        for (NavigationItemId itemId : values()) {
            if (itemId.id == id) {
                return itemId;
            }
        }
        throw new IllegalArgumentException("No enum constant with id" + id);
    }
}
