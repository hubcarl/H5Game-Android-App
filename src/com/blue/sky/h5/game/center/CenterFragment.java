package com.blue.sky.h5.game.center;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.blue.sky.common.config.AppConstants;
import com.blue.sky.common.entity.User;
import com.blue.sky.common.fragment.BaseFragment;
import com.blue.sky.common.http.Action;
import com.blue.sky.common.http.HttpAsyncStringResult;
import com.blue.sky.common.http.HttpRequestAPI;
import com.blue.sky.common.message.ActionManager;
import com.blue.sky.common.message.LoginBroadcastReceiver;
import com.blue.sky.common.message.MessageCallback;
import com.blue.sky.common.utils.*;
import com.blue.sky.h5.game.MyApplication;
import com.blue.sky.h5.game.R;
import com.blue.sky.h5.game.center.favorite.FavoriteListActivity;
import com.blue.sky.h5.game.setting.*;
import com.blue.sky.h5.login.LoginActivity;
import com.blue.sky.h5.login.Util;
import com.tencent.connect.auth.QQAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.wandoujia.ads.sdk.Ads;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 设置
 */
public class CenterFragment extends BaseFragment implements View.OnClickListener {

    public static QQAuth mQQAuth;
    private Tencent mTencent;
    private com.tencent.connect.UserInfo mInfo;
    private String openid;

    private ImageView userIcon;
    private TextView userName;

