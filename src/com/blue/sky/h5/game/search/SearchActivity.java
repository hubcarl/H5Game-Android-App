package com.blue.sky.h5.game.search;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import com.blue.sky.common.activity.BaseActivity;
import com.blue.sky.common.adapter.CommonListAdapter;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.common.http.HttpAsyncResult;
import com.blue.sky.common.http.HttpRequestAPI;
import com.blue.sky.common.utils.NetWorkHelper;
import com.blue.sky.common.utils.Strings;
import com.blue.sky.common.utils.UIHelp;
import com.blue.sky.h5.game.R;
import com.blue.sky.h5.game.detail.DetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索
 *
 * @author BlueSky
 */
public class SearchActivity extends BaseActivity {

    private ListView listView;

    private CommonListAdapter commonListAdapter;

    private List<GameInfo> list = new ArrayList<GameInfo>();

    private ImageView btnSearch;

    private EditText txtSearch;

    private int selectedModuleId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sky_activity_search);

        setHeader("搜索", true);

        txtSearch = (EditText) SearchActivity.this.findViewById(R.id.txtSearch);
        btnSearch = (ImageView) this.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(btnSearchOnClickListener);

        listView = (ListView) this.findViewById(R.id.articleList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(DetailActivity.class, "item", list.get(position));
            }
        });

    }

    private void updateListView() {
        if (commonListAdapter == null) {
            commonListAdapter = new CommonListAdapter(this, list, 2);
            listView.setAdapter(commonListAdapter);
        } else {
            commonListAdapter.notifyDataSetChanged();
        }
    }

    private View.OnClickListener btnSearchOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            list.clear();
            hideSoftInput();
            String searchText = txtSearch.getText().toString();
            if (Strings.isNotEmpty(searchText)) {
                UIHelp.showLoading(SearchActivity.this, "正在搜索，请稍后...");
                if (NetWorkHelper.isConnect(SearchActivity.this)) {
                    HttpRequestAPI.search(selectedModuleId, -1, Strings.filterSpecialChar(searchText),
                            new HttpAsyncResult() {
                                public void process(List<GameInfo> tempList) {
                                    UIHelp.closeLoading();
                                    if (tempList.size() > 0) {
                                        list.addAll(tempList);
                                        updateListView();
                                    }
                                }
                            }
                    );
                } else {
//                    HttpRequestParam searchParams = new HttpRequestParam(0,2,50);
//                    searchParams.setSearchText(searchText);
//                    List<GameInfo> tempList = DBHelper.getGameList(SearchActivity.this, searchParams);
//                    if (tempList.size() > 0) {
//                        list.addAll(tempList);
//                        updateListView();
//                    }
                   UIHelp.closeLoading();
                }
            }

        }
    };

}
