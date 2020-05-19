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

    RecyclerView recyclerView;
    ProductAdapter adapter;
    List<Product> productList;

    String msDay = null;
    String msDate = null;
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
        msDate = getIntent().getStringExtra("sDate");
        mBreakfast = getIntent().getBooleanExtra("breakfast", false);
        mLunch = getIntent().getBooleanExtra("lunch", false);
        mDinner = getIntent().getBooleanExtra("dinner", false);
        mAllMeals = getIntent().getBooleanExtra("all_meals", false);

        productList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true); // try removing it!

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (mAllMeals) {
            productList.add(new Product(BREAKFAST, "Breakfast"));
            productList.add(new Product(LUNCH, "Lunch"));
            productList.add(new Product(DINNER, "Dinner"));
        }
        else {
            if (mBreakfast)
                productList.add(new Product(BREAKFAST, "Breakfast"));
            if (mLunch)
                productList.add(new Product(LUNCH, "Lunch"));
            if (mDinner)
                productList.add(new Product(DINNER, "Dinner"));
        }

        adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        register_mess_btn = findViewById(R.id.register_mess);
        register_mess_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                ArrayList<String> registration_dates = new ArrayList<>();
                if (msDate != null)
                    registration_dates.add(msDate);
                else if (msDay != null) {
                    int nDayOfWeek = day_num_map.get(msDay);
                    Calendar current_date = Calendar.getInstance();
                    int current_month = current_date.get(Calendar.MONTH);

                    Calendar next_date = Calendar.getInstance();
                    next_date.add(Calendar.DAY_OF_MONTH, (nDayOfWeek + 7 - next_date.get(Calendar.DAY_OF_WEEK)));

                    int days_gap = (int) Duration.between(current_date.toInstant(), next_date.toInstant()).toDays();
                    if (days_gap <= 2)
                        next_date.add(Calendar.DAY_OF_MONTH, 7);

                    int updated_month = next_date.get(Calendar.MONTH);
                    while (updated_month == current_month) {
                        String sDayNum = String.valueOf(next_date.get(Calendar.DAY_OF_MONTH));
                        if (sDayNum.length() < 2)
                            sDayNum = "0" + sDayNum;
                        String sMonth = String.valueOf(next_date.get(Calendar.MONTH)+1);  // months start from 0
                        if (sMonth.length() < 2)
                            sMonth = "0" + sMonth;
                        registration_dates.add(sDayNum + "_" + sMonth + "_" + next_date.get(Calendar.YEAR));

                        next_date.add(Calendar.DAY_OF_MONTH, 7);
                        updated_month = next_date.get(Calendar.MONTH);
                    }
//                    Toast.makeText(ChooseMessActivity.this, "Dates: " + dates_added, Toast.LENGTH_SHORT).show();
                }

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser mCurrentUser = mAuth.getCurrentUser();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference user_doc = db.collection("users").document(mCurrentUser.getEmail());

                for (String date: registration_dates) {
                    Map<String, Object> meal_mess = new HashMap<>();
                    for (Product product: productList) {
                        if (product.getRegistered_mess() != null)
                            meal_mess.put(product.getMeal(), product.getRegistered_mess());
                    }
                    user_doc.collection("mess_registrations")
                            .document(date)
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
