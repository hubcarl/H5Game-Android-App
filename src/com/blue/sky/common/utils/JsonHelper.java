package com.blue.sky.common.utils;

import android.util.Log;
import com.blue.sky.common.entity.Comment;
import com.blue.sky.common.entity.Free;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.h5.game.category.Category;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/7/27.
 */
public class JsonHelper {

    public static List<GameInfo> parse(String str)
    {
        List<GameInfo> list = new ArrayList<GameInfo>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(str);
            for(int i=0;i<jsonArray.length();i++){
                GameInfo item = new GameInfo();
                JSONObject json = (JSONObject)jsonArray.opt(i);
                item.setId(json.getLong("id"));
                item.setGameId(json.getString("gameId"));
                item.setShortName(json.getString("shortName"));
                item.setLongName(json.getString("longName"));
                item.setGameIcon(json.getString("gameIcon"));
                item.setGameUrl(json.getString("gameUrl"));
                item.setHitCount(json.getInt("hitCount"));
                item.setPlayCount(json.getInt("playCount"));
                item.setUnPlayCount(json.getInt("unPlayCount"));
                item.setScore(Float.valueOf(json.getString("score")));
                item.setCommentCount(json.getInt("commentCount"));
                item.setSummery(json.getString("summary"));
                item.setCreateTime(json.getString("createTime"));
                item.setCategoryId(json.getInt("categoryId"));
                item.setCategoryName(json.getString("categoryName"));
                list.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(">>>JsonHelper.parse size:", list.size()+"");
        return list;
    }

    public static List<Category> parseChildCategory(String str){
        List<Category> list = new ArrayList<Category>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(str);
            for(int i=0;i<jsonArray.length();i=i+2){

                Category item = new Category();
                JSONObject json1 = (JSONObject)jsonArray.opt(i);
                JSONObject json2 = (JSONObject)jsonArray.opt(i+1);

                item.categoryId1 = json1.getInt("id");
                item.title1 =  json1.getString("name");
                item.description1 =  json1.getString("description");

                item.categoryId2 = json2.getInt("id");
                item.title2 =  json2.getString("name");
                item.description2 =  json2.getString("description");

                list.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static List<Comment> parseComment(String response){
        List<Comment> list = new ArrayList<Comment>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(response);
            for(int i=0;i<jsonArray.length();i++){
                Comment comment = new Comment();
                JSONObject json = (JSONObject)jsonArray.opt(i);
                comment.setUserId(json.getString("userId"));
                comment.setUserName(json.getString("userName"));
                comment.setContent(json.getString("comment"));
                comment.setTime(json.getString("createTime"));
                list.add(comment);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Free> parseFreeImage(String str)
    {
        List<Free> list = new ArrayList<Free>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(str);
            for(int i=0;i<jsonArray.length();i++){
                Free item = new Free();
                JSONObject json = (JSONObject)jsonArray.opt(i);
                item.setId(json.getInt("id"));
                item.setTitle(json.getString("title"));
                item.setThumbUrl(json.getString("thumbUrl"));
                item.setScreenShotUrl(json.getString("screenshotUrl"));
                item.setGameId(json.getString("gameId"));
                item.setHref(json.getString("href"));
                list.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(">>>JsonHelper.parse size:", list.size()+"");
        return list;
    }
}
