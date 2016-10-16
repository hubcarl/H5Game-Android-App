package com.blue.sky.common.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.h5.game.R;
import com.blue.sky.h5.game.category.Category;

import java.util.ArrayList;
import java.util.List;


public class CategoryPanelAdapter extends BaseAdapter {

	private Context context;
	private List<Category> list = new ArrayList<Category>();
	private int type;

	public CategoryPanelAdapter(Context context, List<Category> list, int type) {
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
		    convertView = View.inflate(context, R.layout.sky_categaory_list_item, null);
			holder.id = (TextView) convertView.findViewById(R.id.title);
			holder.name = (TextView) convertView.findViewById(R.id.summery);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

        GameInfo entity = (GameInfo) getItem(position);
		holder.id.setText(entity.getShortName());
        holder.name.setText(entity.getSummery());
		return convertView;
	}

	class ViewHolder {
		TextView id;
		TextView name;
	}

}

