package com.example.android.joker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeLibraryActivity extends AppCompatActivity {

    private static final String JOKE_KEY = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_library);

        TextView displayedJokeFromJavaLib = findViewById(R.id.display_joke_tv);

        Intent receiveIntent = getIntent();
        if (receiveIntent.hasExtra(JOKE_KEY)) {
            String joke = receiveIntent.getStringExtra(JOKE_KEY);

            displayedJokeFromJavaLib.setText(joke);
        }

    }
}
