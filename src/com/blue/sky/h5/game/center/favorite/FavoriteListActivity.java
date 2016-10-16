package com.blue.sky.h5.game.center.favorite;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.blue.sky.common.activity.BaseActivity;
import com.blue.sky.common.adapter.CommonListAdapter;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.common.http.HttpAsyncResult;
import com.blue.sky.common.http.HttpRequestAPI;
import com.blue.sky.common.utils.NetWorkHelper;
import com.blue.sky.common.utils.SystemUtil;
import com.blue.sky.h5.game.detail.DetailActivity;
import com.blue.sky.h5.game.R;
import com.blue.sky.control.pullrefresh.PullToRefreshBase;
import com.blue.sky.control.pullrefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListActivity extends BaseActivity {

    private PullToRefreshListView mPullRefreshListView;
    private CommonListAdapter listAdapter;
    private List<GameInfo> list = new ArrayList<GameInfo>();

    public FavoriteListActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sky_activity_favorite_list);

        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
        mPullRefreshListView.setEmptyView(findViewById(R.id.empty));
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
                Intent intent = new Intent(FavoriteListActivity.this, DetailActivity.class);
                intent.putExtra("item", list.get(position - 1));
                startActivity(intent);
            }
        });

        listAdapter = new CommonListAdapter(FavoriteListActivity.this, list, 2);
        actualListView.setAdapter(listAdapter);
        setHeader("我的收藏", true);
        loadData();
    }


    private void loadData() {
        String uniqueId = SystemUtil.getUniqueId();
        if (NetWorkHelper.isConnect(this)) {
            mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
            HttpRequestAPI.getFavoriteList(uniqueId, new HttpAsyncResult() {
                        @Override
                        public void process(List<GameInfo> list) {
                            refresh(list);
                        }
                    }
            );
        } else {
            showToast("没有网络, 请检查网络设置!");
        }
    }

    private void refresh(List<GameInfo> tempList) {
        if (tempList.size() > 0) {
            list.addAll(tempList);
            listAdapter.notifyDataSetChanged();
        }
        mPullRefreshListView.onRefreshComplete();

        if (list.isEmpty()) {
            setPageEmpty();
            mPullRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
        }
    }
}
