package com.blue.sky.common.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;
import com.blue.sky.common.utils.Strings;
import com.blue.sky.h5.game.R;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Administrator on 2014/7/27.
 */
public class BaseActivity extends FragmentActivity {

    public InputMethodManager manager; // 软键盘管理，用于点击空白处隐藏软键盘

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    /**
     * 点击空白出隐藏软键盘
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    public void hideSoftInput() {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); // 强制隐藏键盘
        }
    }


    protected  void setTitle(String name)
    {
        ((TextView)findViewById(R.id.commonTitle)).setText(name);
    }


    protected void setHeader(String title, boolean isBack)
    {
        setTitle(title);
        if(isBack){
            TextView leftButton = ((TextView)findViewById(R.id.leftButton));
            leftButton.setVisibility(View.VISIBLE);
            leftButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    protected  void setHeader(String title, boolean isBack, String rightText, View.OnClickListener rightListener){
        setHeader(title,isBack);
        if (Strings.isNotEmpty(rightText)) {
            findViewById(R.id.rightButton).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.rightButton)).setText(rightText);
            if(rightListener!=null){
                findViewById(R.id.rightButton).setOnClickListener(rightListener);
            }
        }
        else
        {
            findViewById(R.id.rightButton).setVisibility(View.GONE);
        }
    }

    protected  void setHeader(String title, boolean isBack, String rightText, int resId, View.OnClickListener rightListener){
        setHeader(title,isBack);
        TextView rightButton = (TextView)findViewById(R.id.rightButton);
        rightButton.setVisibility(View.VISIBLE);
        rightButton.setText(rightText);
        if(rightListener!=null){
            findViewById(R.id.rightButton).setOnClickListener(rightListener);
        }
        if(resId>0){
            rightButton.setBackgroundResource(resId);
        }
    }

    private Toast toast = null;

    /**
     * 默认时间LENGTH_LONG
     *
     * @param msg
     */
    public void showToast(String msg) {
        showToast(msg, Toast.LENGTH_LONG);
    }

    /**
     * 不会一直重复重复重复重复的提醒了
     *
     * @param msg
     * @param length
     *            显示时间
     */
    protected void showToast(String msg, int length) {
        if (toast == null) {
            toast = Toast.makeText(this, msg, length);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    protected void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    protected int getScreenMode() {
        return getResources().getConfiguration().orientation;
    }

    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startActivity(intent);
    }

    protected void startActivity(Class<?> clazz, String key, Serializable value) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    protected void startActivity(Class<?> clazz, String key, String value) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    protected void startActivity(Class<?> clazz, String key, int value) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    protected void startActivity(Class<?> clazz,Map<String,String> params) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        for (Map.Entry<String, String> entry: params.entrySet()) {
            intent.putExtra(entry.getKey(), entry.getValue());
        }
        startActivity(intent);
    }

    public void setNetworkError(){
        findViewById(R.id.loading).setVisibility(View.GONE);
        findViewById(R.id.liner_empty).setVisibility(View.VISIBLE);

        TextView emptyText =((TextView)findViewById(R.id.emptyText));
        Drawable drawable= getResources().getDrawable(R.drawable.code_icon_error_network);
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        emptyText.setVisibility(View.VISIBLE);
        emptyText.setCompoundDrawables(null,drawable,null,null);
        emptyText.setText(R.string.network_error_text);
    }

    public void setNetworkError(View.OnClickListener listener){
        TextView btnReply =  (TextView)findViewById(R.id.btn_retry);
        if(btnReply!=null){
            btnReply.setVisibility(View.VISIBLE);
            btnReply.setOnClickListener(listener);
        }
        setNetworkError();
    }

    public void setPageEmpty(){
        findViewById(R.id.loading).setVisibility(View.GONE);
        Drawable drawable= getResources().getDrawable(R.drawable.code_empty);
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        TextView emptyText =((TextView)findViewById(R.id.emptyText));
        emptyText.setCompoundDrawables(null,drawable,null,null);
        emptyText.setText(R.string.page_empty_text);
    }

    public void setPageVisible(){
        findViewById(R.id.empty).setVisibility(View.GONE);
    }
}
