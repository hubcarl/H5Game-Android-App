package com.blue.sky.common.task;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.blue.sky.common.db.DBHelper;
import com.blue.sky.common.db.SQLScript;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.common.http.HttpAsyncResult;
import com.blue.sky.common.http.HttpRequestAPI;
import com.blue.sky.common.http.HttpRequestParam;
import com.blue.sky.common.utils.Constants;
import com.blue.sky.common.utils.MessageUtil;
import com.blue.sky.common.utils.NetWorkHelper;
import com.blue.sky.h5.game.MyApplication;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2010/10/11.
 */
public class TimerNotificationTask {


    private final Timer timer = new Timer();
    private TimerTask task;
    private HttpRequestParam latestRequestParam;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if(NetWorkHelper.isConnect(MyApplication.getInstance().getApplicationContext())){
                        final long maxId = DBHelper.getMaxId(MyApplication.getInstance().getApplicationContext(), SQLScript.TAB_GAME, "id");
                        Log.i(">>>TimerNotificationTask local maxId", maxId + "");
                        HttpRequestAPI.request(latestRequestParam, new HttpAsyncResult() {
                            public void process(List<GameInfo> tempList) {
                                Log.i(">>>TimerNotificationTask server response", "..........");
                                if (tempList != null && !tempList.isEmpty()) {
                                    Log.i(">>>TimerNotificationTask server response id", tempList.get(0).getId() + "");
                                    if (tempList.get(0).getId() > maxId) {
                                        Log.i(">>>TimerNotificationTask server response title", tempList.get(0).getShortName());
                                        MessageUtil.notification(tempList.get(0));
                                    }
                                } else {
                                    Log.i(">>>TimerNotificationTask server response is ", "empty");
                                }
                            }
                        });
                    }
                    break;
            }
        }
    };

    public TimerNotificationTask() {
        latestRequestParam = new HttpRequestParam(0, 1);
        latestRequestParam.setPageSize(1);

        task = new TimerTask() {
            @Override
            public void run() {
                if(NetWorkHelper.isConnect(MyApplication.getInstance().getApplicationContext())){
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
            }
        };
    }

    public void startTimerTask() {
        timer.schedule(task, 3000, 1000 * 300);
    }
}
