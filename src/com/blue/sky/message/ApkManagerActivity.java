package com.blue.sky.message;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;

import com.blue.sky.androidwebapp.R;

public class ApkManagerActivity extends Activity {

	private ApkReceiver apkReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apk_manager);
	}

	/**
	 * 代码方式：一般在Activity的onStart()方法中注册监听，在onDestroy()方法中注销监听（也可以在onStop()方法中注销，其生命周期注销时结束）
	 */
	@Override
	public void onStart() {
		super.onStart();

		apkReceiver = new ApkReceiver();
		IntentFilter filter = new IntentFilter();

		filter.addAction("android.intent.action.PACKAGE_ADDED");
		filter.addAction("android.intent.action.PACKAGE_REMOVED");
		filter.addDataScheme("package");

		this.registerReceiver(apkReceiver, filter);
	}

	@Override
	public void onDestroy() {
		if (apkReceiver != null) {
			this.unregisterReceiver(apkReceiver);
		}

		super.onDestroy();
	}
}
