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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mas.samplevideomeeting.R;
import com.mas.samplevideomeeting.utilities.Constants;
import com.mas.samplevideomeeting.utilities.PreferenceManager;

public class SignInActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private MaterialButton btnSignIn;
    private ProgressBar pbSignIn;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        preferenceManager = new PreferenceManager(getApplicationContext());

        if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        findViewById(R.id.tvSignUp).setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SignUpActivity.class)));

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        pbSignIn = findViewById(R.id.pbSignIn);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(v -> {
            if (etEmail.getText().toString().trim().isEmpty()) {
                Toast.makeText(SignInActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
                Toast.makeText(SignInActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
            } else if (etPassword.getText().toString().trim().isEmpty()) {
                Toast.makeText(SignInActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
            } else {
                signIn();
            }
        });
    }

    private void signIn() {
        btnSignIn.setVisibility(View.INVISIBLE);
        pbSignIn.setVisibility(View.VISIBLE);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL, etEmail.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD, etPassword.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                        preferenceManager.putString(Constants.KEY_FIRST_NAME, documentSnapshot.getString(Constants.KEY_FIRST_NAME));
                        preferenceManager.putString(Constants.KEY_LAST_NAME, documentSnapshot.getString(Constants.KEY_LAST_NAME));
                        preferenceManager.putString(Constants.KEY_EMAIL, documentSnapshot.getString(Constants.KEY_EMAIL));

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        pbSignIn.setVisibility(View.INVISIBLE);
                        btnSignIn.setVisibility(View.VISIBLE);
                        Toast.makeText(SignInActivity.this, "Unable to sign in", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}