package com.mas.samplevideomeeting.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mas.samplevideomeeting.listeners.UsersListener;
import com.mas.samplevideomeeting.model.User;
import com.mas.samplevideomeeting.R;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder>{

    private List<User> users;
    private UsersListener usersListener;

    public UsersAdapter(List<User> users, UsersListener usersListener) {
        this.users = users;
        this.usersListener = usersListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_user,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        TextView tvFirstChar, tvUsername, tvEmail;
        ImageView ivAudioMeeting, ivVideoMeeting;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFirstChar = itemView.findViewById(R.id.tvFirstChar);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            ivAudioMeeting = itemView.findViewById(R.id.ivAudioMeeting);
            ivVideoMeeting = itemView.findViewById(R.id.ivVideoMeeting);
        }

        void setUserData(User user) {
            tvFirstChar.setText(user.firstName.substring(0,1 ));
            tvUsername.setText(String.format("%s %s", user.firstName, user.lastName));
            tvEmail.setText(user.email);

            ivAudioMeeting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    usersListener.initiateAudioMeeting(user);
                }
            });

            ivVideoMeeting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    usersListener.initiateVideoMeeting(user);
                }
            });
        }
    }
}
