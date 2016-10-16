package com.blue.sky.h5.game.detail;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.blue.sky.common.activity.BaseActivity;
import com.blue.sky.common.config.AppConstants;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.common.http.HttpAsyncStringResult;
import com.blue.sky.common.http.HttpRequestAPI;
import com.blue.sky.common.utils.*;
import com.blue.sky.control.webview.CrossDomainWebView;
import com.blue.sky.h5.game.R;
import com.wandoujia.ads.sdk.Ads;
import com.wandoujia.ads.sdk.widget.AdBanner;

/**
 * Created by Administrator on 2014/7/27.
 */
public class PlayActivity extends BaseActivity implements View.OnClickListener {

    private CrossDomainWebView webView;
    private ProgressBar progressbar;

    private PopupWindow popupWindow ;
    private View mPopupWindowView;

    private AdBanner adBanner;
    private View adBannerView;

    private GameInfo item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sky_activity_detail);

        item = (GameInfo) getIntent().getSerializableExtra("item");
        setHeader(item.getShortName(), true, "", R.drawable.sky_icon_actionbar_more,new ActionBarClickListener());

        Log.d(">>>Url:", item.getGameUrl());
        progressbar =  (ProgressBar)findViewById(R.id.webview_progress_bar);
        webView = (CrossDomainWebView) findViewById(R.id.web_app_view);
        webView.openSetting(this);
        webView.setWebChromeClient(new  WebChromeClient());

        if (item != null) {
            if (NetWorkHelper.isConnect(this)) {
                findViewById(R.id.liner_empty).setVisibility(View.GONE);
                webView.loadUrl(item.getGameUrl());
                updateViewCount();
            } else {
                setNetworkError(replyListener);
            }
        } else {

        }

        initPopupWindow();
        showBannerAd();

        long ad_show_valid = SharedPreferenceUtil.getInstance(PlayActivity.this).getLong("ad_show_valid");
        if (ad_show_valid > 0 && System.currentTimeMillis() / 1000 - ad_show_valid < 3600) {
            if (NetWorkHelper.isConnect(this)) {
                findViewById(R.id.ad_container).setVisibility(View.VISIBLE);
            }
        }else{
            HttpRequestAPI.parameter("android_h5_game_ad_show",new HttpAsyncStringResult() {
                @Override
                public void process(String response) {
                    if("true".equals(response)){
                        findViewById(R.id.ad_container).setVisibility(View.VISIBLE);
                        SharedPreferenceUtil.getInstance(PlayActivity.this).putLong("ad_show_valid",System.currentTimeMillis()/1000);
                    }
                }
            });
        }
    }

    private void updateViewCount(){
        HttpRequestAPI.updateCount(1, item.getGameId(), new HttpAsyncStringResult() {
            @Override
            public void process(String response) {
                if (Constants.SUCCESS.equals(response)) {
                }
            }
        });
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressbar.setVisibility(View.GONE);
            } else {
                if (progressbar.getVisibility() == View.GONE)
                    progressbar.setVisibility(View.VISIBLE);
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

    }

    private View.OnClickListener replyListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            TextView emptyText = (TextView)findViewById(R.id.emptyText);
            emptyText.setText(R.string.loading_text);
            emptyText.setVisibility(View.VISIBLE);
            emptyText.setCompoundDrawables(null,null,null,null);
            findViewById(R.id.loading).setVisibility(View.VISIBLE);
            findViewById(R.id.liner_empty).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_retry).setVisibility(View.GONE);
            if(NetWorkHelper.isConnect(PlayActivity.this)){

            }else{
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setNetworkError(replyListener);
                    }
                },1000);
            }
        }
    };

    private void showBannerAd() {
        if (NetWorkHelper.isConnect(this)) {
            try{
                ViewGroup containerView = (ViewGroup)findViewById(R.id.banner_ad_container);
                if (adBannerView != null && containerView.indexOfChild(adBannerView) >= 0) {
                    containerView.removeView(adBannerView);
                }
                adBanner = Ads.showBannerAd(this, (ViewGroup) findViewById(R.id.banner_ad_container), AppConstants.WDJ_AD_APP_BANNER);
                adBannerView = adBanner.getView();
            }catch (Exception ex){
                Log.e(">>>adBanner detail showBannerAd", ex.toString());
            }
            ImageView  adClose = (ImageView)findViewById(R.id.ad_close);
            adClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    findViewById(R.id.ad_container).setVisibility(View.GONE);
                }
            });
        }
    }


    private void initPopupWindow(){
        mPopupWindowView = LayoutInflater.from(this).inflate(R.layout.sky_popupwindow_menu, null);
        mPopupWindowView.findViewById(R.id.action_share).setOnClickListener(this);
        mPopupWindowView.findViewById(R.id.action_browser).setOnClickListener(this);
        mPopupWindowView.findViewById(R.id.action_refresh).setOnClickListener(this);
        mPopupWindowView.findViewById(R.id.action_favorite).setOnClickListener(this);
        popupWindow = new PopupWindow(mPopupWindowView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //popupWindow.setBackgroundDrawable(null);
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.action_share:
                // 启动分享发送到属性
                Intent intent = new Intent(Intent.ACTION_SEND);
                // 分享发送到数据类型
                intent.setType("text/plain");
                // 分享的主题
                intent.putExtra(Intent.EXTRA_SUBJECT, "爱玩--"+item.getShortName()+"--HTML5小游戏");
                // 分享的内容
                intent.putExtra(Intent.EXTRA_TEXT, getShareInfo());
                // 允许intent启动新的activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //目标应用选择对话框的标题
                startActivity(Intent.createChooser(intent, "爱玩--HTML5小游戏"));
                popupWindow.dismiss();
                break;
            case R.id.action_browser:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(item.getGameUrl()));
                startActivity(i);
                break;
            case R.id.action_refresh:
                webView.loadUrl(item.getGameUrl());
                popupWindow.dismiss();
                break;
            case R.id.action_favorite:
                favorite();
                popupWindow.dismiss();
                break;
        }
    }

    private String getShareInfo(){
        String shareInfo= "[爱玩]" + item.getShortName() + "小游戏不错" ;
        String title = webView.getTitle();
        if(Strings.isNotEmpty(title)){
            title = title.replace("-7k7k游戏","").replace("9G游戏","").replace("猎豹游戏","");
        }
        if(Strings.isNotEmpty(title)){
            shareInfo+= "," + title;
        }else if(Strings.isNotEmpty(item.getSummery())){
            shareInfo+= "," + item.getSummery();
        }
        shareInfo+=",精彩小游戏等你来玩, 快来放松放松吧！分享自小游戏Android客户端[爱玩] http://appshow.sinaapp.com";
        return shareInfo;
    }

    private void favorite(){
        if(NetWorkHelper.isConnect(this)){
            HttpRequestAPI.addFavorite(item, SystemUtil.getUniqueId(), new HttpAsyncStringResult() {
                @Override
                public void process(String response) {
                    if(Constants.SUCCESS.equals(response)){
                        showToast("收藏成功!");
                    }else{
                        showToast("收藏失败,请稍后再试!");
                    }
                }
            });
        }else{
            showToast("没有网络, 请检查网络设置!");
        }
    }

    private class ActionBarClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            if(!popupWindow.isShowing()){
                TextView rightButton = (TextView)findViewById(R.id.rightButton);
                popupWindow.showAsDropDown(rightButton, rightButton.getLayoutParams().width/2, 0);
            }else{
                popupWindow.dismiss();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.removeAllViews();
        webView.clearHistory();
        webView.destroy();
    }
}
