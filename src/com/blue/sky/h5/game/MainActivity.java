package com.blue.sky.h5.game;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.blue.sky.common.config.AppConstants;
import com.blue.sky.common.message.ActionManager;
import com.blue.sky.common.message.MessageCallback;
import com.blue.sky.common.message.NetworkChangeReceiver;
import com.blue.sky.common.utils.Constants;
import com.blue.sky.common.utils.NetWorkHelper;
import com.blue.sky.h5.game.adapter.FragmentTabAdapter;
import com.blue.sky.h5.game.category.CategoryMainFragment;
import com.blue.sky.h5.game.center.CenterFragment;
import com.blue.sky.h5.game.home.HomeFragment;
import com.blue.sky.h5.game.rank.RankMainFragment;
import com.blue.sky.h5.game.search.SearchActivity;
import com.wandoujia.ads.sdk.Ads;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private TextView headerTitle;

    private RadioGroup tabGroup;
    private RadioButton mTab1;
    private RadioButton mTab2;
    private RadioButton mTab3;
    private RadioButton mTab4;

    private TextView rightButton;
    private TextView network_msg;

    private FragmentTabAdapter tabAdapter;
    public List<Fragment> fragments = new ArrayList<Fragment>();

    private NetworkChangeReceiver networkChangeReceiver;

    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sky_activity_main);
        initView();
        initMsgService();
        initAd();
        syncData();
        instance = this;
    }

    public static MainActivity getInstance() {
        return instance;
    }

    private void initView() {

        headerTitle = (TextView) findViewById(R.id.commonTitle);
        rightButton = (TextView) findViewById(R.id.rightButton);
        network_msg = (TextView) findViewById(R.id.network_msg);
        tabGroup = (RadioGroup) findViewById(R.id.tabGroup);

        fragments.add(new HomeFragment());
        fragments.add(new RankMainFragment());
        fragments.add(new CategoryMainFragment());
        fragments.add(new CenterFragment());

        tabAdapter = new FragmentTabAdapter(this, fragments, R.id.content_frame, tabGroup);
        tabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener() {
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                System.out.println("Extra---- " + index + " checked!!! ");
            }
        });

        mTab1 = (RadioButton) findViewById(R.id.tab_home);
        mTab2 = (RadioButton) findViewById(R.id.tab_code);
        mTab3 = (RadioButton) findViewById(R.id.tab_mobile);
        mTab4 = (RadioButton) findViewById(R.id.tab_web);

        mTab1.setOnClickListener(this);
        mTab2.setOnClickListener(this);
        mTab3.setOnClickListener(this);
        mTab4.setOnClickListener(this);

        rightButton.setOnClickListener(this);
        mTab1.performClick();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_home:
                headerTitle.setText("首页");
                break;
            case R.id.tab_code:
                headerTitle.setText("排行");
                break;
            case R.id.tab_mobile:
                headerTitle.setText("分类");
                break;
            case R.id.tab_web:
                headerTitle.setText("个人中心");
                break;
            case R.id.rightButton:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {

            }
        }
    };

    private void initMsgService() {
        networkChangeReceiver = new NetworkChangeReceiver(new MessageCallback() {
            @Override
            public void onReceiveMessage(Context context, Intent intent) {
                if (intent != null) {
                    int state = intent.getIntExtra(Constants.NET_STATE, -1);
                    Log.i(">>>HomeActivity NetworkChangeReceiver", "state:" + state);
                    if (state > 0) {
                        network_msg.setVisibility(View.GONE);
                        Message msg = new Message();
                        msg.what = 2;
                        mHandler.sendMessage(msg);
                    } else {
                        network_msg.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        registerReceiver(networkChangeReceiver, new IntentFilter(ActionManager.ACTION_CONNECTIVITY_CHANGE));

        // 启动定时拉取最新文章消息提醒
        //new TimerNotificationTask().startTimerTask();
        if(!NetWorkHelper.isConnect(this)){
            network_msg.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 同步数据
     */
    private void syncData() {
//        if (NetWorkHelper.isConnect(this)) {
//            String ids = SharedPreferenceUtil.getInstance(this).getString(Constants.SYNC_FAVORITE_SKY);
//            Log.i(">>>syncData ids:", ids);
//            if (Strings.isNotEmpty(ids)) {
//                new DataSyncTask(this).execute(ids);
//            }
//        }
    }

    private void initAd() {
        try {
            if (NetWorkHelper.isConnect(this)) {
                Ads.init(this, AppConstants.WDJ_AD_APP_ID, AppConstants.WDJ_AD_APP_Secret_Key);
            }
        } catch (Exception e) {

        }
    }

    /**
     * 点击返回键只返回桌面不关闭程序
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            this.startActivity(intent);
            return true;
        }
        /** 按下其它键，调用父类方法，进行默认操作 */
        return super.dispatchKeyEvent(event);
    }


    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        //取消广播接收器
        registerReceiver(networkChangeReceiver, new IntentFilter(ActionManager.ACTION_CONNECTIVITY_CHANGE));
        super.onStart();
    }

    @Override
    public void onStop() {
        //取消广播接收器
        unregisterReceiver(networkChangeReceiver);
        super.onStop();
    }

}
