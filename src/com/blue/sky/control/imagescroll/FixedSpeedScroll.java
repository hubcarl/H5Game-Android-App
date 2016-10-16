package com.blue.sky.control.imagescroll;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;
/**
 * 图片滑动动画时间控制类  ,如果用默认时间可用不这个类
 * @author Administrator
 *
 *	FixedSpeedScroller scroller = new FixedSpeedScroller(context,new AccelerateInterpolator());
 */
public class FixedSpeedScroll extends Scroller {
    Context context;
    private int mDuration = 500;

    public FixedSpeedScroll(Context context) {
        super(context);
        this.context=context;
    }
    public FixedSpeedScroll(Context context, Interpolator interpolator) {
        super(context, interpolator);
        this.context=context;
    }
    /**
     *  设置滑动时间  ,如果用默认时间可不用这个类 ,反射技术实现
     * @param vp  ViewPager 对象
     * @param time  时间
     */

    public void setDuration(ViewPager vp,int time) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            this.setmDuration(time);
            field.set(vp, this);
        } catch (Exception e) {

        }
    }
    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        //System.out.println("startScroll1");
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        //System.out.println("startScroll2");
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setmDuration(int time) {
        mDuration = time;
    }

    public int getmDuration() {
        return mDuration;
    }
}