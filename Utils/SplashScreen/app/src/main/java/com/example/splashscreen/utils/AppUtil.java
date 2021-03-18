package com.example.splashscreen.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

public class AppUtil {
    public static boolean isNetworkAvailable(Context context){
        if(context == null){
            return false;
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager == null){
            return false;
        }

        Network network = connectivityManager.getActiveNetwork();
        if(network == null){
            return false;
        }

        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
        return capabilities != null &&
                ((capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                ||(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)));
    }
}
