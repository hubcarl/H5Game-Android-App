package com.blue.sky.h5.game.setting;

import android.os.Bundle;
import android.webkit.WebView;
import com.blue.sky.common.activity.BaseActivity;
import com.blue.sky.h5.game.R;


public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sky_activity_about);
        setHeader("关于爱玩", true);

        WebView webView = (WebView) findViewById(R.id.web_app_view);
        webView.loadUrl("file:///android_asset/about.html");
    }
}
