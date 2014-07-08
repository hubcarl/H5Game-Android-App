package com.example.androidwebapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.blue.sky.component.R;
import com.blue.sky.animate.MyGuideViewActivity;
import com.blue.sky.animate.SimpleGesture;
import com.blue.sky.web.api.webview.CrossDomainWebViewActivity;

public class MainActivity extends Activity {

	private TextView webviewTest;
	private TextView SimpleGesture;
	private TextView MyGuideGesture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table_circle);

		webviewTest = (TextView) findViewById(R.id.webviewTest);
		SimpleGesture = (TextView) findViewById(R.id.SimpleGesture);
		MyGuideGesture = (TextView) findViewById(R.id.MyGuideGesture);

		webviewTest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						CrossDomainWebViewActivity.class);
				startActivity(intent);
			}
		});

		SimpleGesture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						SimpleGesture.class);
				startActivity(intent);
			}
		});

		MyGuideGesture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						MyGuideViewActivity.class);
				startActivity(intent);
			}
		});
	}
}
