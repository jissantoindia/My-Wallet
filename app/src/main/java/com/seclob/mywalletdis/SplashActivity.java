package com.seclob.mywalletdis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        sharedPreferences = getSharedPreferences("MYSCBCL", MODE_PRIVATE);
        FirebaseMessaging.getInstance().subscribeToTopic("global");


        if(sharedPreferences.getString("username","").length()>2 && sharedPreferences.getString("password","").length()>2)
        {

            Intent intents = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intents);
            finish();

        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intents = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intents);
                    finish();
                }

            }, 4000);
        }

    }
}