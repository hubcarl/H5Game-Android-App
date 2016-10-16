package com.blue.sky.common.control;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.blue.sky.common.activity.SimpleListActivity;
import com.blue.sky.common.adapter.CommonListAdapter;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.common.utils.ImageLoadUtil;
import com.blue.sky.common.utils.Strings;
import com.blue.sky.h5.game.category.Category;
import com.blue.sky.h5.game.detail.DetailActivity;
import com.blue.sky.h5.game.R;
import com.blue.sky.h5.game.detail.PlayActivity;

import java.util.List;

/**
 * Created by Administrator on 2014/7/27.
 */
public class MyCommonPanel extends LinearLayout {

    protected final Context ctx;
    protected LayoutInflater mInflater;
    protected View rootView;
    protected TextView btnMore;
    protected LinearLayout myContainer;
    protected CommonListAdapter commonPanelAdapter;
    protected Category category;

    public MyCommonPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
        this.mInflater = LayoutInflater.from(context);
        rootView = mInflater.inflate(R.layout.sky_activity_common_panel_extend, this);
        btnMore  = (TextView)rootView.findViewById(R.id.btn_more);
        myContainer  = (LinearLayout)rootView.findViewById(R.id.myContainer);

        btnMore.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, SimpleListActivity.class);
                intent.putExtra("category", category);
                ctx.startActivity(intent);
            }
        });
    }

    /*
     LayoutInflater 相当于一个“布局加载器”，有三种方式可以从系统中获取到该布局加载器对象，如：

    方法一： LayoutInflater.from(this);

    方法二： (LayoutInflater)this.getSystemService(this.LAYOUT_INFLATER_SERVICE);

    方法三： this.getLayoutInflater();
     */
    public void refresh(final List<GameInfo> tempList)
    {
        if (tempList != null && tempList.size() > 0) {
            myContainer.removeAllViews();
            for(int i=0;i<tempList.size();i++){
                View convertView = LayoutInflater.from(this.ctx).inflate(R.layout.sky_panel_list_item, null);
                ViewHolder holder = new ViewHolder();
                holder.index = (TextView) convertView.findViewById(R.id.item_index);
                holder.icon =(ImageView) convertView.findViewById(R.id.item_icon);
                holder.title = (TextView) convertView.findViewById(R.id.item_title);
                holder.item_category = (TextView) convertView.findViewById(R.id.item_category);
                holder.item_hitCount = (TextView) convertView.findViewById(R.id.item_hitCount);
                holder.item_ratingBar = (RatingBar) convertView.findViewById(R.id.item_ratingBar);
                holder.item_score = (TextView) convertView.findViewById(R.id.item_score);

                holder.btnPlay = (TextView) convertView.findViewById(R.id.btn_play);

                GameInfo entity = tempList.get(i);

                ImageLoadUtil.loadImage(holder.icon,entity.getGameIcon(), ImageLoadUtil.ImageStyle.PHOTO);
                holder.title.setText(entity.getShortName());
                holder.item_category.setText(entity.getCategoryName());
                if (Strings.isEmpty(entity.getCategoryName())) {
                    holder.item_hitCount.setText(entity.getHitCount() + "人玩");
                } else {
                    holder.item_hitCount.setText(" | " + entity.getHitCount() + "人玩");
                }
                holder.item_ratingBar.setRating(entity.getScore());
                holder.item_score.setText(entity.getScore() + "");
                holder.index.setText(i+"");

                holder.btnPlay.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RelativeLayout relativeLayout = (RelativeLayout)v.getParent();
                        TextView tx = (TextView)relativeLayout.findViewById(R.id.item_index);
                        int index = Integer.valueOf(tx.getText().toString());
                        Intent intent = new Intent(ctx, PlayActivity.class);
                        intent.putExtra("item", tempList.get(index));
                        ctx.startActivity(intent);
                    }
                });

                convertView.findViewById(R.id.list_item).setOnClickListener(new OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        TextView tx = (TextView)v.findViewById(R.id.item_index);
                        int index = Integer.valueOf(tx.getText().toString());
                        Intent intent = new Intent(ctx, DetailActivity.class);
                        intent.putExtra("item", tempList.get(index));
                        ctx.startActivity(intent);
                    }
                });
                myContainer.addView(convertView);
            }
        }
    }

    class ViewHolder {
        TextView index;
        ImageView icon;
        TextView title;
        TextView item_category;
        TextView item_hitCount;
        TextView item_score;
        RatingBar item_ratingBar;
        TextView btnPlay;
    }

    public void setTitle(String title){
        ((TextView)rootView.findViewById(R.id.title)).setText(title);
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public void init(String title, Category category){
        setTitle(title);
        setCategory(category);
    }

    public void init(String title){
        setTitle(title);
    }

    public void setMoreVisible(int visible)
    {
        btnMore.setVisibility(visible);
    }
}
