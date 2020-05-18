package com.example.mymess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class SelectMealActivity extends OptionsMenuActivity {
    private Toolbar mtoolbar;

    Button food_options_btn;
    Integer mnDay, mnMonth, mnYear;
    String msDay;
    CheckBox breakfastCheckbox, lunchCheckbox, dinnerCheckbox, allCheckbox;
    boolean mBreakfastChecked = false, mLunchChecked = false, mDinnerChecked = false, mAllChecked = false;
    TextView skip_food_options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_meal);

        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        msDay = getIntent().getStringExtra("sDay");
        mnYear = getIntent().getIntExtra("nYear", 0);
        mnMonth = getIntent().getIntExtra("nMonth", 0);
        mnDay = getIntent().getIntExtra("nDay", 0);
        breakfastCheckbox = (CheckBox) findViewById(R.id.breakfast);
        lunchCheckbox = (CheckBox) findViewById(R.id.lunch);
        dinnerCheckbox = (CheckBox) findViewById(R.id.dinner);
        allCheckbox = (CheckBox) findViewById(R.id.all_meals);

        food_options_btn = (Button) findViewById(R.id.food_options);
        food_options_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBreakfastChecked = breakfastCheckbox.isChecked();
                mLunchChecked = lunchCheckbox.isChecked();
                mDinnerChecked = dinnerCheckbox.isChecked();
                mAllChecked = allCheckbox.isChecked();
                gotoActivity(ShowPreferences.class);
            }
        });

        skip_food_options = findViewById(R.id.skip_food_options);
        skip_food_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBreakfastChecked = breakfastCheckbox.isChecked();
                mLunchChecked = lunchCheckbox.isChecked();
                mDinnerChecked = dinnerCheckbox.isChecked();
                mAllChecked = allCheckbox.isChecked();
                gotoActivity(ChooseMessActivity.class);
            }
        });
    }

    public void mealClicked(View v) {
        if (allCheckbox.isChecked()) {
            if (!breakfastCheckbox.isChecked() || !lunchCheckbox.isChecked() || !dinnerCheckbox.isChecked())
                allCheckbox.toggle();
        } else {
            if (breakfastCheckbox.isChecked() && lunchCheckbox.isChecked() && dinnerCheckbox.isChecked())
                allCheckbox.toggle();
        }
    }

    public void allMealsClicked (View v) {
        if (allCheckbox.isChecked()) {
            if (!breakfastCheckbox.isChecked())
                breakfastCheckbox.toggle();
            if (!lunchCheckbox.isChecked())
                lunchCheckbox.toggle();
            if (!dinnerCheckbox.isChecked())
                dinnerCheckbox.toggle();
        } else {
            if (breakfastCheckbox.isChecked())
                breakfastCheckbox.toggle();
            if (lunchCheckbox.isChecked())
                lunchCheckbox.toggle();
            if (dinnerCheckbox.isChecked())
                dinnerCheckbox.toggle();
        }
    }

    private void gotoActivity(Class goto_class) {
        if (!mBreakfastChecked && !mLunchChecked && !mDinnerChecked && !mAllChecked)
            Toast.makeText(SelectMealActivity.this, "Please select a meal option", Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(SelectMealActivity.this, goto_class);
            if (msDay != null) {
                intent.putExtra("sDay", msDay);
            }
            else {
                intent.putExtra("nYear", mnYear);
                intent.putExtra("nMonth", mnMonth);
                intent.putExtra("nDay", mnDay);
            }
            if (mAllChecked)
                intent.putExtra("allChecked", mAllChecked);
            else {
                intent.putExtra("breakfastChecked", mBreakfastChecked);
                intent.putExtra("lunchChecked", mLunchChecked);
                intent.putExtra("dinnerChecked", mDinnerChecked);
            }
            startActivity(intent);
        }
    }
}
