package com.udacity.gradle.builditbigger.utils;

import android.os.Build;
import android.util.Log;

import com.udacity.gradle.builditbigger.BuildConfig;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class GetIpAddress {

    private static final String LOG_TAG = GetIpAddress.class.getSimpleName();

    // - 10.0.2.2 is localhost's IP address in Android emulator
    private static final String EMULATOR_IP_ADDRESS = "10.0.2.2";
    private static final String LOCAL_SERVER_IP = "http://" + EMULATOR_IP_ADDRESS + ":8080/_ah/api/";



    /**
     * Method for checking if the tested device is an emulator
     * recourse: https://stackoverflow.com/a/21505193/8132331
     *
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


    /**
     * Method for getting the IP Address on the testing device whether emulator or real device
     *
     * @return String with the IP Address
     */
    public static String getIpAddress() {

        boolean isEmulator = checkIsEmulator();
        Log.d(LOG_TAG, "isEmulator= " + isEmulator);

        if (isEmulator) {
            return LOCAL_SERVER_IP;
        } else {
            // Get the Local IP Address of the machine for testing the backend server on a real devices
            // resource: http://disq.us/p/1dqd3fz
            Log.d(LOG_TAG, "Local machine IP Address: " + BuildConfig.LOCAL_API);
            return BuildConfig.LOCAL_API;
        }
    }

    /**
     * Method for getting the local IP Address of the device
     * <p>
     * TODO: Need to extract IP Address from the computer, not the device
     * <p>
     * resource: https://stackoverflow.com/a/10199498/8132331
     *
     * @return String with the IP address
     */
    private static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
                 enumeration.hasMoreElements(); ) {

                NetworkInterface networkInterface = enumeration.nextElement();
                for (Enumeration<InetAddress> enumIpAddress = networkInterface.getInetAddresses();
                     enumIpAddress.hasMoreElements(); ) {

                    InetAddress inetAddress = enumIpAddress.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {

                        // TODO: Extract the computer's IP Address
                        // This gets the IP of the device,
                        // but we need the IP Address of the computer!!!
                        String ip = inetAddress.getHostAddress();

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
