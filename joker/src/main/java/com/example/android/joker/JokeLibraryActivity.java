package com.example.android.joker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class JokeLibraryActivity extends AppCompatActivity {

    private static final String JOKE_KEY = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_library);


        // Get the ActionBar to display the Up Button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinator_layout);
        TextView displayedJokeFromJavaLib = findViewById(R.id.display_joke_tv);
        ImageButton newJokeButton = findViewById(R.id.new_joke_button);

        Intent receiveIntent = getIntent();
        if (receiveIntent.hasExtra(JOKE_KEY)) {
            String joke = receiveIntent.getStringExtra(JOKE_KEY);

            displayedJokeFromJavaLib.setText(joke);
        }

        newJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackBar = Snackbar.make(coordinatorLayout, "Work in progress",
                        Snackbar.LENGTH_SHORT);
                snackBar.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