    private LoginBroadcastReceiver loginBroadcastReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sky_activity_center, container, false);

        initUI(rootView);
        initUserInfo();
        initListener(rootView);
        initService();

        return rootView;
    }

    private void initService() {
        loginBroadcastReceiver = new LoginBroadcastReceiver(new MessageCallback() {
            @Override
            public void onReceiveMessage(Context context, Intent intent) {
                initUserInfo();
            }
        });
        getActivity().registerReceiver(loginBroadcastReceiver, new IntentFilter(ActionManager.ACTION_LOGIN));
    }


    private void initUserInfo() {
        String cacheUserName = MyApplication.Cookies.getUserInfo().getUserName();
        if (Strings.isNotEmpty(cacheUserName)) {
            userName.setText(cacheUserName);
        }

        String loginType = MyApplication.Cookies.getUserInfo().getLoginType();
        final String cacheLogo = MyApplication.Cookies.getUserInfo().getUserIcon();
        if (Action.Login.QQ.toString().equals(loginType)) {
            if (NetWorkHelper.isConnect(getActivity())) {
                ImageLoadUtil.getInstance().displayImage(cacheLogo, userIcon);
            } else {
                userIcon.setImageResource(R.drawable.icon_qq_logo);
            }
        } else {
            ImageLoadUtil.loadImage(userIcon, cacheLogo, ImageLoadUtil.ImageStyle.USER_MEMBER);
        }
    }

    private void initUI(View rootView) {

        userIcon = (ImageView) rootView.findViewById(R.id.setting_user_iocn);
        userName = (TextView) rootView.findViewById(R.id.setting_user_name);
        // 创建QQ实例
        mQQAuth = QQAuth.createInstance(AppConstants.QQ_APP_ID, getActivity().getApplicationContext());
        mTencent = Tencent.createInstance(AppConstants.QQ_APP_ID, getActivity());
    }

    private void initListener(View rootView) {
        rootView.findViewById(R.id.setting_login).setOnClickListener(this);

        rootView.findViewById(R.id.setting_favorite).setOnClickListener(this);
        rootView.findViewById(R.id.setting_password).setOnClickListener(this);
        rootView.findViewById(R.id.setting_game).setOnClickListener(this);
        rootView.findViewById(R.id.setting_software).setOnClickListener(this);
        rootView.findViewById(R.id.setting_soft_update).setOnClickListener(this);

        rootView.findViewById(R.id.setting_set).setOnClickListener(this);
        rootView.findViewById(R.id.setting_about).setOnClickListener(this);
        rootView.findViewById(R.id.setting_feedback).setOnClickListener(this);

        rootView.findViewById(R.id.setting_exit).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(loginBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_login:
                loginDialog();
                break;
            case R.id.setting_soft_update:
                if (NetWorkHelper.isConnect(getActivity())) {
                    UIHelp.showLoading(getActivity(), "正在检查,请稍后......");
                    HttpRequestAPI.parameter(Constants.PARAM_APK_VERSION, new HttpAsyncStringResult() {
                        @Override
                        public void process(String response) {
                            UIHelp.closeLoading();
                            if (Strings.isEmpty(response) || Constants.EMPTY_TEXT.equals(response) || response.equals(SystemUtil.getApkVersionCode())) {
                                UIHelp.showToast(getActivity(), "当前版本已是最新版本!");
                            } else {
                                UIHelp.showToast(getActivity(), "有最新版本[" + response + "]!请到豌豆荚应用市场下载最新版本!");
                            }
                        }
                    });
                } else {
                    UIHelp.showToast(getActivity(), "亲,没有网络,请检查网络设置!");
                }
                break;
            case R.id.setting_password:
                if (NetWorkHelper.isConnect(getActivity())) {
                    Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                    startActivity(intent);
                } else {
                    UIHelp.showToast(getActivity(), "亲,没有网络,请检查网络设置!");
                }
                break;
            case R.id.setting_exit:
                quit();
                break;
            case R.id.setting_about:
                startActivity(AboutActivity.class);
                break;
            case R.id.setting_favorite:
                startActivity(FavoriteListActivity.class);
                break;
            case R.id.setting_money:

                break;
            case R.id.setting_set:
                startActivity(MessageNoticeSettingActivity.class);
                break;
            case R.id.setting_feedback:
                startActivity(FeedbackActivity.class);
                break;
            case R.id.setting_game:
                Ads.showAppWall(getActivity(), AppConstants.WDJ_AD_APP_LIST_GAME);
                break;
            case R.id.setting_software:
                Ads.showAppWall(getActivity(), AppConstants.WDJ_AD_APP_LIST_SOFT);
                break;
        }
    }

    private void quit() {

        UIHelp.showConfirmDialog(getActivity(), "确定要退出登录吗？", new UIHelp.OnConfirmDialogClickListener() {
            @Override
            public void onOkClick() {

                // 发送广播
                Intent intent = new Intent("android.intent.action.QUITCURRENTACCOUNT");
                getActivity().sendBroadcast(intent);
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
        Intent intent = new Intent(getActivity(), ChangeProfileActivity.class);
        startActivity(intent);
    }

    /**
     * 密码设置
     */
    private void setPassword() {
        Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
        startActivity(intent);
    }

    /**
     * 消息通知
     */
    private void setNotice(int type) {

        Intent intent = new Intent(getActivity(), MessageNoticeSettingActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    private void loginDialog() {
        final Dialog dialog = new Dialog(getActivity(), R.style.exit_dialog);
        dialog.setContentView(R.layout.sky_login_dialog);

        TextView lovePlay = (TextView) dialog.findViewById(R.id.love_play_login);
        lovePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.class);
                dialog.dismiss();
            }
        });

        TextView qqLogin = (TextView) dialog.findViewById(R.id.qq_login);
        qqLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLogin();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void onClickLogin() {
        if (!mQQAuth.isSessionValid()) {
            IUiListener listener = new BaseUiListener() {
                @Override
                protected void doComplete(JSONObject values) {
                    updateUserInfo();
                }
            };
            //mQQAuth.login(this, "all", listener);
            mTencent.loginWithOEM(getActivity(), "all", listener, "10000144", "10000144", "xxxx");
        } else {
            mQQAuth.logout(getActivity());
            updateUserInfo();
        }
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Log.d("login response onComplete:", response.toString());
            //Util.showResultDialog(LoginActivity.this, response.toString(), "登陆成功");
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject response) {
            try {
                openid = response.getString("openid");
            } catch (JSONException e) {

            }
        }

        @Override
        public void onError(UiError e) {
            Util.toastMessage(getActivity(), "onError: " + e.errorDetail);
            Util.dismissDialog();
        }

        @Override
        public void onCancel() {
            Util.toastMessage(getActivity(), "onCancel: ");
            Util.dismissDialog();
        }
    }


    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;
                Log.d("login response:", response.toString());
                String nickName = Strings.EMPTY_STRING;
                if (response.has("nickname")) {

                    try {
                        nickName = response.getString("nickname");
                        UIHelp.showToast(getActivity(), "QQ登陆成功!");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                String icon = Strings.EMPTY_STRING;
                if (response.has("figureurl")) {
                    try {
                        icon = response.getString("figureurl_qq_2");
                        ImageLoadUtil.loadImage(icon);
                    } catch (JSONException e) {

                    }
                }
                openLogin(openid, nickName, icon, Action.Login.QQ);

                userName.setText(nickName);
                ImageLoadUtil.getInstance().displayImage(icon, userIcon);

                MyApplication.Cookies.getUserInfo().setUserName(nickName);
                MyApplication.Cookies.getUserInfo().setUserEmail("QQ账号登陆");
                MyApplication.Cookies.getUserInfo().setUserIcon(icon);
                MyApplication.Cookies.getUserInfo().setLoginType(Action.Login.QQ);
                getActivity().sendBroadcast(new Intent(ActionManager.ACTION_LOGIN));
            }
        }

    };

    private void updateUserInfo() {
        if (mQQAuth != null && mQQAuth.isSessionValid()) {
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onComplete(final Object response) {
                    Message msg = new Message();
                    msg.obj = response;
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                }

                @Override
                public void onCancel() {
                    // TODO Auto-generated method stub

                }
            };
            mInfo = new com.tencent.connect.UserInfo(getActivity(), mQQAuth.getQQToken());
            mInfo.getUserInfo(listener);

        }
    }


    private void openLogin(String openid, String nickName, String icon, Action.Login loginType) {
        User user = new User();
        user.setOpenid(openid);
        user.setNickName(nickName);
        user.setIcon(icon);
        user.setLoginType(loginType);
        HttpRequestAPI.openLogin(user, new HttpAsyncStringResult() {
            public void process(String userId) {
                Log.d(">>>openLogin userId:", userId);
                if (Strings.isNumeric(userId)) {
                    MyApplication.Cookies.getUserInfo().setUserId(userId);
                }
            }
        });
    }
}
