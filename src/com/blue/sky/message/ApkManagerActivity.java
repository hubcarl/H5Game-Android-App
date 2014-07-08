package com.blue.sky.message;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;

import com.blue.sky.component.R;

public class ApkManagerActivity extends Activity {

	private ApkReceiver apkReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apk_manager);
	}

	/**
	 * ���뷽ʽ��һ����Activity��onStart()������ע�������onDestroy()������ע�����Ҳ������onStop()������ע������������ע��ʱ����
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
