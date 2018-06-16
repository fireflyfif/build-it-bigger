package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class JokerAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {

    private static final String LOG_TAG = JokerAsyncTask.class.getSimpleName();

    // Interface for the callback listener
    public interface OnEventListener<String> {
        void onSuccess(String joke);
        void onFailure(Exception e);
    }


    private OnEventListener<String> mCallback;
    private Exception mException;

    private static MyApi myApiService = null;
    private Context mContext;


    // Constructor of the AsyncTask
    public JokerAsyncTask(Context context, OnEventListener<String> callback) {
        mCallback = callback;
        mContext = context;
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://192.168.11.150:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });

            myApiService = builder.build();

            //mContext = params[0].first;

            try {
                String jokeDisplayed = myApiService.jokerMe().execute().getData();
                Log.d(LOG_TAG, "Passed joke: " + jokeDisplayed);

                return jokeDisplayed;

            } catch (IOException e) {
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

        // Toast.makeText(mContext, result, Toast.LENGTH_LONG).show();
    }
}

