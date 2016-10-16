package com.blue.sky.common.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 蓝天科技
 * BlueSky
 */
public class LoginBroadcastReceiver extends BroadcastReceiver {

    private MessageCallback messageCallback;
    public LoginBroadcastReceiver(MessageCallback messageCallback)
    {
        this.messageCallback = messageCallback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("LoginBroadcastReceiver",">>>receive LoginBroadcastReceiver message");
        this.messageCallback.onReceiveMessage(context,intent);
    }
}
