package com.blue.sky.common.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.blue.sky.common.utils.Constants;

/**
 * Created by Administrator on 2014/9/6.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {


    private static final String TAG = NetworkChangeReceiver.class.getSimpleName();
    private MessageCallback messageCallback;
    private long lastTime = System.currentTimeMillis();


    public NetworkChangeReceiver()
    {

    }

    public NetworkChangeReceiver(MessageCallback messageCallback)
    {
        this.messageCallback = messageCallback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        long currentTime = System.currentTimeMillis();
        if(currentTime-lastTime>1000){
            lastTime = currentTime;
            Log.i(TAG, "网络状态改变");
            //获得网络连接服务
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // State state = connManager.getActiveNetworkInfo().getState();
            // 获取WIFI网络连接状态
            NetworkInfo.State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            // 判断是否正在使用WIFI网络
            if (NetworkInfo.State.CONNECTED == state) {
                intent.putExtra(Constants.NET_STATE, Constants.NET_WIFI);
            }
            // 获取GPRS网络连接状态
            state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            // 判断是否正在使用GPRS网络
            if (NetworkInfo.State.CONNECTED == state) {
                intent.putExtra(Constants.NET_STATE, Constants.NET_MOBILE);
            }
            this.messageCallback.onReceiveMessage(context,intent);
        }
    }
}
