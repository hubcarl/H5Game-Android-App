package com.blue.sky.common.fragment;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import com.blue.sky.h5.game.R;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/7/27.
 */
public class BaseFragment extends Fragment {

//    @Override
//    public void setMenuVisibility(boolean menuVisible) {
//        super.setMenuVisibility(menuVisible);
//        if (this.getView() != null)
//            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
//    }


    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
        startActivity(intent);
    }

    protected void startActivity(Class<?> clazz, String key, Serializable value) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    protected void startActivity(Class<?> clazz, String key, String value) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    protected void startActivity(Class<?> clazz, String key, int value) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    public void setPageEmpty(View rootView){
        rootView.findViewById(R.id.loading).setVisibility(View.GONE);
        Drawable drawable= getResources().getDrawable(R.drawable.code_empty);
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        TextView emptyText =((TextView)rootView.findViewById(R.id.emptyText));
        emptyText.setCompoundDrawables(null,drawable,null,null);
        emptyText.setText(R.string.page_empty_text);
    }
}
