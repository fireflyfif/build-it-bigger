package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

// Test checking if the AsyncTask provides jokes
// resource help: http://marksunghunpark.blogspot.com/2015/05/how-to-test-asynctask-in-android.html
@RunWith(AndroidJUnit4.class)
public class JokerAsyncTaskTest extends ApplicationTestCase {

    String jokeString = null;
    Exception error = null;
    CountDownLatch signal = null;

    public JokerAsyncTaskTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        signal.countDown();
    }

    @Test
    public void testAsyncTaskGetJokes() throws InterruptedException {

        JokerAsyncTask task = new JokerAsyncTask(getContext(),
                new JokerAsyncTask.OnEventListener<String>() {
                    @Override
                    public void onSuccess(String joke) {
                        jokeString = joke;
                        signal.countDown();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        error = e;
                    }
                });
        task.execute();
        signal.await();

        assertNull(error);
        assertNotNull("joke_null", jokeString);
        assertTrue("joke_string", !jokeString.isEmpty());

    }
}
