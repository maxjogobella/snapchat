package com.example.myapplication.Adapter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private List<User> users = new ArrayList<>();
    private onUserClickListener onUserClickListener;

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public void setOnUserClickListener(UsersAdapter.onUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        String userInfo = String.format("%s %s", user.getUserName(), user.getAge());
        holder.textViewUserInfo.setText(userInfo);

        int backgroundResId;
        if (user.isStatus()) {
            backgroundResId = R.drawable.circle_green;
        } else {
            backgroundResId = R.drawable.circle_red;
        }
        Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(),
                backgroundResId);
        holder.viewOnline.setBackground(background);

        holder.itemView.setOnClickListener(v -> {
            if (onUserClickListener != null) {
                onUserClickListener.onUserClick(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewUserInfo;
        private final View viewOnline;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserInfo = itemView.findViewById(R.id.textViewUserInfo);
            viewOnline = itemView.findViewById(R.id.viewOnline);
        }
    }

    public interface onUserClickListener {
        void onUserClick(User user);
    }
}
