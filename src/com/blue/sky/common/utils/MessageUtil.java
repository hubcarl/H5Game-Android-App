package com.blue.sky.common.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.h5.game.MyApplication;
import com.blue.sky.h5.game.detail.DetailActivity;
import com.blue.sky.h5.game.R;

/**
 * Created by Administrator on 2010/10/11.
 */
public class MessageUtil {


    public static Vibrator vibrator;
    private static MediaPlayer mediaPlayer = new MediaPlayer();

    /**
     * 振动通知
     */
    private static void notificationVibrator(Context context) {
        if (vibrator == null) {
            vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        }
        vibrator.vibrate(new long[]{500, 50, 50, 1000, 50}, -1);
    }

    /**
     * 铃声通知
     */
    private static void notificationRing(Context context) {

        if (mediaPlayer.isPlaying()) return;

        try {
            // 这里是调用系统自带的铃声
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            //
            mediaPlayer.stop();
            mediaPlayer.reset();
            //
            mediaPlayer.setDataSource(context, uri);

            mediaPlayer.prepare();
        } catch (Exception e) {
            Log.e("notificationRing", e.toString());
        }
        mediaPlayer.start();
    }

    public static void notification(GameInfo item) {

        Context context = MyApplication.getInstance().getApplicationContext();
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
        //定义通知栏展现的内容信息
        int icon = R.drawable.ic_launcher;
        CharSequence tickerText = "[爱玩]" + item.getShortName();
        long when = System.currentTimeMillis();
        //构建一个通知对象(需要传递的参数有三个,分别是图标,标题和 时间)
        Notification notification = new Notification(icon, tickerText, when);

        //定义下拉通知栏时要展现的内容信息
        CharSequence contentTitle = item.getShortName();
        CharSequence contentText = item.getSummery();

        Intent notificationIntent = new Intent(context, DetailActivity.class);
        notificationIntent.putExtra("item", item);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
        //点击后自动消失
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        //声音默认
        notification.defaults = Notification.DEFAULT_SOUND;
        //用mNotificationManager的notify方法通知用户生成标题栏消息通知
        mNotificationManager.notify((int) System.currentTimeMillis() / 1000, notification);
        if (MyApplication.Cookies.getUserSetting().isShake()) {
            notificationVibrator(context);
        }
        if (MyApplication.Cookies.getUserSetting().isMusic()) {
            notificationRing(context);
        }
    }
}
