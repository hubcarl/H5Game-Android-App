package com.blue.sky.common.control;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.blue.sky.common.activity.SimpleListActivity;
import com.blue.sky.common.adapter.CommonListAdapter;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.common.utils.ControlUtil;
import com.blue.sky.h5.game.category.Category;
import com.blue.sky.h5.game.detail.DetailActivity;
import com.blue.sky.h5.game.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/7/27.
 */
public class CommonPanel extends LinearLayout {

    protected final Context ctx;
    protected LayoutInflater mInflater;
    protected View rootView;
    protected TextView btnMore;
    protected ListView listView;
    protected CommonListAdapter commonPanelAdapter;
    protected List<GameInfo> list = new ArrayList<GameInfo>();
    protected Category category;

    public CommonPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
        this.mInflater = LayoutInflater.from(context);
        rootView = mInflater.inflate(R.layout.sky_activity_common_panel, this);
        btnMore  = (TextView)rootView.findViewById(R.id.btn_more);
        listView  = (ListView)rootView.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ctx, DetailActivity.class);
                intent.putExtra("item", list.get(position));
                ctx.startActivity(intent);
            }
        });

        btnMore.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, SimpleListActivity.class);
                intent.putExtra("category", category);
                ctx.startActivity(intent);
            }
        });
        refresh(list);
    }

    public void refresh(List<GameInfo> tempList)
    {
        if (tempList != null && tempList.size() > 0) {
            list.clear();
            list.addAll(tempList);
        }
        if(commonPanelAdapter == null){
            commonPanelAdapter = new CommonListAdapter(ctx, list, 1);
            listView.setAdapter(commonPanelAdapter);
        }else{
            commonPanelAdapter.notifyDataSetChanged();
        }
        ControlUtil.setListViewHeightBasedOnChildren(listView);
    }

    public void setTitle(String title){
        ((TextView)rootView.findViewById(R.id.title)).setText(title);
    }

    public void init(String title){
        setTitle(title);
    }

    public void init(String title, int moduleId, int categoryId, int orderBy){
        setTitle(title);
    }

    public void setMoreVisible(int visible)
    {
        btnMore.setVisibility(visible);
    }
}
