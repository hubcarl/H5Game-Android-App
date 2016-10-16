package com.blue.sky.h5.game.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.blue.sky.common.activity.BaseActivity;
import com.blue.sky.common.config.AppConstants;
import com.blue.sky.common.http.HttpAsyncStringResult;
import com.blue.sky.common.http.HttpRequestAPI;
import com.blue.sky.common.utils.*;
import com.blue.sky.h5.game.MyApplication;
import com.blue.sky.h5.game.R;
import com.wandoujia.ads.sdk.Ads;

/**
 * 设置
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton btnVioceNotifi;
    private ImageButton btnShakeNotifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sky_activity_setting);
        setHeader("设置", true);
        initUI();
        initListener();
        initAd();
    }

    private void initAd() {
        try {
            if (NetWorkHelper.isConnect(this)) {
                Ads.init(this, AppConstants.WDJ_AD_APP_ID, AppConstants.WDJ_AD_APP_Secret_Key);
            }
        } catch (Exception e) {

        }
    }

    private void initUI() {

        btnVioceNotifi = (ImageButton) findViewById(R.id.setting_voice_notifi);
        btnShakeNotifi = (ImageButton) findViewById(R.id.setting_shake_notifi);

        btnShakeNotifi.setOnClickListener(this);
        btnVioceNotifi.setOnClickListener(this);

        btnVioceNotifi.setImageResource(MyApplication.Cookies.getUserSetting().isMusic() ? R.drawable.icon_switch_on
                : R.drawable.icon_switch_off);
        btnShakeNotifi.setImageResource(MyApplication.Cookies.getUserSetting().isShake() ? R.drawable.icon_switch_on
                : R.drawable.icon_switch_off);

    }

    private void initListener() {
        findViewById(R.id.setting_information).setOnClickListener(this);
        findViewById(R.id.setting_exit).setOnClickListener(this);
        findViewById(R.id.setting_password).setOnClickListener(this);
        findViewById(R.id.soft_update).setOnClickListener(this);
        findViewById(R.id.setting_software).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_software:
                Ads.showAppWall(this, AppConstants.WDJ_AD_APP_LIST_GAME);
                break;
            case R.id.setting_information:
                setInformation();
                break;
            case R.id.soft_update:
                if(NetWorkHelper.isConnect(this)){
                    UIHelp.showLoading(this, "正在检查,请稍后......");
                    HttpRequestAPI.parameter(Constants.PARAM_APK_VERSION, new HttpAsyncStringResult() {
                        @Override
                        public void process(String response) {
                            UIHelp.closeLoading();
                            if (Strings.isEmpty(response) || Constants.EMPTY_TEXT.equals(response) || response.equals(SystemUtil.getApkVersionCode())) {
                                showToast("当前版本已是最新版本!");
                            } else {
                                showToast("有最新版本[" + response + "]!请到豌豆荚应用市场下载最新版本!");
                            }
                        }
                    });
                }else{
                    showToast("亲,没有网络,请检查网络设置!");
                }
                break;
            case R.id.setting_password:
                if(NetWorkHelper.isConnect(this)){
                    startActivity(ChangePasswordActivity.class);
                }else{
                    showToast("亲,没有网络,请检查网络设置!");
                }
                break;
            case R.id.setting_exit:
                quit();
                break;
            case R.id.setting_voice_notifi:
                boolean isMusic = MyApplication.Cookies.getUserSetting().isMusic();
                btnVioceNotifi.setImageResource(!isMusic ? R.drawable.icon_switch_on : R.drawable.icon_switch_off);
                MyApplication.Cookies.getUserSetting().setMusic(!isMusic);
                showToast("设置成功");
                break;
            case R.id.setting_shake_notifi:
                boolean isShake = MyApplication.Cookies.getUserSetting().isShake();
                btnShakeNotifi.setImageResource(!isShake ? R.drawable.icon_switch_on : R.drawable.icon_switch_off);
                MyApplication.Cookies.getUserSetting().setShake(!isShake);
                showToast("设置成功");
                break;
        }
    }

    private void quit() {

        UIHelp.showConfirmDialog(SettingActivity.this, "确定要退出登录吗？", new UIHelp.OnConfirmDialogClickListener() {
            @Override
            public void onOkClick() {

                // 发送广播
                Intent intent = new Intent("android.intent.action.QUITCURRENTACCOUNT");
                SettingActivity.this.sendBroadcast(intent);
            }

            @Override
            public void onCancelClick() {
            }
        });
    }

    /**
     * 个人信息
     */
    private void setInformation() {
        Intent intent = new Intent(this, ChangeProfileActivity.class);
        startActivity(intent);
    }

    /**
     * 密码设置
     */
    private void setPassword() {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    /**
     * 消息通知
     */
    private void setNotice(int type) {

        Intent intent = new Intent(SettingActivity.this, MessageNoticeSettingActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
