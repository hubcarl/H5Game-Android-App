package com.blue.sky.h5.game.detail.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.blue.sky.common.entity.Comment;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.common.fragment.BaseFragment;
import com.blue.sky.common.http.HttpAsyncCommentResult;
import com.blue.sky.common.http.HttpAsyncStringResult;
import com.blue.sky.common.http.HttpRequestAPI;
import com.blue.sky.common.utils.*;
import com.blue.sky.control.pullrefresh.PullToRefreshBase;
import com.blue.sky.control.pullrefresh.PullToRefreshListView;
import com.blue.sky.h5.game.MyApplication;
import com.blue.sky.h5.game.R;
import com.blue.sky.h5.login.LoginActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentListFragment extends BaseFragment {

    private PullToRefreshListView mPullRefreshListView;
    private CommentListAdapter listAdapter;
    private ListView actualListView;
    private List<Comment> list = new ArrayList<Comment>();

    private int pageIndex = 1;
    private int pageSize = 10;

    private RatingBar ratingBar;
    private TextView btn_rating;
    private  boolean isScore;

    private EditText editComment;
    private ImageView txtPublish;

    private GameInfo gameInfo;
    public  CommentListFragment(GameInfo gameInfo)
    {
        this.gameInfo = gameInfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sky_game_comment_list, container, false);

        initRating(rootView);
        initListView(rootView);
        loadData();

        return rootView;
    }

    private void initRating(View rootView){
        btn_rating = (TextView)rootView.findViewById(R.id.btn_rating);
        ratingBar = (RatingBar)rootView.findViewById(R.id.ratingBar);
        isScore = SharedPreferenceUtil.getInstance(getActivity()).getBoolean("score_flag_"+gameInfo.getGameId(),false);
        if(isScore){
            float myScore = SharedPreferenceUtil.getInstance(getActivity()).getFloat("score_"+gameInfo.getGameId());
            ratingBar.setRating(myScore);
            ratingBar.setIsIndicator(true);
            btn_rating.setText("已 评 分");
        }else{
            btn_rating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isScore){
                        score(ratingBar.getRating());
                    }
                }
            });
        }
    }

    private void score(final float score){
        if(score>0){
            if(NetWorkHelper.isConnect(getActivity())){
                UIHelp.showLoading(getActivity(),"正在评分,请稍候...");
                HttpRequestAPI.score(gameInfo.getGameId(),score,new HttpAsyncStringResult() {
                    @Override
                    public void process(String response) {
                        isScore = true;
                        btn_rating.setText("已 评 分");
                        ratingBar.setIsIndicator(true);
                        SharedPreferenceUtil.getInstance(getActivity()).putFloat("score_"+gameInfo.getGameId(),score);
                        SharedPreferenceUtil.getInstance(getActivity()).putBoolean("score_flag_"+gameInfo.getGameId(),true);
                        UIHelp.showToast(getActivity(),"评分成功,谢谢你的参与!");
                        UIHelp.closeLoading();
                    }
                });
            }else{
                UIHelp.showToast(getActivity(),"没有网络,请检查网络设置");
            }
        }else{
            UIHelp.showToast(getActivity(),"请选择分数");
        }
    }

    private void initListView(View rootView){

        mPullRefreshListView = (PullToRefreshListView) rootView.findViewById(R.id.pull_refresh_list);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        //mPullRefreshListView.setEmptyView(rootView.findViewById(R.id.empty));
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            // 下拉Pulling Down
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉的时候数据重置
                mPullRefreshListView.onRefreshComplete();
            }

            // 上拉Pulling Up
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 上拉的时候添加选项
                loadData();
            }
        });

        // Add an end-of-list listener
        mPullRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                //Toast.makeText(PullToRefreshListActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
            }
        });

        actualListView = mPullRefreshListView.getRefreshableView();
        registerForContextMenu(actualListView);

        listAdapter = new CommentListAdapter(getActivity(), list);
        actualListView.setAdapter(listAdapter);

        editComment = (EditText) rootView.findViewById(R.id.comment);
        txtPublish = (ImageView) rootView.findViewById(R.id.publish);
        txtPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = Strings.trim(editComment.getText().toString());
                if (Strings.isNotEmpty(msg)) {
                    String currentKey =  gameInfo.getId()+"";
                    long lastTime = SharedPreferenceUtil.getInstance(getActivity()).getLong(currentKey);
                    long currentTime = new Date().getTime()/1000;
                    if(currentTime-lastTime>15){
                        SharedPreferenceUtil.getInstance(getActivity()).putLong(currentKey,currentTime);
                        publish(msg);
                    }else{
                        UIHelp.showToast(getActivity(), "亲,你发表评论太频繁了,请稍后再评论!");
                    }
                }
            }
        });
    }



    private void loadData() {
        HttpRequestAPI.getCommentList(gameInfo.getGameId(), pageIndex, pageSize, new HttpAsyncCommentResult() {
            public void process(List<Comment> tempList) {
                pageIndex++;
                if (tempList.size() > 0) {
                    list.addAll(tempList);
                    listAdapter.notifyDataSetChanged();
                }
                mPullRefreshListView.onRefreshComplete();
                if(tempList.size()<pageSize){
                    mPullRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
                    UIHelp.showToast(getActivity(),"评论已全部加载完成");
                }
            }
        });
    }

    private void publish(String msg) {
        if (NetWorkHelper.isConnect(getActivity())) {
            UIHelp.showLoading(getActivity(), "正在提交，请稍后...");
            final Comment comment = new Comment();
            comment.setGameId(gameInfo.getGameId());
            comment.setContent(msg);
            comment.setUserId(SystemUtil.getUniqueId());
            comment.setUserName(MyApplication.Cookies.getUserInfo().getUserName());
            comment.setUserEmail(MyApplication.Cookies.getUserInfo().getUserEmail());
            comment.setUserIcon(MyApplication.Cookies.getUserInfo().getUserIcon());
            comment.setTime(TimeUtil.dateToStrLong(new Date()));
            HttpRequestAPI.addComment(comment, new HttpAsyncStringResult() {
                public void process(String response) {
                    if (Constants.SUCCESS.equals(response)) {
                        UIHelp.showToast(getActivity(), "评论发布成功!");
                        if(Strings.isEmpty(MyApplication.Cookies.getUserInfo().getUserName())){
                            comment.setUserName("我");
                        }
                        list.add(0, comment);
                        listAdapter.notifyDataSetChanged();
                        editComment.setText(Strings.EMPTY_STRING);
                        mPullRefreshListView.onRefreshComplete();
                    } else {
                        UIHelp.showToast(getActivity(), "评论发布失败,请重试!");
                    }
                    UIHelp.hideSoftInputFromWindow(editComment);
                    UIHelp.closeLoading();
                }
            });
        } else {
            UIHelp.showToast(getActivity(), "亲，没有网络!");
        }
    }
}
