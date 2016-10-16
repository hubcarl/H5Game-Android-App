package com.blue.sky.common.control;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.common.utils.ImageLoadUtil;
import com.blue.sky.common.utils.Strings;
import com.blue.sky.h5.game.R;
import com.blue.sky.h5.game.detail.PlayActivity;

/**
 * Created by Administrator on 2014/7/27.
 */
public class GameInfoPanel extends LinearLayout {

    protected final Context ctx;
    protected LayoutInflater mInflater;
    protected View rootView;
    protected ImageView icon;
    protected TextView title;
    protected TextView item_category;
    protected TextView item_hitCount;
    protected RatingBar item_ratingBar;
    protected TextView item_score;
    protected TextView btnPlay;

    public GameInfoPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
        this.mInflater = LayoutInflater.from(context);
        rootView = mInflater.inflate(R.layout.sky_game_item, this);

        icon = (ImageView) rootView.findViewById(R.id.item_icon);
        title = (TextView) rootView.findViewById(R.id.item_title);
        item_category = (TextView) rootView.findViewById(R.id.item_category);
        item_hitCount = (TextView) rootView.findViewById(R.id.item_hitCount);
        item_ratingBar = (RatingBar) rootView.findViewById(R.id.item_ratingBar);
        item_score = (TextView) rootView.findViewById(R.id.item_score);
        btnPlay = (TextView) rootView.findViewById(R.id.btn_play);
    }

    public void setGameInfo(final GameInfo entity) {
        if (entity != null) {
            ImageLoadUtil.loadImage(icon, entity.getGameIcon(), ImageLoadUtil.ImageStyle.PHOTO);

            title.setText(entity.getShortName());
            item_category.setText(entity.getCategoryName());
            if (Strings.isEmpty(entity.getCategoryName())) {
                item_hitCount.setText(entity.getHitCount() + "人玩");
            } else {
                item_hitCount.setText(" | " + entity.getHitCount() + "人玩");
            }
            item_ratingBar.setRating(entity.getScore());
            item_score.setText(entity.getScore() + "");

            btnPlay.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ctx, PlayActivity.class);
                    intent.putExtra("item", entity);
                    ctx.startActivity(intent);
                }
            });
        }
    }
}
