package com.blue.sky.common.control;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.blue.sky.common.activity.SimpleListActivity;
import com.blue.sky.h5.game.R;
import com.blue.sky.h5.game.category.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/7/27.
 */
public class CategoryNavPanel extends LinearLayout implements View.OnClickListener{

    protected final Context ctx;
    protected LayoutInflater mInflater;
    protected View rootView;
    protected List<Category> list = new ArrayList<Category>();
    protected List<TextView> textList = new ArrayList<TextView>();
    protected TextView txOne;
    protected TextView txTwo;
    protected TextView txThree;
    protected TextView txFour;

    public CategoryNavPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
        this.mInflater = LayoutInflater.from(context);
        rootView = mInflater.inflate(R.layout.sky_merge_category_nav_panel, this);
        txOne = (TextView)rootView.findViewById(R.id.tab_nav_one);
        txTwo = (TextView)rootView.findViewById(R.id.tab_nav_two);
        txThree = (TextView)rootView.findViewById(R.id.tab_nav_three);
        txFour = (TextView)rootView.findViewById(R.id.tab_nav_four);

        textList.add(txOne);
        textList.add(txTwo);
        textList.add(txThree);
        textList.add(txFour);

        for(TextView tx : textList){
            tx.setOnClickListener(this);
        }
    }

    public void go(int index)
    {
        if(index<this.list.size()){
            Category item = this.list.get(index);
            Intent intent = new Intent(ctx, SimpleListActivity.class);
            intent.putExtra("categoryId", item.categoryId1);
            intent.putExtra("categoryName", item.title1);
            ctx.startActivity(intent);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId()){
            case R.id.tab_nav_one:
                go(0);
                break;
            case R.id.tab_nav_two:
                go(1);
                break;
            case R.id.tab_nav_three:
                go(2);
                break;
            case R.id.tab_nav_four:
                go(3);
                break;
        }
    }

    public void init(List<Category> categoryList)
    {
        this.list = categoryList;
        int size = this.list.size();
        for(int i=0;i<size && i<4;i++){
            Category item = list.get(i);
            TextView tx = textList.get(i);
            tx.setText(item.title1);
        }
    }
}
