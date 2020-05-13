package com.example.mymess;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowPreferences extends AppCompatActivity {

    TextView heading;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_preferences);

        heading = findViewById(R.id.headingLabel);
        heading.setText("You chose: " + getIntent().getStringExtra("CATEGORIES"));
    }
}
