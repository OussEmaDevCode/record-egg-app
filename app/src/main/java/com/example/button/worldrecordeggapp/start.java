package com.example.button.worldrecordeggapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class start extends AppCompatActivity {
    boolean wasRunBefore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            SharedPreferences preferences = getSharedPreferences("intro", Context.MODE_PRIVATE);
            wasRunBefore = preferences.getBoolean("done", false);
            //Show info slider on first run
            if(wasRunBefore) {
                final Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                final Intent intent = new Intent(this, Intro.class);
                startActivity(intent);
                finish();
            }

    }
}
