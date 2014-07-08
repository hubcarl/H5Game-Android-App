package com.blue.sky.handle;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import com.blue.sky.component.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * http://www.imyukin.com/?p=268
 * @author Administrator
 *
 */
public class HandlerManager {

	private TextView myBounceView;

	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				myBounceView.invalidate();
				break;
			}
			super.handleMessage(msg);
		}
	};

	class myThread implements Runnable {
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {

				Message message = new Message();
				message.what = Constants.GUIUPDATEIDENTIFIER;

				HandlerManager.this.myHandler.sendMessage(message);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	class JavaTimer extends Activity {

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				setTitle("hear me?");
			}
		};

		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);

			timer.schedule(task, 10000);

		}
	}

	public class TestTimer extends Activity {

		Timer timer = new Timer();
		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					setTitle("hear me?");
					break;
				}
				super.handleMessage(msg);
			}

		};

		TimerTask task = new TimerTask() {
			public void run() {
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
		};

		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);

			timer.schedule(task, 10000);
		}
	}

	private Handler handler = new Handler();
	private int count = 0;

	private Runnable myRunnable = new Runnable() {
		public void run() {
			handler.postDelayed(this, 1000);
			count++;
			myBounceView.setText("Count: " + count);
		}
	};
	
	public void run(){
		handler.post(myRunnable);
	}

}
