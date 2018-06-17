package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

import static com.udacity.gradle.builditbigger.utils.GetIpAddress.getIpAddress;

// Async Task that fetches joke String from the backend
// resource: https://gist.github.com/cesarferreira/ef70baa8d64f9753b4da#file-oneventlistener-java
public class JokerAsyncTask extends AsyncTask<Void, Void, String> {

    private static final String LOG_TAG = JokerAsyncTask.class.getSimpleName();


    // Interface for the callback listener
    public interface OnEventListener<String> {
        void onSuccess(String joke);
        void onFailure(Exception e);

    }


    private OnEventListener<String> mCallback;
    private Exception mException;

    // !!! Can't be a static variable so that it get's new values every time AsyncTask executes!!!
    private MyApi myApiService = null;
    private Context mContext;


    // Constructor of the AsyncTask
    public JokerAsyncTask(Context context, OnEventListener<String> callback) {
        mCallback = callback;
        mContext = context;
    }

    @Override
    protected String doInBackground(Void... params) {

        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(getIpAddress())
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });

            myApiService = builder.build();

            try {
                String jokeDisplayed = myApiService.jokerMe().execute().getJokes();

                Log.d(LOG_TAG, "Passed joke: " + jokeDisplayed);

                return jokeDisplayed;

            } catch (IOException e) {
                Log.e(LOG_TAG, "Exception: " + e.getMessage());
                return e.getMessage();
            }
        }

        return null;
    }



    @Override
    protected void onPostExecute(String result) {

        if (mCallback != null) {
            if (mException == null) {
                mCallback.onSuccess(result);
            } else {
                mCallback.onFailure(mException);
            }
        }
    }
}

