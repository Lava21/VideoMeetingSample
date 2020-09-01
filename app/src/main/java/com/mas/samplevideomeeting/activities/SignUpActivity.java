package com.mas.samplevideomeeting.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mas.samplevideomeeting.R;
import com.mas.samplevideomeeting.utilities.Constants;
import com.mas.samplevideomeeting.utilities.PreferenceManager;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etEmail, etPass, etPassConfirm;
    private MaterialButton btnSignUp;
    private ProgressBar pbSignUp;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        preferenceManager = new PreferenceManager(getApplicationContext());

        findViewById(R.id.ivBack).setOnClickListener(v -> onBackPressed());
        findViewById(R.id.tvSignIn).setOnClickListener(v -> onBackPressed());

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPassword);
        etPassConfirm = findViewById(R.id.etConfirmPassword);

        pbSignUp = findViewById(R.id.pbSignUp);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(v -> {
            if (etFirstName.getText().toString().trim().isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Enter first name", Toast.LENGTH_SHORT).show();
            } else if (etLastName.getText().toString().trim().isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Enter last name", Toast.LENGTH_SHORT).show();
            } else if (etEmail.getText().toString().trim().isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
                Toast.makeText(SignUpActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
            } else if (etPass.getText().toString().trim().isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
            } else if (etPassConfirm.getText().toString().isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Confirm your Password", Toast.LENGTH_SHORT).show();
            } else if (!etPassConfirm.getText().toString().equals(etPassConfirm.getText().toString())) {
                Toast.makeText(SignUpActivity.this, "Password & confirm password must be same", Toast.LENGTH_SHORT).show();
            } else {
                signUp();
            }
        });
    }

    private void signUp() {
        btnSignUp.setVisibility(View.INVISIBLE);
        pbSignUp.setVisibility(View.VISIBLE);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_FIRST_NAME, etFirstName.getText().toString());
        user.put(Constants.KEY_LAST_NAME, etLastName.getText().toString());
        user.put(Constants.KEY_EMAIL, etEmail.getText().toString());
        user.put(Constants.KEY_PASSWORD, etPass.getText().toString());

        database.collection(Constants.KEY_COLLECTION_USERS)
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                    preferenceManager.putString(Constants.KEY_FIRST_NAME, etFirstName.getText().toString());
                    preferenceManager.putString(Constants.KEY_LAST_NAME, etLastName.getText().toString());
                    preferenceManager.putString(Constants.KEY_EMAIL, etEmail.getText().toString());

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    pbSignUp.setVisibility(View.INVISIBLE);
                    btnSignUp.setVisibility(View.VISIBLE);
                    Toast.makeText(SignUpActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}