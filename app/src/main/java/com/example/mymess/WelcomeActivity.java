package com.example.mymess;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WelcomeActivity extends OptionsMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView full_name = findViewById(R.id.full_name);
        final TextView current_date = findViewById(R.id.current_date);
        final TextView breakfast_mess = findViewById(R.id.breakfast_mess);
        final TextView lunch_mess = findViewById(R.id.lunch_mess);
        final TextView dinner_mess = findViewById(R.id.dinner_mess);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mCurrentUser = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Calendar date = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMM y");
        current_date.setText(dateFormat.format(date.getTimeInMillis()));

        DocumentReference user_doc = db.collection("users").document(mCurrentUser.getEmail());
        user_doc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                full_name.setText(documentSnapshot.getString("first_name") + " " + documentSnapshot.getString("last_name"));
            }
        });

        DocumentReference mess_registration = db.collection("users")
                                                .document(mCurrentUser.getEmail())
                                                .collection("mess_registrations")
                                                .document("19_05_2020");

        mess_registration.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                breakfast_mess.setText(documentSnapshot.getString("Breakfast"));
                lunch_mess.setText(documentSnapshot.getString("Lunch"));
                dinner_mess.setText(documentSnapshot.getString("Dinner"));
            }
        });
    }
}
