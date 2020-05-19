package com.example.mymess;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import static java.time.Duration.between;

public class DayDateActivity extends OptionsMenuActivity {
    private Toolbar mtoolbar;

    Button mSelect_meal_btn;
    String msDay = null;
    String msDate = null;
    String msMonthYear = null;
    CalendarView mCalenderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_date);

        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        final Spinner days = (Spinner) findViewById(R.id.days);
        mCalenderView = (CalendarView) findViewById(R.id.calendar);


        // Day Selection
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DayDateActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.days));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        days.setAdapter(adapter);
        days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                msDay = days.getSelectedItem().toString();
                if (!msDay.equals("DAY")) {
                    Calendar date = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM_y");
                    String sMonthYear = dateFormat.format(date.getTimeInMillis());

                    Intent intent = new Intent(DayDateActivity.this, SelectMealActivity.class);
                    intent.putExtra("sDay", msDay);
                    intent.putExtra("sMonthYear", sMonthYear);
                    startActivity(intent);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Date Selection
        mCalenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar selected_date = Calendar.getInstance();
                selected_date.set(year, month, dayOfMonth);

                Calendar current_date = Calendar.getInstance();
                if (selected_date.before(current_date)) {
                    Toast.makeText(DayDateActivity.this, "Past dates are invalid", Toast.LENGTH_LONG).show();
                    msDate = null;
                    days.setEnabled(true);
                } else {
                    int days_gap = (int) between(current_date.toInstant(), selected_date.toInstant()).toDays();
                    if (days_gap <= 2) {
                        Toast.makeText(DayDateActivity.this, "Minimum gap of 2 days is required", Toast.LENGTH_LONG).show();
                        msDate = null;
                        days.setEnabled(true);
                    } else {
                        days.setEnabled(false);
                        Calendar date = Calendar.getInstance();
                        date.set(year, month, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM_y");
                        String sMonthYear = dateFormat.format(date.getTimeInMillis());

                        String sDayNum = String.valueOf(dayOfMonth);
                        if (sDayNum.length() < 2)
                            sDayNum = "0" + sDayNum;
                        String sMonth = String.valueOf(month+1);  // months start from 0
                        if (sMonth.length() < 2)
                            sMonth = "0" + sMonth;
                        msDate =  sDayNum + "_" + sMonth + "_" + year;

                        Intent intent = new Intent(DayDateActivity.this, SelectMealActivity.class);
                        intent.putExtra("sDate", msDate);
                        intent.putExtra("sMonthYear", sMonthYear);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
