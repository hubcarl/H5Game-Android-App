package com.blue.sky.common.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.common.utils.Strings;
import com.blue.sky.h5.game.R;

import java.util.ArrayList;
import java.util.List;


public class SimpleCommonListAdapter extends BaseAdapter {

	private Context context;
	private List<GameInfo> list = new ArrayList<GameInfo>();
	private int type;

	public SimpleCommonListAdapter(Context context, List<GameInfo> list, int type) {
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
		    convertView = View.inflate(context, R.layout.sky_common_simple_list_item, null);
			holder.title = (TextView) convertView.findViewById(R.id.title);
            if(type ==1){
                holder.summery = (TextView) convertView.findViewById(R.id.summery);
            }
            holder.createTime = (TextView) convertView.findViewById(R.id.createTime);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

        GameInfo entity = (GameInfo) getItem(position);
		holder.title.setText(entity.getShortName());
        if(type ==1&& Strings.isNotEmpty(entity.getSummery())&&entity.getSummery().length()>15){
            holder.summery.setText(entity.getSummery());
            holder.summery.setVisibility(View.VISIBLE);
        }
        holder.createTime.setText(entity.getCreateTime());
		return convertView;
	}

	class ViewHolder {
		TextView id;
		TextView title;
		TextView summery;
		TextView createTime;
	}

}

