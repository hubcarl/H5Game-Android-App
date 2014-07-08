package com.blue.sky.common.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.blue.sky.component.R;
import com.blue.sky.common.utils.NetWorkHelper;
import com.blue.sky.control.CirclePageIndicator;
import com.blue.sky.control.CirclePageIndicator.LastPageSlidingListener;
import com.blue.sky.web.api.webview.WebViewTabActivity;

/**
 * 引导界面
 * 
 * 
 */
public class GuideActivity extends Activity implements OnClickListener {
	
	public static final String EXTRA_BOOLEAN_ABOUT = "extra_boolean_about";

	private ViewPager guidePage;
	private GuidePagerAdapter guidePageAdapter;
	private CirclePageIndicator guidePageIndicator;
	private int guideImage[] = new int[] { R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3 };
	private int guideNum = guideImage.length;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		
		guidePage = (ViewPager) this.findViewById(R.id.guide_viewpage);
		guidePage.setOffscreenPageLimit(guideNum);
		guidePageIndicator = (CirclePageIndicator) findViewById(R.id.game_guide_indicator);
		guidePageAdapter = new GuidePagerAdapter();
		guidePage.setAdapter(guidePageAdapter);
		guidePageIndicator.setViewPager(guidePage);
		guidePageIndicator.setLastPageSlidingListener(new GuideLastPageSlidingListener());
	}

	class GuidePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return guideNum;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View itemView = LayoutInflater.from(GuideActivity.this).inflate(R.layout.item_guide_page, container, false);
			ImageView imageview = (ImageView) itemView.findViewById(R.id.guide_item_image);
			Button guideStartBtn = (Button) itemView.findViewById(R.id.guide_item_start_btn);
			guideStartBtn.setOnClickListener(GuideActivity.this);
			imageview.setImageResource(guideImage[position]);
			container.addView(itemView, 0);
			if (position == guideNum - 1) {
				guideStartBtn.setVisibility(View.VISIBLE);
			} else {
				guideStartBtn.setVisibility(View.GONE);
			}
			return itemView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.guide_item_start_btn:
			start();
			break;
		}
	}

	public class GuideLastPageSlidingListener implements LastPageSlidingListener {

		@Override
		public void onLastPageSlidding() {
			start();
		}

	}


	public void start() {
		// 返回到关于页面
		if (getIntent().getBooleanExtra(EXTRA_BOOLEAN_ABOUT, false)) {
			finish();
			overridePendingTransition(R.anim.activity_nothing, R.anim.activity_slide_bottom_out);
			return;
		} 
		if (!NetWorkHelper.isConnectActivited(this)) {
			Toast.makeText(getApplicationContext(), "网络异常,请检查网络", Toast.LENGTH_LONG).show();
		}

		Intent intent = new Intent();
		intent.setClass(GuideActivity.this, WebViewTabActivity.class);
		startActivity(intent);
		finish();
	}

}
