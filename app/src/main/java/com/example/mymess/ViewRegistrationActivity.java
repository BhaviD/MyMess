package com.example.mymess;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public class ViewRegistrationActivity extends OptionsMenuActivity {

    Calendar mDateDisplayed;
    SimpleDateFormat mDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_registration);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDateFormat = new SimpleDateFormat("MMM y");
        mDateDisplayed = Calendar.getInstance();
        mDateDisplayed.set(Calendar.DAY_OF_MONTH, 1);

        final TextView month_year = findViewById(R.id.month_year);
        month_year.setText(mDateFormat.format(mDateDisplayed.getTimeInMillis()));
        show_mess_registration();

        final ImageView up_arrow = findViewById(R.id.up_arrow);
        up_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDateDisplayed.add(Calendar.MONTH, -1);
                month_year.setText(mDateFormat.format(mDateDisplayed.getTimeInMillis()));
                show_mess_registration();
            }
        });

        final ImageView down_arrow = findViewById(R.id.down_arrow);
        down_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDateDisplayed.add(Calendar.MONTH, 1);
                month_year.setText(mDateFormat.format(mDateDisplayed.getTimeInMillis()));
                show_mess_registration();
            }
        });
    }

    private void show_mess_registration() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mCurrentUser = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final Calendar temp_date = (Calendar) mDateDisplayed.clone();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM_y");
        String sMonthYear = dateFormat.format(mDateDisplayed.getTimeInMillis());

        final CollectionReference mess_registrations = db.collection("users")
                                                   .document(mCurrentUser.getEmail())
                                                   .collection("mess_registrations")
                                                   .document(sMonthYear)
                                                   .collection("registrations_by_date");

        Toast.makeText(ViewRegistrationActivity.this, "Fetching mess registrations", Toast.LENGTH_SHORT).show();
        mess_registrations.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(ViewRegistrationActivity.this, "Couldn't get mess registrations", Toast.LENGTH_SHORT).show();
                } else {
                    int display_tv_id;
                    TextView display_tv;
                    for (int r = 1; r <= 6; ++r) {
                        for (int c = 1; c <= 7; ++c) {
                            display_tv_id = getResources().getIdentifier("_" + r + c, "id", getPackageName());
                            display_tv = findViewById(display_tv_id);
                            display_tv.setText("");
                            display_tv.setBackground(null);
                        }
                    }

                    int r = 1, c = temp_date.get(Calendar.DAY_OF_WEEK);
                    for (QueryDocumentSnapshot document: task.getResult()) {
                        Map<String, Object> messes = document.getData();
                        String display_text = String.valueOf(temp_date.get(Calendar.DAY_OF_MONTH));
                        display_text += "\n\n" + (String) messes.get("Breakfast");
                        display_text += "\n\n" + (String) messes.get("Lunch");
                        display_text += "\n\n" + (String) messes.get("Dinner");
                        display_text += "\n";

                        display_tv_id = getResources().getIdentifier("_" + r + c, "id", getPackageName());
                        display_tv = findViewById(display_tv_id);
                        display_tv.setText(display_text);

                        int tv_border_id = getResources().getIdentifier("textview_border", "drawable", getPackageName());
                        Drawable tv_border = getResources().getDrawable(tv_border_id);
                        display_tv.setBackground(tv_border);

                        temp_date.add(Calendar.DAY_OF_MONTH, 1);
                        c = temp_date.get(Calendar.DAY_OF_WEEK);
                        if (c == 1)
                            ++r;
                    }
                }
            }
        });
    }
}
