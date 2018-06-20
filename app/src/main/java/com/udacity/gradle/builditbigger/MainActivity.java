package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.joker.JokeLibraryActivity;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String JOKE_KEY = "joke";

    private Button mJokeGivingButton;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJokeGivingButton = findViewById(R.id.joke_me_button);
        mLoadingIndicator = findViewById(R.id.progress_bar);

        mLoadingIndicator.setVisibility(View.GONE);

        mJokeGivingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadingIndicator.setVisibility(View.VISIBLE);
                tellJoke();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke() {

        JokerAsyncTask jokerAsyncTask = new JokerAsyncTask(getApplicationContext(),
                new JokerAsyncTask.OnEventListener<String>() {
                    @Override
                    public void onSuccess(String joke) {
                        // Hide the Progress Bar when the app is trying to retrieve the joke from the backend
                        mLoadingIndicator.setVisibility(View.GONE);

                        Intent intent = new Intent(getApplicationContext(), JokeLibraryActivity.class);
                        intent.putExtra(JOKE_KEY, joke);
                        startActivity(intent);

                        Log.d(LOG_TAG, "Passed joke: " + joke);
                    }

                    @Override
                    public void onFailure(Exception e) {

                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        jokerAsyncTask.execute();

    }
}
