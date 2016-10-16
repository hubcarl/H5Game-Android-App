package com.blue.sky.h5.game.setting;

import android.os.Bundle;
import android.webkit.WebView;
import com.blue.sky.h5.game.MyApplication;
import com.blue.sky.common.activity.BaseActivity;
import com.blue.sky.common.http.Action;
import com.blue.sky.common.utils.Strings;
import com.blue.sky.h5.game.R;


/**
 * 修改密码(设置->修改密码)
 */
public class ChangePasswordActivity extends BaseActivity {

    private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sky_activity_change_password);
		setHeader("密码修改",true);
        webView = (WebView) findViewById(R.id.web_app_view);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        String baseUrl = "http://codestudy.sinaapp.com/center/reset.php?t="+System.currentTimeMillis();
        String loginType = MyApplication.Cookies.getUserInfo().getLoginType();
        if(Action.Login.QQ.toString().equals(loginType)||Action.Login.Sina.toString().equals(loginType)){

        }else {
            String strUserEmail = MyApplication.Cookies.getUserInfo().getUserEmail();
            if(Strings.isNotEmpty(strUserEmail)){
                baseUrl = "&user=" + strUserEmail;
            }
        }
        webView.loadUrl(baseUrl);
	}
}