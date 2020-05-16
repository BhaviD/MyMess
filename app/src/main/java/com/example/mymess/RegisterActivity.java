package com.example.mymess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private Toolbar mtoolbar;

    EditText emailId, password, first_name, last_name;
    Button btnSignUp;
    DatabaseReference dbUser;

    FirebaseAuth mFirebaseAuth;

    TextView tvSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        dbUser = FirebaseDatabase.getInstance().getReference("users");

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.email);
        password = findViewById(R.id.password);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        btnSignUp = findViewById(R.id.button2);
        tvSignIn = findViewById(R.id.textView);

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
                                addUser(f_name, l_name, email);
                                startActivity(new Intent(RegisterActivity.this, DayMeal.class));
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

    private void addUser(String email, String f_name, String l_name) {
        String id = dbUser.push().getKey();
        User user = new User (id, email, f_name, l_name);
        dbUser.child(id).setValue(user);
    }
}