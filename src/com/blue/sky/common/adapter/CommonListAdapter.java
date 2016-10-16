package com.blue.sky.common.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.common.utils.ImageLoadUtil;
import com.blue.sky.common.utils.Strings;
import com.blue.sky.h5.game.R;
import com.blue.sky.h5.game.detail.PlayActivity;

import java.util.ArrayList;
import java.util.List;


public class CommonListAdapter extends BaseAdapter {

    private Context context;
    private List<GameInfo> list = new ArrayList<GameInfo>();
    private int type;

    public CommonListAdapter(Context context, List<GameInfo> list, int type) {
        super();
        this.context = context;
        this.list = list;
        this.type = type;
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
        return position;
    }

    @Override
    public View getView(int position, View rootView, ViewGroup parent) {
        ViewHolder holder;
        if (rootView == null) {
            holder = new ViewHolder();
            rootView = View.inflate(context, R.layout.sky_common_list_item, null);
            holder.icon =(ImageView) rootView.findViewById(R.id.item_icon);
            holder.title = (TextView) rootView.findViewById(R.id.item_title);
            holder.item_category = (TextView) rootView.findViewById(R.id.item_category);
            holder.item_hitCount = (TextView) rootView.findViewById(R.id.item_hitCount);
            holder.item_ratingBar = (RatingBar) rootView.findViewById(R.id.item_ratingBar);
            holder.item_score = (TextView) rootView.findViewById(R.id.item_score);
            holder.btnPlay = (TextView) rootView.findViewById(R.id.btn_play);
            rootView.setTag(holder);
        } else {
            holder = (ViewHolder) rootView.getTag();
        }

        final GameInfo entity = list.get(position);
        ImageLoadUtil.loadImage(holder.icon, entity.getGameIcon(), ImageLoadUtil.ImageStyle.PHOTO);
        holder.title.setText(entity.getShortName());
        holder.item_category.setText(entity.getCategoryName());
        if (Strings.isEmpty(entity.getCategoryName())) {
            holder.item_hitCount.setText(entity.getHitCount() + "人玩");
        } else {
            holder.item_hitCount.setText(" | " + entity.getHitCount() + "人玩");
        }
        holder.item_ratingBar.setRating(entity.getScore());
        holder.item_score.setText(entity.getScore() + "");

        holder.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("item", entity);
                context.startActivity(intent);
            }
        });

        return rootView;
    }

    class ViewHolder {
        ImageView icon;
        TextView title;
        TextView item_category;
        TextView item_hitCount;
        TextView item_score;
        RatingBar item_ratingBar;
        TextView btnPlay;
    }

}