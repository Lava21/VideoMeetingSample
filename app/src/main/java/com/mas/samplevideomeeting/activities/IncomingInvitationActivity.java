package com.mas.samplevideomeeting.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mas.samplevideomeeting.R;
import com.mas.samplevideomeeting.utilities.Constants;

public class IncomingInvitationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_invitation);

        ImageView ivMeetingType = findViewById(R.id.ivMeetingType);
        String meetingType = getIntent().getStringExtra(Constants.REMOTE_MSG_MEETING_TYPE);

        if (meetingType != null) {
            if (meetingType.equals("video")) {
                ivMeetingType.setImageResource(R.drawable.ic_round_videocam_24);
            }
        }

        TextView tvFirstChar = findViewById(R.id.tvFirstChar);
        TextView tvUsername = findViewById(R.id.tvUsername);
        TextView tvEmail = findViewById(R.id.tvEmail);

        String firstName = getIntent().getStringExtra(Constants.KEY_FIRST_NAME);
        if (firstName != null) {
            tvFirstChar.setText(firstName.substring(0, 1));
        }

        tvUsername.setText(String.format(
                "%s %s",
                firstName,
                getIntent().getStringArrayExtra(Constants.KEY_LAST_NAME)
        ));

        tvEmail.setText(getIntent().getStringExtra(Constants.KEY_EMAIL));
    }
}