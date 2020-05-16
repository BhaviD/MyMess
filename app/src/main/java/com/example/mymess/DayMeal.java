package com.example.mymess;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DayMeal extends OptionsMenuActivity {
    private Toolbar mtoolbar;

    Button btn;
    String day_val, meal_val;
    TextView heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_meal);

        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        heading = (TextView) findViewById(R.id.headingLabel);
        heading.setText("Hello " + getIntent().getStringExtra("NAME"));

        final Spinner mySpinner1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(DayMeal.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.days));
        myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner1.setAdapter(myAdapter1);

        mySpinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                day_val = mySpinner1.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Spinner mySpinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(DayMeal.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.meals));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner2.setAdapter(myAdapter2);

        mySpinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                meal_val = mySpinner2.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn = (Button) findViewById(R.id.foodPreferencesButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DayMeal.this, SelectPreferences.class);
                intent.putExtra("DAY", day_val);
                intent.putExtra("MEAL", meal_val);
                if (day_val.equals("DAY")) {
                    Toast.makeText(DayMeal.this, "Select Day", Toast.LENGTH_LONG).show();
                } else if (meal_val.equals("MEAL")) {
                    Toast.makeText(DayMeal.this, "Select Meal", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(intent);
                }
            }
        });
    }

}
