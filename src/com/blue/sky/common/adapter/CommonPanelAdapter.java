package com.blue.sky.common.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.h5.game.R;

import java.util.ArrayList;
import java.util.List;


public class CommonPanelAdapter extends BaseAdapter {

	private Context context;
	private List<GameInfo> list = new ArrayList<GameInfo>();
	private int type;

	public CommonPanelAdapter(Context context, List<GameInfo> list, int type) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
		    convertView = View.inflate(context, R.layout.sky_common_panel_list_panel, null);
			holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.viewCount = (TextView) convertView.findViewById(R.id.view_count);
            holder.commentCount = (TextView) convertView.findViewById(R.id.comment_count);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

        GameInfo entity = (GameInfo) getItem(position);
		holder.title.setText(entity.getShortName());
        holder.viewCount.setText(entity.getHitCount());
        holder.commentCount.setText(entity.getCommentCount());
		return convertView;
	}

	class ViewHolder {
		TextView id;
		TextView title;
		TextView viewCount;
        TextView commentCount;
        TextView createTime;
	}

}

