package com.example.mymess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

public class DayDateActivity extends OptionsMenuActivity {
    private Toolbar mtoolbar;

    Button mSelect_meal_btn;
    String msDay;
    Integer mYear = 0, mMonth = 0, mDay = 0;
    CalendarView mCalenderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_date);

        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        // Day Selection
        final Spinner days = (Spinner) findViewById(R.id.days);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DayDateActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.days));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        days.setAdapter(adapter);

        days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                msDay = days.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Date Selection
        mCalenderView = (CalendarView) findViewById(R.id.calendar);
        mCalenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month+1;   // since months start from 0 in android
                mDay = dayOfMonth;
            }
        });

        mSelect_meal_btn = (Button) findViewById(R.id.select_meal);
        mSelect_meal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DayDateActivity.this, SelectPreferences.class);
                if (!msDay.equals("DAY")) {
                    intent.putExtra("sDAY", msDay);
                    startActivity(intent);
                }
                else if (mYear != 0) {
                    intent.putExtra("YEAR", mYear);
                    intent.putExtra("MONTH", mMonth);
                    intent.putExtra("nDAY", mDay);
                    startActivity(intent);
                }
                else
                    Toast.makeText(DayDateActivity.this, "Select Either Day or Date", Toast.LENGTH_LONG).show();
            }
        });
    }
}
