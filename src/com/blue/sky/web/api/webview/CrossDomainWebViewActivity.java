package com.blue.sky.web.api.webview;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import com.blue.sky.component.R;


public class CrossDomainWebViewActivity extends Activity {

    private CrossDomainWebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		webView = (CrossDomainWebView) findViewById(R.id.web_app_view);
		webView.openCrossDomain();
		webView.openSetting(getApplicationContext()); 

		webView.loadUrl("file:///android_asset/h5/list.html");
	}

    @Override
    public  boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView.canGoBack() && event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            webView.goBack();
            return    true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
