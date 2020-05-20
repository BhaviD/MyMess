package com.example.mymess;

import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class SelectMealActivity extends OptionsMenuActivity {
    private Toolbar mtoolbar;

//    Button food_options_btn;
    String msDay = null;
    String msStartDate = null, msEndDate = null;
    String msMonthYear = null;
    CheckBox breakfast, lunch, dinner, all_meals;
    Button choose_mess;
//    TextView skip_food_options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_meal);

        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        msDay = getIntent().getStringExtra("sDay");
        msMonthYear = getIntent().getStringExtra("sMonthYear");
        msStartDate = getIntent().getStringExtra("sStartDate");
        msEndDate = getIntent().getStringExtra("sEndDate");
        breakfast = (CheckBox) findViewById(R.id.breakfast);
        lunch = (CheckBox) findViewById(R.id.lunch);
        dinner = (CheckBox) findViewById(R.id.dinner);
        all_meals = (CheckBox) findViewById(R.id.all_meals);

        choose_mess = findViewById(R.id.choose_mess);
        choose_mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(ChooseMessActivity.class);
            }
        });
    }

    public void mealClicked(View v) {
        if (!breakfast.isChecked() || !lunch.isChecked() || !dinner.isChecked())
            all_meals.setChecked(false);
        else
            all_meals.setChecked(true);
    }

    public void allMealsClicked (View v) {
        boolean set_all_checked = false;
        if (all_meals.isChecked())
            set_all_checked = true;

        breakfast.setChecked(set_all_checked);
        lunch.setChecked(set_all_checked);
        dinner.setChecked(set_all_checked);
    }

    private void gotoActivity(Class goto_class) {
        if (!breakfast.isChecked() && !lunch.isChecked() && !dinner.isChecked() && !all_meals.isChecked())
            Toast.makeText(SelectMealActivity.this, "Please select a meal", Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(SelectMealActivity.this, goto_class);
            intent.putExtra("sMonthYear", msMonthYear);
            if (msDay != null)
                intent.putExtra("sDay", msDay);
            else if (msStartDate != null) {
                intent.putExtra("sStartDate", msStartDate);
                intent.putExtra("sEndDate", msEndDate);
            }

            if (all_meals.isChecked())
                intent.putExtra("all_meals", all_meals.isChecked());
            else {
                intent.putExtra("breakfast", breakfast.isChecked());
                intent.putExtra("lunch", lunch.isChecked());
                intent.putExtra("dinner", dinner.isChecked());
            }
            startActivity(intent);
        }
    }
}
