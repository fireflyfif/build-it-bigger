package com.udacity.gradle.builditbigger;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.joker.JokeLibraryActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.AllOf.allOf;

// Test checking if the AsyncTask provides jokes
// resource help: http://marksunghunpark.blogspot.com/2015/05/how-to-test-asynctask-in-android.html
@RunWith(AndroidJUnit4.class)
public class JokerAsyncTaskTest {

    @Rule
    public IntentsTestRule<MainActivity> activityRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void clickButton_GetsJokes() {

        // Wait the backend to be loaded first, before starting the test
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.joke_me_button)).perform(click());

        intended(allOf(
                hasComponent(JokeLibraryActivity.class.getName()),
                hasExtra(equalTo(MainActivity.JOKE_KEY), notNullValue())
        ));

        onView(withId(R.id.display_joke_tv)).check(matches(not(withText(""))));

    }
}
