package com.blue.sky.common.activity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.blue.sky.common.adapter.CommonListAdapter;
import com.blue.sky.common.db.DBHelper;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.common.http.Action;
import com.blue.sky.common.http.HttpAsyncResult;
import com.blue.sky.common.http.HttpRequestAPI;
import com.blue.sky.common.http.HttpRequestParam;
import com.blue.sky.common.utils.Constants;
import com.blue.sky.common.utils.NetWorkHelper;
import com.blue.sky.common.utils.Strings;
import com.blue.sky.common.utils.UIHelp;
import com.blue.sky.h5.game.detail.DetailActivity;
import com.blue.sky.h5.game.R;
import com.blue.sky.control.pullrefresh.PullToRefreshBase;
import com.blue.sky.control.pullrefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class SimpleListActivity extends BaseActivity {

    private PullToRefreshListView mPullRefreshListView;
    private CommonListAdapter listAdapter;
    private List<GameInfo> list = new ArrayList<GameInfo>();
    private HttpRequestParam requestParam = new HttpRequestParam(Action.REST.LIST);
    private int categoryId;

    public SimpleListActivity(){}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sky_game_list);

        init();
        initListView();
        loadData();
    }

    private void init()
    {
      categoryId =  getIntent().getIntExtra("categoryId",-1);
      String categoryName =  getIntent().getStringExtra("categoryName");
      if(Strings.isNotEmpty(categoryName)){
        setHeader(categoryName, true);
      }
    }

    private void initListView(){
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        mPullRefreshListView.setEmptyView(findViewById(R.id.empty));
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            // 下拉Pulling Down
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mPullRefreshListView.getLoadingLayoutProxy().setReleaseLabel("下拉刷新…");
                mPullRefreshListView.getLoadingLayoutProxy().setRefreshingLabel("释放刷新…");
                mPullRefreshListView.getLoadingLayoutProxy().setPullLabel("正在刷新…");
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(UIHelp.getPullRefreshLabel(SimpleListActivity.this));
                new GetDataTask().execute();
            }

            // 上拉Pulling Up
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mPullRefreshListView.getLoadingLayoutProxy().setReleaseLabel("释放加载更多…");
                mPullRefreshListView.getLoadingLayoutProxy().setRefreshingLabel("正在加载,请稍后...");
                mPullRefreshListView.getLoadingLayoutProxy().setPullLabel("上拉加载更多...");
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
        actualListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(SimpleListActivity.this, DetailActivity.class);
                intent.putExtra("item", list.get(position-1));
                startActivity(intent);
            }
        });

        listAdapter = new CommonListAdapter(SimpleListActivity.this, list, 1);
        actualListView.setAdapter(listAdapter);

    }

    private void loadData()
    {
        requestParam.setCategoryId(categoryId);
        requestParam.setOrderBy(Constants.ORDER_HOT);
        if(NetWorkHelper.isConnect(this)){
            HttpRequestAPI.request(requestParam, new HttpAsyncResult() {
                public void process(List<GameInfo> tempList) {
                    refresh(tempList);
                }
            });
        }else{
            List<GameInfo> tempList = DBHelper.getGameList(this, requestParam);
            refresh(tempList);
        }
    }

    private void refresh(List<GameInfo> tempList){
        if(tempList.size()>0){
            requestParam.setPageIndex();
            list.addAll(tempList);
            listAdapter.notifyDataSetChanged();
        }
        mPullRefreshListView.onRefreshComplete();
        if(list.size() == 0){
            mPullRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
            findViewById(R.id.loading).setVisibility(View.GONE);
            if(NetWorkHelper.isConnect(this)){
               setPageEmpty();
            }else{
               setNetworkError(replyListener);
            }
        }
    }

    private View.OnClickListener replyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView emptyText = (TextView)findViewById(R.id.emptyText);
            emptyText.setText(R.string.loading_text);
            emptyText.setVisibility(View.VISIBLE);
            emptyText.setCompoundDrawables(null,null,null,null);
            findViewById(R.id.loading).setVisibility(View.VISIBLE);
            findViewById(R.id.liner_empty).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_retry).setVisibility(View.GONE);
            if(NetWorkHelper.isConnect(SimpleListActivity.this)){
                mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                HttpRequestAPI.request(requestParam,new HttpAsyncResult(){
                    public void process(List<GameInfo> tempList){
                        refresh(tempList);
                    }
                });
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



    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return new String[]{};
        }

        @Override
        protected void onPostExecute(String[] result) {

            listAdapter.notifyDataSetChanged();

            mPullRefreshListView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }
}
