package com.blue.sky.h5.game.category;


import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.blue.sky.common.db.DBHelper;
import com.blue.sky.common.fragment.BaseFragment;
import com.blue.sky.common.http.HttpAsyncCategoryResult;
import com.blue.sky.common.http.HttpRequestAPI;
import com.blue.sky.common.http.HttpRequestParam;
import com.blue.sky.common.utils.Constants;
import com.blue.sky.common.utils.NetWorkHelper;
import com.blue.sky.h5.game.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryMainFragment extends BaseFragment {

    private ListView listView;
    private CategoryAdapter listAdapter;
    private List<Category> list = new ArrayList<Category>();

    public CategoryMainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sky_category_main_fragement, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        listAdapter = new CategoryAdapter(getActivity(), list);
        listView.setAdapter(listAdapter);

        loadData();
        return rootView;
    }


    private void loadData() {
        if (NetWorkHelper.isConnect(getActivity())) {
            HttpRequestAPI.getCategory(new HttpAsyncCategoryResult() {
                public void process(List<Category> categoryList) {
                    loadCategory(categoryList);
                }
            });
        } else {
            loadCategory(DBHelper.getCategoryList(getActivity()));
        }
    }


    private void loadCategory(List<Category> categoryList) {
        list.clear();
        list.addAll(categoryList);
        listAdapter.notifyDataSetChanged();
    }
}
