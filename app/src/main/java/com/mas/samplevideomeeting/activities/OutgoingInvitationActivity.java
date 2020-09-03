package com.mas.samplevideomeeting.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mas.samplevideomeeting.R;
import com.mas.samplevideomeeting.model.User;

public class OutgoingInvitationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outgoing_invitation);

        ImageView ivMeetingType = findViewById(R.id.ivMeetingType);
        String meetingType = getIntent().getStringExtra("type");

        if (meetingType != null) {
            if (meetingType.equals("video")) {
                ivMeetingType.setImageResource(R.drawable.ic_round_videocam_24);
            }
        }

        TextView tvFirstChar = findViewById(R.id.tvFirstChar);
        TextView tvUsername = findViewById(R.id.tvUsername);
        TextView tvEmail = findViewById(R.id.tvEmail);

        User user = (User) getIntent().getSerializableExtra("user");
        if (user != null) {
            tvFirstChar.setText(user.firstName.substring(0, 1));
            tvUsername.setText(String.format("%s %s", user.firstName, user.lastName));
            tvEmail.setText(user.email);
        }

        ImageView ivStopInvitation = findViewById(R.id.ivStopInvitation);
        ivStopInvitation.setOnClickListener(v -> onBackPressed());
    }
}