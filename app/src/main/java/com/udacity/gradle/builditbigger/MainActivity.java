package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.joker.JokeLibraryActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String JOKE_KEY = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void tellJoke(View view) {

        JokerAsyncTask jokerAsyncTask = new JokerAsyncTask(getApplicationContext(),
                new JokerAsyncTask.OnEventListener<String>() {
                    @Override
                    public void onSuccess(String joke) {
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
