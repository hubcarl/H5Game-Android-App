package com.blue.sky.h5.game.home;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.blue.sky.common.config.AppConstants;
import com.blue.sky.common.control.CategoryNavPanel;
import com.blue.sky.common.control.MyCommonPanel;
import com.blue.sky.common.db.DBHelper;
import com.blue.sky.common.entity.Free;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.common.fragment.BaseFragment;
import com.blue.sky.common.http.HttpAsyncResult;
import com.blue.sky.common.http.HttpAsyncStringResult;
import com.blue.sky.common.http.HttpRequestAPI;
import com.blue.sky.common.http.HttpRequestParam;
import com.blue.sky.common.message.ActionManager;
import com.blue.sky.common.message.MessageCallback;
import com.blue.sky.common.message.NetworkChangeReceiver;
import com.blue.sky.common.utils.*;
import com.blue.sky.control.imagescroll.MyImageScroll;
import com.blue.sky.control.pullrefresh.PullToRefreshBase;
import com.blue.sky.control.pullrefresh.PullToRefreshScrollView;
import com.blue.sky.h5.game.R;
import com.blue.sky.h5.game.category.Category;
import com.blue.sky.h5.game.detail.DetailActivity;
import com.wandoujia.ads.sdk.Ads;
import com.wandoujia.ads.sdk.widget.AdBanner;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    private CategoryNavPanel categoryNavPanel;
    private MyCommonPanel recommendPanel;
    private MyCommonPanel latestPanel;
    private MyCommonPanel hotPanel;

    private MyImageScroll myPager; // 图片容器
    private LinearLayout ovalLayout; // 圆点容器
    private List<View> listViews; // 图片组
    private List<Free> imagesDataList;

    private List<GameInfo> latestList;
    private List<GameInfo> hotList;

    private PullToRefreshScrollView mPullRefreshScrollView;
    private ScrollView mScrollView;


    private AdBanner adBanner;
    private View adBannerView;
    private ViewGroup adContainerView;

    private NetworkChangeReceiver networkChangeReceiver;

    // 在Handler中获取消息，重写handleMessage()方法
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 判断消息码是否为1
            if (msg.what == Constants.FINISHED) {
                latestPanel.refresh(latestList);
                hotPanel.refresh(hotList);
                mPullRefreshScrollView.onRefreshComplete();
            }
        }
    };

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sky_activity_home_fragment, container, false);

        categoryNavPanel = (CategoryNavPanel) rootView.findViewById(R.id.categoryNavPanel);


        recommendPanel = (MyCommonPanel) rootView.findViewById(R.id.recommendPanel);
        latestPanel = (MyCommonPanel) rootView.findViewById(R.id.latestPanel);
        hotPanel = (MyCommonPanel) rootView.findViewById(R.id.hotPanel);

        recommendPanel.init("编辑推荐");
        latestPanel.init("最新游戏");
        hotPanel.init("热门游戏");

        recommendPanel.setMoreVisible(View.INVISIBLE);
        latestPanel.setMoreVisible(View.INVISIBLE);
        hotPanel.setMoreVisible(View.INVISIBLE);

        categoryNavPanel.init(initNavData());

        myPager = (MyImageScroll) rootView.findViewById(R.id.myvp);
        ovalLayout = (LinearLayout) rootView.findViewById(R.id.vb);
        listViews = new ArrayList<View>();

        mPullRefreshScrollView = (PullToRefreshScrollView) rootView.findViewById(R.id.pull_refresh_scrollview);
        mPullRefreshScrollView.setMode(PullToRefreshBase.Mode.DISABLED);
        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(UIHelp.getPullRefreshLabel(getActivity()));
                if (NetWorkHelper.isConnect(getActivity())) {
                    loadHttpData();
                } else {
                    loadData();
                }
            }
        });

        mScrollView = mPullRefreshScrollView.getRefreshableView();

        loadData();
        if (NetWorkHelper.isConnect(getActivity())) {
            loadHttpData();
            loadImageScroll();
        }else{
            recommendPanel.setVisibility(View.GONE);
        }
        //showAd(rootView);
        registerNetworkChange(rootView);

        return rootView;
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                latestList = DBHelper.getGameList(getActivity(), new HttpRequestParam(0,1,5));
                hotList = DBHelper.getGameList(getActivity(), new HttpRequestParam(0,2, 5));
                //imagesDataList = DBHelper.getGameList(getActivity(), new HttpRequestParam(Action.REST.LIST, 89, 116, 0, 0, 1, 10));
                // 获取一个Message对象，设置what为1
                Message msg = Message.obtain();
                msg.what = Constants.FINISHED;
                // 发送这个消息到消息队列中
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void loadHttpData() {

        HttpRequestAPI.request(new HttpRequestParam(101), new HttpAsyncResult() {
            public void process(List<GameInfo> tempList) {
                recommendPanel.refresh(tempList);
                mPullRefreshScrollView.onRefreshComplete();
            }
        });

        HttpRequestAPI.request(new HttpRequestParam(0, Constants.ORDER_TIME, 5), new HttpAsyncResult() {
            public void process(List<GameInfo> tempList) {
                latestPanel.refresh(tempList);
                mPullRefreshScrollView.onRefreshComplete();
            }
        });

        HttpRequestAPI.request(new HttpRequestParam(0, Constants.ORDER_HOT, 5), new HttpAsyncResult() {
            public void process(List<GameInfo> tempList) {
                hotPanel.refresh(tempList);
                mPullRefreshScrollView.onRefreshComplete();
            }
        });
    }

    /**
     * 图片滚动
     */
    private void initImageScroll(){
        listViews.clear();
        for (Free item : imagesDataList) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {// 设置图片点击事件
                    if(NetWorkHelper.isConnect(getActivity())){
                        startActivity(DetailActivity.class, "free", imagesDataList.get(myPager.getCurIndex()));
                    }else{
                        UIHelp.showToast(getActivity(),"没有网络,请检测网络设置");
                    }
                }
            });
            ImageLoadUtil.loadImage(imageView, item.getThumbUrl(), null);
            //imageView.setImageResource(imageResId[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            listViews.add(imageView);
        }
        if(!listViews.isEmpty()){
            //开始滚动
            myPager.setVisibility(View.VISIBLE);
            myPager.start(getActivity(), listViews, 4000, ovalLayout,
                    R.layout.sky_image_scroll_ad_bottom_item, R.id.ad_item_v,
                    R.drawable.code_image_scroll_dot_focused, R.drawable.code_image_scroll_dot_normal);
        }else{
            myPager.setVisibility(View.GONE);
        }
    }

    private void loadImageScroll(){
        HttpRequestAPI.freeImage(100, 8, new HttpAsyncStringResult() {
            public void process(String response) {
                imagesDataList = JsonHelper.parseFreeImage(response);
                Log.d(">>>Home imagesDataList:", imagesDataList.size() + "");
                initImageScroll();
            }
        });
    }

    @Override
    public void onStart() {
//        try{
//            if(adBanner!=null){
//                adBanner.startAutoScroll();
//            }
//            Log.i(">>>HomeActivity adBanner onResume", "startAutoScroll");
//        }catch (Exception ex){
//            Log.e(">>>HomeActivity adBanner onResume", ex.toString());
//        }
        getActivity().registerReceiver(networkChangeReceiver, new IntentFilter(ActionManager.ACTION_CONNECTIVITY_CHANGE));
        super.onStart();
    }

    @Override
    public void onStop() {
//        try{
//            if(adBanner!=null){
//                adBanner.stopAutoScroll();
//                Log.i(">>>HomeActivity adBanner onStop", "stopAutoScroll");
//            }
//        }catch (Exception ex){
//            Log.e(">>>HomeActivity adBanner onStop", ex.toString());
//        }
        getActivity().unregisterReceiver(networkChangeReceiver);
        super.onStop();
    }

    private List<Category> initNavData() {
        List<Category> list = new ArrayList<Category>();
        list.add(new Category(5, "射击游戏"));
        list.add(new Category(4, "棋牌游戏"));
        list.add(new Category(9, "美女帅哥"));
        list.add(new Category(1, "休闲娱乐"));
        return list;
    }

    private void showAd(View rootView) {
        adContainerView = (ViewGroup) rootView.findViewById(R.id.banner_ad_container);
        if (NetWorkHelper.isConnect(getActivity())) {
            showBannerAd(rootView);
            //  闪屏一个星期显示一次
            long adTime = SharedPreferenceUtil.getInstance(getActivity()).getLong("ad_time");
            if(System.currentTimeMillis()/1000 - adTime > 7* 24*3600){
                Ads.showAppWidget(getActivity(), null, AppConstants.WDJ_AD_APP_FULLSCREEN, Ads.ShowMode.FULL_SCREEN);
                SharedPreferenceUtil.getInstance(getActivity()).putLong("ad_time",System.currentTimeMillis()/1000);
            }
        }
    }

    private void showBannerAd(final View rootView) {

        if (NetWorkHelper.isConnect(getActivity())) {
            try{
                Ads.init(getActivity(), AppConstants.WDJ_AD_APP_ID,  AppConstants.WDJ_AD_APP_Secret_Key);
                if (adBannerView != null && adContainerView.indexOfChild(adBannerView) >= 0) {
                    adContainerView.removeView(adBannerView);
                }
                adBanner = Ads.showBannerAd(getActivity(), adContainerView, AppConstants.WDJ_AD_APP_BANNER);
                adBannerView = adBanner.getView();
                adContainerView.setVisibility(View.VISIBLE);
                Log.d(">>>HomeActivity showBannerAd","success");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("adBannerView","");
                    }
                },2000);
            }catch (Exception ex){
                Log.e(">>>adBanner showBannerAd", ex.toString());
            }
        }
    }

    private void registerNetworkChange(final View rootView){

        networkChangeReceiver = new NetworkChangeReceiver(new MessageCallback(){
            @Override
            public void onReceiveMessage(Context context, Intent intent) {
                if (intent != null) {
//                    int state = intent.getIntExtra(Constants.NET_STATE, -1);
//                    Log.i(">>>HomeActivity NetworkChangeReceiver", "state:" + state);
//                    if (state > 0 && adContainerView.getVisibility() == View.GONE) {
//                        showBannerAd(rootView);
//                    }
                }
            }
        });
        getActivity().registerReceiver(networkChangeReceiver, new IntentFilter(ActionManager.ACTION_CONNECTIVITY_CHANGE));
    }

}
