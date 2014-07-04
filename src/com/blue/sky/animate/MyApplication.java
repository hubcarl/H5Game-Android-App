package com.blue.sky.animate;

import android.app.Application;

public class MyApplication extends Application  {
	private static MyApplication mInstance;

	public static MyApplication getInstance() {
		return mInstance;
	}
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}
}
