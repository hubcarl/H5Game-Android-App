package com.blue.sky.h5.game.detail;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.blue.sky.common.activity.BaseActivity;
import com.blue.sky.common.config.AppConstants;
import com.blue.sky.common.control.GameInfoPanel;
import com.blue.sky.common.entity.Free;
import com.blue.sky.common.entity.GameInfo;

import com.blue.sky.common.http.HttpAsyncResult;
import com.blue.sky.common.http.HttpRequestAPI;
import com.blue.sky.control.astuetz.PagerSlidingTabStrip;
import com.blue.sky.h5.game.R;
import com.blue.sky.common.utils.NetWorkHelper;
import com.wandoujia.ads.sdk.Ads;
import com.wandoujia.ads.sdk.widget.AdBanner;

import java.util.List;

/**
 * Created by Administrator on 2014/7/27.
 */
public class DetailActivity extends BaseActivity {

    private GameInfoPanel game_panel;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private TabPagerAdapter adapter;

    private AdBanner adBanner;
    private View adBannerView;

    private GameInfo item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sky_game_detail);
        item = (GameInfo) getIntent().getSerializableExtra("item");
        if(item == null){
            Free free =  (Free) getIntent().getSerializableExtra("free");
            if(NetWorkHelper.isConnect(this)){
                HttpRequestAPI.game(free.getGameId(),new HttpAsyncResult() {
                    @Override
                    public void process(List<GameInfo> list) {
                        if(!list.isEmpty()){
                            item = list.get(0);
                            init();
                        }
                    }
                });
            }else{
                showToast("没有网络,请检测网络设置");
            }
        }else{
            init();
        }
    }

    private void init(){
        setHeader(item.getShortName(), true);
        game_panel = (GameInfoPanel) findViewById(R.id.game_panel);
        game_panel.setGameInfo(item);

        tabs = (PagerSlidingTabStrip) findViewById(R.id.game_pager_tabs);
        pager = (ViewPager) findViewById(R.id.game_pager);
        //pager.setOffscreenPageLimit(2);
        adapter = new TabPagerAdapter(getSupportFragmentManager(), item);
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        tabs.setTabsStyle(getResources().getDisplayMetrics());
    }
    private void showBannerAd() {
        if (NetWorkHelper.isConnect(this)) {
            try{
                ViewGroup containerView = (ViewGroup)findViewById(R.id.banner_ad_container);
                if (adBannerView != null && containerView.indexOfChild(adBannerView) >= 0) {
                    containerView.removeView(adBannerView);
                }
                adBanner = Ads.showBannerAd(this, (ViewGroup) findViewById(R.id.banner_ad_container), AppConstants.WDJ_AD_APP_BANNER);
                adBannerView = adBanner.getView();
                containerView.setVisibility(View.VISIBLE);

                //Ads.showAppWidget(this, null, AppConstants.WDJ_AD_APP_FULLSCREEN, Ads.ShowMode.FULL_SCREEN);
            }catch (Exception ex){
                Log.e(">>>adBanner detail showBannerAd", ex.toString());
            }
        }
    }

    private class ActionBarClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {

        }
    }

}
