package com.blue.sky.h5.game.detail.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.blue.sky.common.db.DBHelper;
import com.blue.sky.common.entity.Free;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.common.fragment.BaseFragment;
import com.blue.sky.common.http.*;
import com.blue.sky.common.utils.*;
import com.blue.sky.h5.game.MyApplication;
import com.blue.sky.h5.game.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GameDetailFragment extends BaseFragment implements View.OnClickListener {

    private List<String> imagesDataList;
    private LinearLayout imageContainer;
    private TextView btn_play;
    private TextView btn_un_play;

    private GameInfo gameInfo;
    private String playKey = "gameInfo_isPlay_";

    public GameDetailFragment(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
        this.playKey = playKey + gameInfo.getGameId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sky_game_info_fragment, container, false);

        initGameInfo();
        initUI(rootView);
        loadImageScroll(rootView);

        return rootView;
    }

    private void initGameInfo(){
      GameInfo cacheGameInfo =  DBHelper.getGame(getActivity(),gameInfo.getGameId());
      if(cacheGameInfo!=null){
          Log.i(">>>initGameInfo","cacheGameInfo" + cacheGameInfo.toString());
          gameInfo.setPlayCount(cacheGameInfo.getPlayCount());
          gameInfo.setUnPlayCount(cacheGameInfo.getUnPlayCount());
      }else{
          Log.i(">>>initGameInfo","cacheGameInfo is null");
      }
    }

    private void initScreenShot(String url) {
            ImageView imageView = new ImageView(getActivity());
            ImageLoadUtil.loadImage(imageView, url, null);
            imageContainer.addView(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {// 设置图片点击事件

                }
            });
            //imageView.setImageResource(imageResId[i]);
            int width = UIHelp.dip2px(getActivity(), 160);
            Log.d(">>>width", width + "");// 600  2.5
            Log.d(">>>pxToSp", UIHelp.px2sp(getActivity(), 18) + "");
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.leftMargin = UIHelp.dip2px(getActivity(), 2);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

    }

    private void loadImageScroll(final View rootView) {
        if(NetWorkHelper.isConnect(getActivity())){
            HttpRequestAPI.gameScreenShot(gameInfo.getGameId(), new HttpAsyncStringResult() {
                public void process(String response) {
                    imageContainer = (LinearLayout) rootView.findViewById(R.id.imageContainer);
                    imageContainer.removeAllViews();
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json = (JSONObject) jsonArray.opt(i);
                            initScreenShot(json.getString("screenUrl"));
                        }
                        if(jsonArray.length()>0){
                            rootView.findViewById(R.id.imageHorizontalView).setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                updateCount(5);
                break;
            case R.id.btn_un_play:
                updateCount(6);
                break;
        }
    }

    private void initUI(View rootView) {

        btn_play = (TextView) rootView.findViewById(R.id.btn_play);
        btn_un_play = (TextView) rootView.findViewById(R.id.btn_un_play);

        btn_play.setOnClickListener(this);
        btn_un_play.setOnClickListener(this);

        boolean isPlay = SharedPreferenceUtil.getInstance(getActivity()).getBoolean(playKey, true);
        setPlayInfo(isPlay);
        if (Strings.isEmpty(gameInfo.getSummery())) {
            ((TextView) rootView.findViewById(R.id.game_desc)).setText("暂无相关游戏说明信息!");
        } else {
            ((TextView) rootView.findViewById(R.id.game_desc)).setText(gameInfo.getSummery());
        }
        ((TextView) rootView.findViewById(R.id.game_guide)).setText("暂无相关游戏指引信息!");
    }

    private void updateCount(final int type) {
//        if(isSummitPlay){
//            UIHelp.showToast(getActivity(), "你已经提交过");
//        }else{
        final boolean isPlay = (type == 5);
        if (isPlay) {
            gameInfo.setPlayCount(gameInfo.getPlayCount() + 1);
            DBHelper.updateGameStatCount(getActivity(), 5, gameInfo);
        } else {
            gameInfo.setUnPlayCount(gameInfo.getUnPlayCount() + 1);
            DBHelper.updateGameStatCount(getActivity(), 6, gameInfo);
        }
        setPlayInfo(isPlay);
        HttpRequestAPI.updateCount(type, gameInfo.getGameId(), new HttpAsyncStringResult() {
            @Override
            public void process(String response) {
                if (Constants.SUCCESS.equals(response)) {
                    SharedPreferenceUtil.getInstance(getActivity()).putBoolean(playKey, isPlay);
                    UIHelp.showToast(MyApplication.getInstance(), "提交成功");
                }
            }
        });
        //}
    }

    private void setPlayInfo(boolean isPlay) {
        btn_play.setText("可以玩(" + gameInfo.getPlayCount() + ")");
        btn_un_play.setText("不可玩(" + gameInfo.getUnPlayCount() + ")");
        if (isPlay) {
            btn_play.setTextColor(getResources().getColor(R.color.white));
            btn_un_play.setTextColor(getResources().getColor(R.color.light_blank));
            btn_play.setBackgroundColor(getResources().getColor(R.color.code_green));
            btn_un_play.setBackgroundColor(getResources().getColor(R.color.light_gray));
        } else {
            btn_un_play.setTextColor(getResources().getColor(R.color.white));
            btn_un_play.setBackgroundColor(getResources().getColor(R.color.red));
            btn_play.setTextColor(getResources().getColor(R.color.light_blank));
            btn_play.setBackgroundColor(getResources().getColor(R.color.light_gray));
        }
    }
}
