package com.blue.sky.web.api.webview;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import com.blue.sky.component.R;
import com.blue.sky.control.tab.webview.WebViewFragment;
import com.blue.sky.control.tab.webview.WebViewPagerAdapter;
import com.blue.sky.main.MainTestFragment;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WebViewTabActivity extends FragmentActivity implements ActionBar.TabListener {

	  private WebViewPagerAdapter webViewPagerAdapter;

	  private ViewPager viewPager;

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_webview_tab);

	    final ActionBar actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    actionBar.setDisplayOptions(0,ActionBar.DISPLAY_SHOW_HOME|ActionBar.DISPLAY_SHOW_TITLE );

        //bar.setDisplayShowHomeEnabled(false);  
        //bar.setDisplayShowTitleEnabled(false);
	    
	    Context app = getApplicationContext();
	    
	    Fragment[] fragments = {
	      new WebViewFragment("http://www.baidu.com/" , app),
	      new WebViewFragment("http://www.bing.com/" , app),
	      new WebViewFragment("file:///android_asset/h5/list.html", app),
          new MainTestFragment(app)
	    };
	    
	    CharSequence[] titles = {"百度","Bing","H5","测试入口"};
	    webViewPagerAdapter = new WebViewPagerAdapter(getSupportFragmentManager(),fragments,titles);

	    viewPager = (ViewPager) findViewById(R.id.pager);
	    viewPager.setAdapter(webViewPagerAdapter);

	    viewPager.setOnPageChangeListener(
	      new ViewPager.SimpleOnPageChangeListener() {
	        @Override
	        public void onPageSelected(int position) {
	          actionBar.setSelectedNavigationItem(position);
	        }
	      }
	    );

	    for (int i = 0; i < webViewPagerAdapter.getCount(); i++) {
	      actionBar.addTab(
	        actionBar.newTab()
	          .setText(webViewPagerAdapter.getPageTitle(i))
	          .setTabListener(this)
	      );
	    }
	    viewPager.setOffscreenPageLimit(webViewPagerAdapter.getCount() - 1);
	  }

	  @Override
	  public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	    viewPager.setCurrentItem(tab.getPosition());
	  }

	  @Override
	  public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

	  @Override
	  public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}
	  
	  @Override
		public boolean onKeyDown(int keyCode, KeyEvent event)
		{
			if (keyCode == KeyEvent.KEYCODE_BACK )
			{

				AlertDialog isExit = new AlertDialog.Builder(this).create();
				isExit.setTitle("温馨提示");
				isExit.setMessage("退出客户端?");
				isExit.setButton("取消", listener);
				isExit.setButton2("确定", listener);

				isExit.show();

			}
			
			return false;
			
		}
		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				switch (which)
				{
				case AlertDialog.BUTTON_POSITIVE:
					finish();
					break;
				case AlertDialog.BUTTON_NEGATIVE:
					break;
				default:
					break;
				}
			}
		};	
		
	  class HomeKeyEventBroadCastReceiver extends BroadcastReceiver {
	        static final String SYSTEM_REASON = "reason";
	        static final String SYSTEM_HOME_KEY = "homekey";//home key
	        static final String SYSTEM_RECENT_APPS = "recentapps";//long home key
	       
	        @Override
	        public void onReceive(Context context, Intent intent) {
	            String action = intent.getAction();
	            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
	                String reason = intent.getStringExtra(SYSTEM_REASON);
	                if (reason != null) {
	                    if (reason.equals(SYSTEM_HOME_KEY)) {
	                    } else if (reason.equals(SYSTEM_RECENT_APPS)) {
	                    }
	                }
	            }
	        }
	    }

	}