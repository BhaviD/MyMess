package com.example.mymess;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class OptionsMenuActivity extends AppCompatActivity {
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent intentToWelcome = new Intent(this, WelcomeActivity.class);
                startActivity(intentToWelcome);
                return true;

            case R.id.view_mess_menus:
                Intent intentToViewMessMenu = new Intent(this, MessMenuActivity.class);
                startActivity(intentToViewMessMenu);
                return true;

            case R.id.change_mess_registration:
                Intent intentToDayDate = new Intent(this, DayDateActivity.class);
                startActivity(intentToDayDate);
                return true;

            case R.id.view_mess_registrations:
                Intent intentToViewRegistration = new Intent(this, ViewRegistrationActivity.class);
                startActivity(intentToViewRegistration);
                return true;

            case R.id.about:
                Intent intentToAbout = new Intent(this, AboutActivity.class);
                startActivity(intentToAbout);
                return true;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intentToMain = new Intent (this, MainActivity.class);
                startActivity(intentToMain);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
