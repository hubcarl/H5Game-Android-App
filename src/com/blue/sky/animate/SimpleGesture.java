package com.blue.sky.animate;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blue.sky.androidwebapp.R;
public class SimpleGesture extends Activity implements OnTouchListener,
		OnGestureListener {
	
	GestureDetector mGestureDetector;
	private static final int FLING_MIN_DISTANCE = 50;
	private static final int FLING_MIN_VELOCITY = 0;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_gesture);
		mGestureDetector = new GestureDetector(this);
		LinearLayout ll=(LinearLayout)findViewById(R.id.ll);
		ll.setOnTouchListener(this);
		ll.setLongClickable(true);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		Log.i("touch","touch");
		 return mGestureDetector.onTouchEvent(event); 
	}
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		 if (e1.getX()-e2.getX() > FLING_MIN_DISTANCE 
	                && Math.abs(velocityX) > FLING_MIN_VELOCITY) { 
	            // Fling left 
	            Toast.makeText(this, "向左手势", Toast.LENGTH_SHORT).show(); 
	        } else if (e2.getX()-e1.getX() > FLING_MIN_DISTANCE 
	                && Math.abs(velocityX) > FLING_MIN_VELOCITY) { 
	            // Fling right 
	            Toast.makeText(this, "向右手势", Toast.LENGTH_SHORT).show(); 
	        } 
	        return false; 
	}
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
}