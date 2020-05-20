package com.example.mymess;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        final Calendar date = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMM y");
        current_date.setText(dateFormat.format(date.getTimeInMillis()));

        DocumentReference user_doc = db.collection("users").document(mCurrentUser.getEmail());
        user_doc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                full_name.setText(documentSnapshot.getString("first_name") + " " + documentSnapshot.getString("last_name"));
            }
        });

        dateFormat = new SimpleDateFormat("MMM_y");
        String sMonthYear = dateFormat.format(date.getTimeInMillis());

        dateFormat = new SimpleDateFormat("dd");
        String fetch_date = dateFormat.format(date.getTimeInMillis());

        DocumentReference mess_registration = db.collection("users")
                                                .document(mCurrentUser.getEmail())
                                                .collection("mess_registrations")
                                                .document(sMonthYear)
                                                .collection("registrations_by_date")
                                                .document(fetch_date);

        mess_registration.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    String b_mess = documentSnapshot.getString("Breakfast");
                    String l_mess = documentSnapshot.getString("Lunch");
                    String d_mess = documentSnapshot.getString("Dinner");
                    if (b_mess != null)
                        breakfast_mess.setText(b_mess.substring(0, b_mess.length()-4));
                    if (l_mess != null)
                        lunch_mess.setText(l_mess.substring(0, l_mess.length()-4));
                    if (d_mess != null)
                        dinner_mess.setText(d_mess.substring(0, d_mess.length()-4));

                    if (b_mess == null || l_mess == null || d_mess == null)
                        Toast.makeText(WelcomeActivity.this, "Mess Registrations are not done", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
