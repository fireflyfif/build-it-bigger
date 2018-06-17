package com.udacity.gradle.builditbigger.utils;

import android.os.Build;
import android.text.format.Formatter;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class GetIpAddress {

    private static final String LOG_TAG = GetIpAddress.class.getSimpleName();

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
     * Method for getting the local IP Address of the user
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
}
