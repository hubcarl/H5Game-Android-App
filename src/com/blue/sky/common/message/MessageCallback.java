package com.blue.sky.common.message;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2014/8/9.
 */
public interface MessageCallback {
    void onReceiveMessage(Context context, Intent intent);
}
