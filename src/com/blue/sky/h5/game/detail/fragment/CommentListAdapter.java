package com.blue.sky.h5.game.detail.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.blue.sky.common.entity.Comment;
import com.blue.sky.common.utils.*;
import com.blue.sky.h5.game.MyApplication;
import com.blue.sky.h5.game.R;

import java.util.ArrayList;
import java.util.List;


public class CommentListAdapter extends BaseAdapter {

    private Context context;
    private List<Comment> list = new ArrayList<Comment>();

    public CommentListAdapter(Context context, List<Comment> list) {
        super();
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.sky_activity_comment_list_item, null);
            holder.userIcon = (ImageView) convertView.findViewById(R.id.user_logo);
            holder.userName = (TextView) convertView.findViewById(R.id.user_name);
            holder.commentContent = (TextView) convertView.findViewById(R.id.comment_content);
            holder.commentTime = (TextView) convertView.findViewById(R.id.comment_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Comment entity = (Comment) getItem(position);
        if (Strings.isNotEmpty(entity.getUserId())
                && (entity.getUserId().equals(SystemUtil.getUniqueId())
                || entity.getUserId().equals(MyApplication.Cookies.getUserInfo().getUserId()))) {
            holder.userName.setText("我");
        } else {
            holder.userName.setText(entity.getUserName());
        }
        holder.commentContent.setText(entity.getContent());
        holder.commentTime.setText("于" + TimeUtil.transDate(entity.getTime()) + "评论");
        ImageLoadUtil.loadImage(holder.userIcon, Constants.BASE_USER_LOGO + entity.getUserIcon(), ImageLoadUtil.ImageStyle.USER_MEMBER);
        return convertView;
    }

    class ViewHolder {
        ImageView userIcon;
        TextView userName;
        TextView commentContent;
        TextView commentTime;
    }

}

