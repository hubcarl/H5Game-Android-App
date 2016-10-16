package com.blue.sky.common.http;

import android.util.Log;
import com.blue.sky.common.utils.Encrypt;
import com.loopj.android.http.RequestParams;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 2014/8/23.
 */
public class MyRequestParams extends RequestParams {

    public String sign(){
        StringBuilder sb = new StringBuilder();
        TreeMap<String, String> treeMap = new TreeMap<String, String>(this.urlParams);
        Iterator<Map.Entry<String, String>> it = treeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        }
        Log.d(">>>RequestParams sign before:", sb.toString());
        String sign = Encrypt.md5(sb.toString());
        Log.d(">>>RequestParams sign after:", sign);
        return sign;
    }

}
