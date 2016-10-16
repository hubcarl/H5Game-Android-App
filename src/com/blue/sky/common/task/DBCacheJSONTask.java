package com.blue.sky.common.task;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.blue.sky.common.db.DBHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/7/27.
 */
public class DBCacheJSONTask extends AsyncTask<JSONArray, Void, Boolean> {

    private Context context;
    private String tabName;
    private String[] tabCols;
    private String[] primaryKeys;

    public DBCacheJSONTask(Context context, String tabName, String[] tabCols, String[] primaryKeys) {
        this.context = context;
        this.tabName = tabName;
        this.tabCols = tabCols;
        this.primaryKeys = primaryKeys;
    }

    @Override
    protected Boolean doInBackground(JSONArray... params) {
        List<ContentValues> listValue = new ArrayList<ContentValues>();
        JSONArray jsonArray = params[0];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = (JSONObject) jsonArray.opt(i);
            ContentValues content = new ContentValues();
            for (String colName : tabCols) {
                try {
                    content.put(colName, json.getString(colName));
                } catch (JSONException e) {
                    Log.e(">>>>>>>>>>>>>codestudy json error:", e.toString());
                }
            }
            listValue.add(content);
        }
        if (listValue.size() > 0) {
            DBHelper.insert(context, tabName, tabCols, primaryKeys, listValue);
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean isSuccess) {

        super.onPostExecute(isSuccess);
    }
}
