package com.blue.sky.h5.game.rank;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.blue.sky.common.adapter.CommonListAdapter;
import com.blue.sky.common.db.DBHelper;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.common.fragment.BaseFragment;
import com.blue.sky.common.utils.Constants;
import com.blue.sky.common.utils.NetWorkHelper;
import com.blue.sky.common.utils.UIHelp;
import com.blue.sky.control.pullrefresh.PullToRefreshListView;
import com.blue.sky.h5.game.R;
import com.blue.sky.control.pullrefresh.PullToRefreshBase;
import com.blue.sky.common.http.*;
import com.blue.sky.h5.game.detail.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class RankAllFragment extends BaseFragment {

    private PullToRefreshListView mPullRefreshListView;
    private CommonListAdapter listAdapter;
    private List<GameInfo> list = new ArrayList<GameInfo>();
    private HttpRequestParam requestParam = new HttpRequestParam(0, Constants.ORDER_HOT);

    private View rootView;

    public RankAllFragment() {

    }

    // 在Handler中获取消息，重写handleMessage()方法
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 判断消息码是否为1
            if (msg.what == Constants.FINISHED) {
                refresh((List<GameInfo>) msg.obj);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(">>>CodeTabFragment", "onCreateView ");
        rootView = inflater.inflate(R.layout.sky_rank_all_tab_fragment, container, false);

        mPullRefreshListView = (PullToRefreshListView) rootView.findViewById(R.id.pull_refresh_list);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
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
        if (NetWorkHelper.isConnect(getActivity())) {
            loadHttpData();
        } else {
            loadCacheData();
        }
    }

    private void loadCacheData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<GameInfo> tempList = DBHelper.getGameList(getActivity(), requestParam);
                // 获取一个Message对象，设置what为1
                Message msg = Message.obtain();
                msg.what = Constants.FINISHED;
                msg.obj = tempList;
                // 发送这个消息到消息队列中
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void loadHttpData() {
        HttpRequestAPI.request(requestParam, new HttpAsyncResult() {
            public void process(List<GameInfo> tempList) {
                refresh(tempList);
            }
        });
    }

    private void refresh(List<GameInfo> tempList) {
        if (tempList.size() > 0) {
            list.addAll(tempList);
            requestParam.setPageIndex();
            listAdapter.notifyDataSetChanged();
            mPullRefreshListView.onRefreshComplete();
        } else if (tempList.size() < requestParam.getPageSize() && list.size() > 100) {
            mPullRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
            UIHelp.showToast(getActivity(), "全部加载完成");
        }
    }

}
