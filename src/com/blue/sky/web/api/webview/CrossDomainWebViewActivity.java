package com.blue.sky.web.api.webview;

import android.app.Activity;
import android.os.Bundle;

import com.blue.sky.component.R;


public class CrossDomainWebViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final CrossDomainWebView webView = (CrossDomainWebView) findViewById(R.id.web_app_view);
		webView.openCrossDomain();
		webView.openSetting(getApplicationContext()); 

		webView.loadUrl("file:///android_asset/h5/list.html");
	}
}
