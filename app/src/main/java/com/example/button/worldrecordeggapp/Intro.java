package com.example.button.worldrecordeggapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;


public class Intro extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addFragment(new Step.Builder().setTitle("World record egg!")
                .setContent("we all know the famous egg on the instagram with over 41.1m likes")
                .setBackgroundColor(Color.parseColor("#00BCA3"))
                .setDrawable(R.drawable.ic_egg)
                .build());

        addFragment(new Step.Builder().setTitle("Create your own egg!")
                .setContent("You can put your face inside the egg and create your own custom one")
                .setBackgroundColor(Color.parseColor("#008577"))
                .setDrawable(R.drawable.ic_boy_broad_smile)
                .build());

        addFragment(new Step.Builder().setTitle("Share it on social media!")
                .setContent("You can share it with all your friends on almost every social media")
                .setBackgroundColor(Color.parseColor("#00574B"))
                .setDrawable(R.drawable.ic_social_media)
                .build());
    }

    @Override
    public void finishTutorial() {
        super.finishTutorial();
        SharedPreferences prefs = getSharedPreferences("intro", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean("done", true);
        editor.apply();

        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
