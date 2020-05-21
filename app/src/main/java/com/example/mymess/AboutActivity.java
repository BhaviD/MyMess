package com.example.mymess;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends OptionsMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View githubLink = findViewById(R.id.github_link);
        githubLink.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                try {
                    Intent goToGithub = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://github.com/BhaviD/MyMess"));
                        goToGithub.addFlags(
                                Intent.FLAG_ACTIVITY_NO_HISTORY |
                                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                    );
                    startActivity(goToGithub);
                } catch (ActivityNotFoundException e) {
                    startActivity(
                            new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://github.com/BhaviD/MyMess")));
                }
            }
        });
    }
}
