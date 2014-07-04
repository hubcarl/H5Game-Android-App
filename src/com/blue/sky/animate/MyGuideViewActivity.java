package com.blue.sky.animate;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blue.sky.androidwebapp.R;

/**
 * Android实现左右滑动指引效果
 * @Description: Android实现左右滑动指引效果

 * @File: MyGuideViewActivity.java

 * @Author Hanyonglu

 * @Date 2012-4-6 下午11:15:18

 * @Version V1.0
 */
public class MyGuideViewActivity extends Activity {
	 private ViewPager viewPager;  
	 private ArrayList<View> pageViews;  
	 private ImageView imageView;  
	 private ImageView[] imageViews; 
	 // 包裹滑动图片LinearLayout
	 private ViewGroup main;
	 // 包裹小圆点的LinearLayout
	 private ViewGroup group;
	 private TextView tv1;
	 private TextView tv2;
	 private TextView tv3;
	 private TextView tv4;
	 private TextView tv5;
	 private TextView tv6;
	    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置无标题窗口
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        LayoutInflater inflater = getLayoutInflater();
        View v5 = inflater.inflate(R.layout.activity_mygruide_item05, null);
        View v6 = inflater.inflate(R.layout.activity_mygruide_item06, null);
        View v1 = inflater.inflate(R.layout.activity_mygruide_item01, null);
        View v2 = inflater.inflate(R.layout.activity_mygruide_item02, null);
        View v3 = inflater.inflate(R.layout.activity_mygruide_item03, null);
        View v4 = inflater.inflate(R.layout.activity_mygruide_item04, null);
        
        pageViews = new ArrayList<View>();  
        pageViews.add(v5);
        pageViews.add(v6);
        pageViews.add(v1);  
        pageViews.add(v2);  
        pageViews.add(v3);  
        pageViews.add(v4);  
        
        imageViews = new ImageView[pageViews.size()];  
        main = (ViewGroup)inflater.inflate(R.layout.activity_mygruide_main, null);  
        
        group = (ViewGroup)main.findViewById(R.id.viewGroup);  
        viewPager = (ViewPager)main.findViewById(R.id.guidePages);
        
        for (int i = 0; i < pageViews.size(); i++) {  
            imageView = new ImageView(MyGuideViewActivity.this);  
            imageView.setLayoutParams(new LayoutParams(20,20));  
            imageView.setPadding(20, 0, 20, 0);  
            imageViews[i] = imageView;  
            
            if (i == 0) {  
                //默认选中第一张图片
                imageViews[i].setBackgroundResource(R.drawable.page_indicator_focused);  
            } else {  
                imageViews[i].setBackgroundResource(R.drawable.page_indicator);  
            }  
            
            group.addView(imageViews[i]);  
        }  
        
        setContentView(main);
        
        // 监听子页面事件
        tv1 = (TextView)v1.findViewById(R.id.textView1);
        tv1.setOnClickListener(new TextViewOnClickListener());
        tv2 = (TextView)v2.findViewById(R.id.textView2);
        tv2.setOnClickListener(new TextViewOnClickListener());
        tv3 = (TextView)v3.findViewById(R.id.textView3);
        tv3.setOnClickListener(new TextViewOnClickListener());
        tv4 = (TextView)v4.findViewById(R.id.textView4);
        tv4.setOnClickListener(new TextViewOnClickListener());
        tv5 = (TextView)v5.findViewById(R.id.textView5);
        tv5.setOnClickListener(new TextViewOnClickListener());
        tv6 = (TextView)v6.findViewById(R.id.textView6);
        tv6.setOnClickListener(new TextViewOnClickListener());
        
        viewPager.setAdapter(new GuidePageAdapter());  
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());  
    }
    
    // 事件监听器
    private class TextViewOnClickListener implements OnClickListener{
    	@Override
    	public void onClick(View v) {
    		// TODO Auto-generated method stub
    		Toast.makeText(MyGuideViewActivity.this, ((TextView) v).getText(), Toast.LENGTH_LONG).show();
    	}
    }
    
    // 指引页面数据适配器
    private class GuidePageAdapter extends PagerAdapter {  
  	  
        @Override  
        public int getCount() {  
            return pageViews.size();  
        }  
  
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) {  
            return arg0 == arg1;  
        }  
  
        @Override  
        public int getItemPosition(Object object) {  
            // TODO Auto-generated method stub  
            return super.getItemPosition(object);  
        }  
  
        @Override  
        public void destroyItem(View arg0, int arg1, Object arg2) {  
            // TODO Auto-generated method stub  
            ((ViewPager) arg0).removeView(pageViews.get(arg1));  
        }  
  
        @Override  
        public Object instantiateItem(View arg0, int arg1) {  
            // TODO Auto-generated method stub  
            ((ViewPager) arg0).addView(pageViews.get(arg1));  
            return pageViews.get(arg1);  
        }  
  
        @Override  
        public void restoreState(Parcelable arg0, ClassLoader arg1) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public Parcelable saveState() {  
            // TODO Auto-generated method stub  
            return null;  
        }  
  
        @Override  
        public void startUpdate(View arg0) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void finishUpdate(View arg0) {  
            // TODO Auto-generated method stub  
  
        }  
    } 
    
    // 指引页面更改事件监听器
    private class GuidePageChangeListener implements OnPageChangeListener {  
    	  
        @Override  
        public void onPageScrollStateChanged(int arg0) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void onPageSelected(int arg0) {  
            for (int i = 0; i < imageViews.length; i++) {  
                imageViews[arg0].setBackgroundResource(R.drawable.page_indicator_focused);
                
                if (arg0 != i) {  
                    imageViews[i].setBackgroundResource(R.drawable.page_indicator);  
                }  
            }
        }  
    }  
}