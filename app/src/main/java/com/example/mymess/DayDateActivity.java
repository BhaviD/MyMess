package com.example.mymess;

import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class DayDateActivity extends OptionsMenuActivity {
    private Toolbar mtoolbar;

    String msDay = null;
    String msStartDate = null, msEndDate = null;
    CheckBox mBreakfast, mLunch, mDinner, mAllMeals;

    private Button mChooseDates;
    private Button mChooseMess;
    private TextView mDayDateTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_date);

        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        mBreakfast = (CheckBox) findViewById(R.id.breakfast);
        mLunch = (CheckBox) findViewById(R.id.lunch);
        mDinner = (CheckBox) findViewById(R.id.dinner);
        mAllMeals = (CheckBox) findViewById(R.id.all_meals);
        mDayDateTv = findViewById(R.id.day_date_tv);
        mChooseDates = findViewById(R.id.choose_dates);
        mChooseMess = findViewById(R.id.choose_mess);

        final Spinner days = (Spinner) findViewById(R.id.days);
        final Calendar current_date = Calendar.getInstance();

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
                if (msDay.equals("Choose Day"))
                    msDay = null;
                else {
                    Calendar date = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM_y");
                    mDayDateTv.setText(msDay);
                    msStartDate = msEndDate = null;
                    mChooseDates.setEnabled(false);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Dates Range Selection
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        long two_days_fwd = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_MONTH, -2);

        long this_month = calendar.getTimeInMillis();
        calendar.add(Calendar.MONTH, 1);
        long next_month = calendar.getTimeInMillis();

        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setStart(this_month);
        constraintBuilder.setEnd(next_month);
        constraintBuilder.setValidator(DateValidatorPointForward.from(two_days_fwd));

        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("SELECT A DATE RANGE");
        builder.setCalendarConstraints(constraintBuilder.build());
        final MaterialDatePicker materialDatePicker = builder.build();

        mChooseDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                String date_range = materialDatePicker.getHeaderText();

                String[] dates = date_range.split(" " + (char)8211 + " ");      // On Emulator: MMM d,  On Phone: d MMM
                String[] start_date = dates[0].split(" ");
                String[] end_date = dates[1].split(" ");

                String display_date_range;
                if (start_date[0].charAt(0) < '1' || '9' < start_date[0].charAt(0)) {
                    String temp = start_date[0];
                    start_date[0] = start_date[1];
                    start_date[1] = temp;

                    temp = end_date[0];
                    end_date[0] = end_date[1];
                    end_date[1] = temp;
                }
                if (start_date[0].length() == 1)
                    start_date[0] = "0" + start_date[0];
                if (end_date[0].length() == 1)
                    end_date[0] = "0" + end_date[0];
                String current_year = String.valueOf(current_date.get(Calendar.YEAR));

                msStartDate = start_date[0] + "_" + start_date[1];
                msEndDate = end_date[0] + "_" + end_date[1];
                if (start_date.length < 3) {
                    msStartDate += "_" + current_year;
                    msEndDate += "_" + current_year;
                    display_date_range = start_date[0] + " " + start_date[1] + "  -  " + end_date[0] + " " + end_date[1];
                } else {
                    msStartDate += "_" + start_date[2];
                    msEndDate += "_" + end_date[2];
                    display_date_range = start_date[0] + " " + start_date[1] + " " + start_date[2] + "  -  " + end_date[0] + " " + end_date[1] + " " + end_date[2];
                }
                mDayDateTv.setText(display_date_range);
                msDay = null;
                days.setEnabled(false);
            }
        });

        mChooseMess = findViewById(R.id.choose_mess);
        mChooseMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(ChooseMessActivity.class);
            }
        });
    }

    public void mealClicked(View v) {
        if (!mBreakfast.isChecked() || !mLunch.isChecked() || !mDinner.isChecked())
            mAllMeals.setChecked(false);
        else
            mAllMeals.setChecked(true);
    }

    public void allMealsClicked (View v) {
        boolean set_all_checked = false;
        if (mAllMeals.isChecked())
            set_all_checked = true;

        mBreakfast.setChecked(set_all_checked);
        mLunch.setChecked(set_all_checked);
        mDinner.setChecked(set_all_checked);
    }

    private void gotoActivity(Class goto_class) {
        if (msDay == null && msStartDate == null)
            Toast.makeText(DayDateActivity.this, "Please choose day or date(s)", Toast.LENGTH_SHORT).show();
        else if (!mBreakfast.isChecked() && !mLunch.isChecked() && !mDinner.isChecked() && !mAllMeals.isChecked())
            Toast.makeText(DayDateActivity.this, "Please select a meal", Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(DayDateActivity.this, goto_class);
            if (msDay != null)
                intent.putExtra("sDay", msDay);
            else if (msStartDate != null) {
                intent.putExtra("sStartDate", msStartDate);
                intent.putExtra("sEndDate", msEndDate);
            }

            if (mAllMeals.isChecked())
                intent.putExtra("all_meals", mAllMeals.isChecked());
            else {
                intent.putExtra("breakfast", mBreakfast.isChecked());
                intent.putExtra("lunch", mLunch.isChecked());
                intent.putExtra("dinner", mDinner.isChecked());
            }
            startActivity(intent);
        }
    }
}
