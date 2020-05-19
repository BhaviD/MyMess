package com.example.mymess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private String TAG = "REGISTER_USER";

    private EditText emailId, password, first_name, last_name;
    private FirebaseFirestore db;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = FirebaseFirestore.getInstance();

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.email);
        password = findViewById(R.id.password);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);

        Button btnSignUp = findViewById(R.id.button2);
        TextView tvSignIn = findViewById(R.id.textView);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final String email = emailId.getText().toString();
            String pwd = password.getText().toString();
            final String f_name = first_name.getText().toString();
            final String l_name = last_name.getText().toString();

            if (email.isEmpty() && pwd.isEmpty() && f_name.isEmpty() && l_name.isEmpty())
                Toast.makeText(RegisterActivity.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
            else if (email.isEmpty()) {
                emailId.setError("Please enter email id");
                emailId.requestFocus();
            } else if (pwd.isEmpty()) {
                password.setError("Please enter your password");
                password.requestFocus();
            } else if (f_name.isEmpty()) {
                emailId.setError("Please enter First Name");
                emailId.requestFocus();
            } else {
                mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "SignUp Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
                        } else {
                            addUser(email, f_name, l_name);
                            startActivity(new Intent(RegisterActivity.this, DayDateActivity.class));
                        }
                    }
                });
            }
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void addUser(String email_id, String f_name, String l_name) {
        Map<String, Object> user = new HashMap<>();
        user.put("first_name", f_name);
        user.put("last_name", l_name);
        user.put("email_id", email_id);
        db.collection("users")
            .document(email_id)
            .set(user)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "User Registration Successful!!");
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "User Registration Failed!!", e);
                }
            });
    }
}