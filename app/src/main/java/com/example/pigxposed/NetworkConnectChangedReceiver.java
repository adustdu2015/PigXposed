package com.example.pigxposed;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

public class NetworkConnectChangedReceiver extends BroadcastReceiver {
    public static final String TAG = "NetworkConnectChangedReceiver";
    @SuppressLint("LongLogTag")
    @Override
    public void onReceive(Context context, Intent intent) {
        //**判断当前的网络连接状态是否可用*/
        boolean isConnected = NetUtils.isConnected(context);
        Log.d(TAG, "onReceive: 当前网络 " + isConnected);
        EventBus.getDefault().post(new NetworkChangeEvent(isConnected));
    }
}
