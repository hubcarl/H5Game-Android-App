package com.blue.sky.h5.game.detail.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.blue.sky.common.adapter.CommonListAdapter;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.common.fragment.BaseFragment;
import com.blue.sky.common.http.HttpAsyncResult;
import com.blue.sky.common.http.HttpRequestAPI;
import com.blue.sky.common.http.HttpRequestParam;
import com.blue.sky.common.utils.Constants;
import com.blue.sky.control.pullrefresh.PullToRefreshBase;
import com.blue.sky.control.pullrefresh.PullToRefreshListView;
import com.blue.sky.h5.game.R;
import com.blue.sky.h5.game.detail.DetailActivity;
import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;
import java.util.List;

public class RecommendFragment extends BaseFragment {

    private static final AsyncHttpClient CLIENT = new AsyncHttpClient();

    private PullToRefreshListView mPullRefreshListView;
    private CommonListAdapter listAdapter;
    private List<GameInfo> list = new ArrayList<GameInfo>();
    private HttpRequestParam requestParam = new HttpRequestParam();

    private GameInfo gameInfo;

    public RecommendFragment(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sky_game_list_no_header, container, false);

        mPullRefreshListView = (PullToRefreshListView) rootView.findViewById(R.id.pull_refresh_list);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
        mPullRefreshListView.setEmptyView(rootView.findViewById(R.id.empty));
        // Set a listener to be invoked when the list should be refreshed.
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

        ListView actualListView = mPullRefreshListView.getRefreshableView();
        registerForContextMenu(actualListView);
        actualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("item", list.get(position - 1));
                startActivity(intent);
            }
        });

        listAdapter = new CommonListAdapter(getActivity(), list, 1);
        actualListView.setAdapter(listAdapter);

        loadData();

        return rootView;
    }


    private void loadData() {
        requestParam.setCategoryId(gameInfo.getCategoryId());
        requestParam.setOrderBy(Constants.ORDER_HOT);
        HttpRequestAPI.request(requestParam, new HttpAsyncResult() {
            public void process(List<GameInfo> tempList) {
                requestParam.setPageIndex();
                if (tempList.size() > 0) {
                    list.addAll(getRecommendList(tempList));
                    listAdapter.notifyDataSetChanged();
                }
                mPullRefreshListView.onRefreshComplete();
            }
        });
    }

    private List<GameInfo> getRecommendList(List<GameInfo> tempList) {
        List<GameInfo> filterGames = new ArrayList<GameInfo>();
        for (GameInfo game : tempList) {
            if (gameInfo.getGameId().equals(game.getGameId())) {
                filterGames.add(game);
            }
        }
        tempList.removeAll(filterGames);
        return tempList;
    }
}
