package com.blue.sky.tab.fragment;

import android.app.Fragment;

/**
 * Created by admin on 13-11-23.
 */
public class FragmentFactory {
    public static Fragment getInstanceByIndex(int index) {
        Fragment fragment = null;
        switch (index) {
            case 1:
                fragment = new AttentionFragment();
                break;
            case 2:
                fragment = new AtmeFragment();
                break;
            case 3:
                fragment = new CommentFragment();
                break;
            case 4:
                fragment = new MyListFragment();
                break;
            case 5:
                fragment = new GlobalFragment();
                break;
        }
        return fragment;
    }
}
