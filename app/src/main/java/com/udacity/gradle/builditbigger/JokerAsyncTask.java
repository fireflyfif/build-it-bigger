package com.udacity.gradle.builditbigger;

import android.companion.WifiDeviceFilter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.text.format.Formatter;
import android.util.Log;
import android.util.Pair;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class JokerAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {

    // - 10.0.2.2 is localhost's IP address in Android emulator
    private static final String EMULATOR_IP_ADDRESS = "http://10.0.2.2:8080/_ah/api/";

    /**
     * Method for checking if the tested device is an emulator
     *
     * recourse: https://stackoverflow.com/a/21505193/8132331
     * @return boolean whether the build is being performed on an Emulator
     */
    public static boolean checkIsEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }


    public static String getIpAddress() {

        boolean isEmulator = checkIsEmulator();
        Log.d(LOG_TAG, "isEmulator= " + isEmulator);

        if (isEmulator) {
            return EMULATOR_IP_ADDRESS;
        } else {
            return getLocalIpAddress();
        }


    }

    /**
     * Method for getting the local IP Address of the used
     *
     * resource: https://stackoverflow.com/a/10199498/8132331
     * @return String with the IP address
     */
    private static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
                 enumeration.hasMoreElements();) {

                NetworkInterface networkInterface = enumeration.nextElement();
                for (Enumeration<InetAddress> enumIpAddress = networkInterface.getInetAddresses();
                        enumIpAddress.hasMoreElements();) {

                    InetAddress inetAddress = enumIpAddress.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());

                        Log.i(LOG_TAG, "IP address = " + ip);
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e(LOG_TAG, ex.toString());
        }
        return null;
    }

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
                    .setRootUrl(getIpAddress())
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });

            myApiService = builder.build();

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

