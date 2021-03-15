package com.example.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class BroadcastReceiverExp extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            networkCallback(context);
        }
    }

    private void networkCallback(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest networkRequest = new NetworkRequest.Builder().build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(){
                @Override
                public void onAvailable(@NonNull Network network) {
                    super.onAvailable(network);
                    Toast.makeText(context, "Network connected", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLost(@NonNull Network network) {
                    super.onLost(network);
                    Toast.makeText(context, "Network disconnected", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            connectivityManager.registerNetworkCallback(networkRequest,new ConnectivityManager.NetworkCallback(){
                @Override
                public void onAvailable(@NonNull Network network) {
                    super.onAvailable(network);
                    Toast.makeText(context, "Network connected", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLost(@NonNull Network network) {
                    super.onLost(network);
                    Toast.makeText(context, "Network disconnected", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


}
