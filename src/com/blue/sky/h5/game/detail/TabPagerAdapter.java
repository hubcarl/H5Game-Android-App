package com.blue.sky.h5.game.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.h5.game.detail.fragment.CommentListFragment;
import com.blue.sky.h5.game.detail.fragment.GameDetailFragment;
import com.blue.sky.h5.game.detail.fragment.RecommendFragment;


public class TabPagerAdapter extends FragmentPagerAdapter {

    private Fragment gameDetailFragment;
    private Fragment commentFragment;
    private Fragment recommendFragment;

    private final String[] TITLES = {"详情", "评论", "推荐"};
    private GameInfo gameInfo;

    public TabPagerAdapter(FragmentManager fm, GameInfo gameInfo) {
        super(fm);
        this.gameInfo = gameInfo;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (gameDetailFragment == null) {
                    gameDetailFragment = new GameDetailFragment(gameInfo);
                }

                return gameDetailFragment;
            case 1:
                if (commentFragment == null) {
                    commentFragment = new CommentListFragment(gameInfo);
                }
                return commentFragment;

            case 2:
                if (recommendFragment == null) {
                    recommendFragment = new RecommendFragment(gameInfo);
                }
                return recommendFragment;
            default:
                break;
        }
        return null;
    }
}