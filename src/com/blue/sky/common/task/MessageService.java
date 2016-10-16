package com.blue.sky.common.task;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.h5.game.detail.DetailActivity;
import com.blue.sky.h5.game.R;

/**
 * Created by Administrator on 2010/10/11.
 */
public class MessageService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i(">>>MessageService", "onCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.i(">>>MessageService", "onDestroy");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(">>>MessageService", "onStartCommand");
        //定义NotificationManager
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
        //定义通知栏展现的内容信息
        int icon = R.drawable.ic_launcher;
        CharSequence tickerText = "我的通知栏标题";
        long when = System.currentTimeMillis();
        //构建一个通知对象(需要传递的参数有三个,分别是图标,标题和 时间)
        Notification notification = new Notification(icon, tickerText, when);

        //定义下拉通知栏时要展现的内容信息
        Context context = getApplicationContext();
        CharSequence contentTitle = "我的通知栏标展开标题";
        CharSequence contentText = "我的通知栏展开详细内容";

        Intent notificationIntent = new Intent(this, DetailActivity.class);

        GameInfo item = new GameInfo();
        item.setId(60);
        item.setShortName("我的通知栏标展开标题");
        item.setSummery("我的通知栏展开详细内容");
        notificationIntent.putExtra("item", item);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, contentText,contentIntent);
        //点击后自动消失
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        //声音默认
        notification.defaults = Notification.DEFAULT_SOUND;
        //用mNotificationManager的notify方法通知用户生成标题栏消息通知
        mNotificationManager.notify(1, notification);

        return super.onStartCommand(intent, flags, startId);
    }


}
