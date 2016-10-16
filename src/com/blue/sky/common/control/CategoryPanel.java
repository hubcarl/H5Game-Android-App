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
public class CategoryPanel extends LinearLayout implements View.OnClickListener{

    protected final Context ctx;
    protected LayoutInflater mInflater;
    protected View rootView;
    protected List<Category> list = new ArrayList<Category>();
    protected List<TextView> textList = new ArrayList<TextView>();
    protected TextView txOne;
    protected TextView txTwo;
    protected TextView txThree;
    protected TextView txFour;
    protected TextView txFile;
    protected TextView txSix;

    public CategoryPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
        this.mInflater = LayoutInflater.from(context);
        rootView = mInflater.inflate(R.layout.sky_activity_category_panel_item, this);
        txOne = (TextView)rootView.findViewById(R.id.nav_list_item_one);
        txTwo = (TextView)rootView.findViewById(R.id.nav_list_item_two);
        txThree = (TextView)rootView.findViewById(R.id.nav_list_item_three);
        txFour = (TextView)rootView.findViewById(R.id.nav_list_item_four);
        txFile = (TextView)rootView.findViewById(R.id.nav_list_item_five);
        txSix = (TextView)rootView.findViewById(R.id.nav_list_item_six);

        textList.add(txOne);
        textList.add(txTwo);
        textList.add(txThree);
        textList.add(txFour);
        textList.add(txFile);
        textList.add(txSix);

        txOne.setOnClickListener(this);
        txTwo.setOnClickListener(this);
        txThree.setOnClickListener(this);
        txFour.setOnClickListener(this);
        txFile.setOnClickListener(this);
        txSix.setOnClickListener(this);
    }

    public void go(int index)
    {
        if(index<this.list.size()){
            Intent intent = new Intent(ctx, SimpleListActivity.class);
            intent.putExtra("category", this.list.get(index));
            ctx.startActivity(intent);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId()){
            case R.id.nav_list_item_one:
                go(0);
                break;
            case R.id.nav_list_item_two:
                go(1);
                break;
            case R.id.nav_list_item_three:
                go(2);
                break;
            case R.id.nav_list_item_four:
                go(3);
                break;
            case R.id.nav_list_item_five:
                go(4);
                break;
            case R.id.nav_list_item_six:
                go(5);
                break;
        }
    }

    public void refresh(List<Category> categoryList)
    {
        this.list = categoryList;
        int size = this.list.size();
        for(int i=0;i<size && i<6;i++){
            Category item = list.get(i);
            TextView tx = textList.get(i);
            tx.setText(item.title1);
        }
    }
}
