package com.blue.sky.h5.game.category;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.blue.sky.common.activity.SimpleListActivity;
import com.blue.sky.common.config.AppConstants;
import com.blue.sky.h5.game.R;
import com.wandoujia.ads.sdk.Ads;

import java.util.List;

/**
 * Created by Administrator on 2014/8/11.
 */
public class CategoryAdapter extends BaseAdapter{

    private List<Category> list;
    private Context context;

    public CategoryAdapter(Context context, List<Category> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.sky_category_list_item, null);
            holder.category_left = (LinearLayout)convertView.findViewById(R.id.category_left);
            holder.category_right = (LinearLayout)convertView.findViewById(R.id.category_right);
            holder.title1 = (TextView)convertView.findViewById(R.id.title1);
            holder.description1 = (TextView)convertView.findViewById(R.id.description1);
            holder.title2 = (TextView)convertView.findViewById(R.id.title2);
            holder.description2 = (TextView)convertView.findViewById(R.id.description2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Category category = (Category) getItem(position);
        holder.title1.setText(" " +category.title1);
        holder.description1.setText(category.description1);
        holder.title2.setText(" " +category.title2);
        holder.description2.setText(category.description2);
        holder.category_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category.categoryId1 == 10001) {
                    Ads.showAppWall(context, AppConstants.WDJ_AD_APP_LIST_GAME);
                }else if (category.categoryId2 == 10002) {
                    Ads.showAppWall(context, AppConstants.WDJ_AD_APP_LIST_SOFT);
                } else {
                    Intent intent = new Intent(context, SimpleListActivity.class);
                    intent.putExtra("categoryId", category.categoryId1);
                    intent.putExtra("categoryName", category.title1);
                    context.startActivity(intent);
                }
            }
        });

        holder.category_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SimpleListActivity.class);
                intent.putExtra("categoryId",category.categoryId2);
                intent.putExtra("categoryName",category.title2);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {

        LinearLayout category_left;
        LinearLayout category_right;
        TextView title1;
        TextView description1;

        TextView title2;
        TextView description2;
    }

}
