package com.example.mymess;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseMessActivity extends OptionsMenuActivity {
    private String TAG = "CHOOSE_MESS";

    final int BREAKFAST = 1;
    final int LUNCH = 2;
    final int DINNER = 3;

    HashMap<String, Integer> day_num_map  = new HashMap<String, Integer>() {{
        put("Sunday", 1);
        put("Monday", 2);
        put("Tuesday", 3);
        put("Wednesday", 4);
        put("Thursday", 5);
        put("Friday", 6);
        put("Saturday", 7);
    }};

    HashMap<String, Integer> month_num_map  = new HashMap<String, Integer>() {{
        put("Jan", 0);
        put("Feb", 1);
        put("Mar", 2);
        put("Apr", 3);
        put("May", 4);
        put("Jun", 5);
        put("Jul", 6);
        put("Aug", 7);
        put("Sep", 8);
        put("Oct", 9);
        put("Nov", 10);
        put("Dec", 11);
    }};

    RecyclerView recyclerView;
    ChooseMessProductAdapter adapter;
    List<ChooseMessProduct> productList;

    String msDay = null;
    String msStartDate = null, msEndDate = null;
    boolean mBreakfast = false, mLunch = false, mDinner = false, mAllMeals = false;

    Button register_mess_btn;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mess);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        msDay = getIntent().getStringExtra("sDay");
        msStartDate = getIntent().getStringExtra("sStartDate");
        msEndDate = getIntent().getStringExtra("sEndDate");
        mBreakfast = getIntent().getBooleanExtra("breakfast", false);
        mLunch = getIntent().getBooleanExtra("lunch", false);
        mDinner = getIntent().getBooleanExtra("dinner", false);
        mAllMeals = getIntent().getBooleanExtra("all_meals", false);

        productList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);     // try removing it!
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (mAllMeals) {
            productList.add(new ChooseMessProduct(BREAKFAST, "Breakfast"));
            productList.add(new ChooseMessProduct(LUNCH, "Lunch"));
            productList.add(new ChooseMessProduct(DINNER, "Dinner"));
        }
        else {
            if (mBreakfast)
                productList.add(new ChooseMessProduct(BREAKFAST, "Breakfast"));
            if (mLunch)
                productList.add(new ChooseMessProduct(LUNCH, "Lunch"));
            if (mDinner)
                productList.add(new ChooseMessProduct(DINNER, "Dinner"));
        }

        adapter = new ChooseMessProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        register_mess_btn = findViewById(R.id.register_mess);
        register_mess_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                ArrayList<String> registration_dates = new ArrayList<>();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MMM_y");
                String sTempDate;
                if (msStartDate != null) {
//                    Toast.makeText(ChooseMessActivity.this, "msStartDate is not null", Toast.LENGTH_SHORT).show();
                    Calendar temp_date = Calendar.getInstance();
                    Calendar end_date = Calendar.getInstance();
                    temp_date.clear();
                    end_date.clear();
                    String[] sDate = msStartDate.split("_");
                    String[] eDate = msEndDate.split("_");
                    temp_date.set(Integer.valueOf(sDate[2]), month_num_map.get(sDate[1]),Integer.valueOf(sDate[0]));
                    end_date.set(Integer.valueOf(eDate[2]), month_num_map.get(eDate[1]),Integer.valueOf(eDate[0]));

                    while (temp_date.compareTo(end_date) <= 0) {
                        sTempDate = dateFormat.format(temp_date.getTimeInMillis());
                        registration_dates.add(sTempDate);
                        temp_date.add(Calendar.DAY_OF_MONTH, 1);
                    }
                }
                else if (msDay != null) {
                    int nDayOfWeek = day_num_map.get(msDay);
                    Calendar current_date = Calendar.getInstance();
                    Calendar next_date = (Calendar) current_date.clone();
                    next_date.add(Calendar.DAY_OF_MONTH, (nDayOfWeek + 7 - next_date.get(Calendar.DAY_OF_WEEK)));

                    Calendar after_two_days = (Calendar) current_date.clone();
                    after_two_days.add(Calendar.DAY_OF_MONTH, 2);
                    if (next_date.compareTo(after_two_days) <= 0)
                        next_date.add(Calendar.DAY_OF_MONTH, 7);

                    while (current_date.get(Calendar.MONTH) == next_date.get(Calendar.MONTH)) {
                        sTempDate = dateFormat.format(next_date.getTimeInMillis());
                        registration_dates.add(sTempDate);
                        next_date.add(Calendar.DAY_OF_MONTH, 7);
                    }
                }

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser mCurrentUser = mAuth.getCurrentUser();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference user_mess_registrations = db.collection("users")
                                                                .document(mCurrentUser.getEmail())
                                                                .collection("mess_registrations");

                String month_year = "";
                for (String date: registration_dates) {
                    Map<String, Object> meal_mess = new HashMap<>();
                    for (ChooseMessProduct product: productList)
                        if (product.getRegistered_mess() != null)
                            meal_mess.put(product.getMeal(), product.getRegistered_mess() + " (" + product.getMeal().charAt(0) + ")");

                    month_year = date.substring(3);
                    user_mess_registrations.document(month_year)
                            .collection("registrations_by_date")
                            .document(date.substring(0, 2))
                            .set(meal_mess, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ChooseMessActivity.this, "Mess Registration Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ChooseMessActivity.this, WelcomeActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ChooseMessActivity.this, "Mess Registration Failed. Please try again", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}
