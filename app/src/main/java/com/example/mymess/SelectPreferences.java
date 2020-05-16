package com.example.mymess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class SelectPreferences extends OptionsMenuActivity {
    private Toolbar mtoolbar;

    TextView heading, heading_label;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_preferences);

        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        heading_label = (TextView) findViewById(R.id.headingLabel2);
        heading_label.setText(getIntent().getStringExtra("DAY") + " | " + getIntent().getStringExtra("MEAL"));

        btn = (Button) findViewById(R.id.foodPreferencesButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                String categoryChoices = "";
                boolean spicyChecked = ((CheckBox) findViewById(R.id.checkBoxSpicy)).isChecked();
                boolean healthyChecked = ((CheckBox) findViewById(R.id.checkBoxHealthy)).isChecked();
                boolean fibrousChecked = ((CheckBox) findViewById(R.id.checkBoxFibrous)).isChecked();
                boolean friedChecked = ((CheckBox) findViewById(R.id.checkBoxFried)).isChecked();
                if (spicyChecked) {
                    categoryChoices += "Spicy, ";
                    count += 1;
                }
                if (healthyChecked) {
                    categoryChoices += "Healthy, ";
                    count += 1;
                }
                if (fibrousChecked) {
                    categoryChoices += "Fibrous, ";
                    count += 1;
                }
                if (friedChecked) {
                    categoryChoices += "Fried, ";
                    count += 1;
                }
                if (count > 0) {
                    categoryChoices = categoryChoices.substring(0, categoryChoices.length() - 2);
                } else {
                    categoryChoices = "None";
                }
                Intent intent = new Intent(SelectPreferences.this, ShowPreferences.class);
                intent.putExtra("CATEGORIES", categoryChoices);
//                intent.putExtra("DESCRIPTION", description);
                startActivity(intent);
            }
        });
    }
}
