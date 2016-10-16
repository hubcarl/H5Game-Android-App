package com.blue.sky.common.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.common.http.HttpRequestParam;
import com.blue.sky.common.utils.Constants;
import com.blue.sky.common.utils.NetWorkHelper;
import com.blue.sky.common.utils.SharedPreferenceUtil;
import com.blue.sky.common.utils.Strings;
import com.blue.sky.h5.game.R;
import com.blue.sky.h5.game.category.Category;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 类名 DataAccess.java 
 * 说明 作者 BlueSky 
 * 版权 蓝天科技工作室 
 * 创建时间 2012-7-2
 */
public class DBHelper
{

	private final static String DATABASE_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/H5Game";

    private final static Object LOCK = new Object();

	private final static String DATABASE_FILENAME = "H5Game.db";

    private static SQLiteHelper instance;

    private static synchronized SQLiteHelper getHelper(Context context)
    {
        if (instance == null)
            instance = new SQLiteHelper(context, SQLScript.DB_NAME, null, SQLScript.DB_VERSION);

        return instance;
    }

	private static  SQLiteDatabase getDB(Context context)
	{
        boolean isReadDynamicDB = SharedPreferenceUtil.getInstance(context).getBoolean("ReadDynamicDB",false);
        if(isReadDynamicDB || NetWorkHelper.isConnect(context) || getCount(context,SQLScript.TAB_GAME, Strings.EMPTY_STRING)>0){
            Log.d(">>>db","dynamic");
            SharedPreferenceUtil.getInstance(context).putBoolean("ReadDynamicDB",true);
            return getHelper(context).getWritableDatabase();
        }else{
            Log.d(">>>db","static");
            return createDB(context);
        }
	}

	/**
	 * 从SDCARD中读取数据库
	 * 
	 * @param context
	 * @return
	 */
	private static SQLiteDatabase createDB(Context context)
	{

		try
		{

			File dir = new File(DATABASE_PATH);
			// 如果/sdcard/h5game目录中存在，创建这个目录
			if (!dir.exists())
				dir.mkdir();
			// 如果在/sdcard/h5game目录中不存在
			// h5game.db文件，则从res\raw目录中复制这个文件到
			// SD卡的目录（/sdcard/h5game）
			// 获得h5game.db文件的绝对路径
			String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
			File file = new File(databaseFilename);
			if (!file.exists())
			{
				// 获得封装h5game.db文件的InputStream对象
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[1024];
				int count = 0;
				// 开始复制h5game.db文件
				InputStream is = context.getResources().openRawResource(R.raw.h5game);
				while ((count = is.read(buffer)) > 0)
				{
					fos.write(buffer, 0, count);
				}

				fos.close();
				is.close();
			}
			// 打开/sdcard/h5game目录中的h5game.db文件
			return SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
		}
		catch (Exception e)
		{
            SharedPreferenceUtil.getInstance(context).putBoolean("ReadDynamicDB",true);
            Log.e("DB_ERROR", "打开数据库失败:" + e.getMessage());
		}
		return getHelper(context).getWritableDatabase();
	}

    private static String getPrimarySQL(String[] primaryKeys, ContentValues values){
        String strPrimarySQL= Strings.EMPTY_STRING;
        for(int i=0;i<primaryKeys.length;i++){
           String key = primaryKeys[i];
           if(i==0){
               strPrimarySQL += key+"="+values.getAsString(key);
           }else{
               strPrimarySQL += " AND " + key+"="+values.getAsString(key);
           }
        }
        return strPrimarySQL;
    }

	public static void insert(Context context, String tabName, String[] tabCols, String[] primaryKeys, List<ContentValues> listValue)
	{
        synchronized (LOCK){
		    SQLiteDatabase db = getDB(context);
            if (null != listValue)
            {
                for (ContentValues values : listValue)
                {
                    db.delete(tabName,getPrimarySQL(primaryKeys,values), null);
                    db.insert(tabName, null, values);
                }
            }
            db.close();
        }
	}

	/**
	 * 添加收藏
	 * 
	 * @param context
	 * @param contentValue
	 */
	public static void addUserCollection(Context context, ContentValues contentValue)
	{
        synchronized (LOCK){
            SQLiteDatabase db = getDB(context);

            //db.insert(SQLScript.TAB_USER_COLLECTION, null, contentValue);

            db.close();
        }
	}

    public static void addCollection(Context context, GameInfo item) {
        synchronized (LOCK){
            SQLiteDatabase db = getDB(context);
            ContentValues contentValue = new ContentValues();
            contentValue.put("ArticleId", item.getId());
            contentValue.put("Title", item.getShortName());
            contentValue.put("UserId", item.getUserId());
            contentValue.put("CreateDate", item.getCreateTime());
            contentValue.put("CategoryId", item.getCategoryId());
            contentValue.put("Status", 1);
            //db.insert(SQLScript.TAB_USER_COLLECTION, null, contentValue);
            db.close();
        }
    }


    public static List<GameInfo> getCollectionList(Context context, long userId) {
        List<GameInfo> list = new ArrayList<GameInfo>();
        synchronized (LOCK){
            SQLiteDatabase db = getDB(context);
//            Cursor cursor = db.query(SQLScript.TAB_USER_COLLECTION, SQLScript.TAB_USER_COLLECTION_COLS, null, null, null, null, "CreateDate DESC");
//            cursor.moveToFirst();
//            while (!cursor.isAfterLast()) {
//                GameInfo note = new GameInfo();
//                note.setId(cursor.getLong(0));
//                note.setShortName(cursor.getString(1));
//                note.setCreateTime(cursor.getString(4));
//                note.setUserId(cursor.getString(5));
//                list.add(note);
//                cursor.moveToNext();
//            }
//            cursor.close();
//            db.close();
        }
        return list;
    }


    public static void deleteUserCollection(Context context, String articleId)
	{
        synchronized (LOCK){
            SQLiteDatabase db = getDB(context);
            //db.delete(SQLScript.TAB_USER_COLLECTION, "ArticleId=?", new String[] { articleId });
            db.close();
        }
	}

	public static boolean isExistCollection(Context context, long articleId, long userId)
	{
       boolean isExist = false;
//        synchronized (LOCK){
//            SQLiteDatabase db = getDB(context);
//            Cursor cursor = db.query(SQLScript.TAB_USER_COLLECTION, new String[] { "ArticleId"}, "ArticleId=" + articleId + " And UserId="+userId, null,
//                    null, null, null);
//
//            cursor.moveToFirst();
//            if (!cursor.isAfterLast())
//            {
//                isExist = true;
//            }
//            cursor.close();
//            db.close();
//        }

		return isExist;
	}

	private static String getQueryWhere(HttpRequestParam requestParam)
	{
		String queryWhere = " status=1";
        String dynamicSQL=Strings.EMPTY_STRING;
        if (requestParam.getCategoryId() > 0)
        {
            dynamicSQL += " AND categoryId=" + requestParam.getCategoryId();

        }

        queryWhere  += dynamicSQL;
        String filterSearchText = Strings.filterSpecialChar(requestParam.getSearchText());
		if (Strings.isNotEmpty(filterSearchText))
		{
			queryWhere += " AND (shortName like '%" + filterSearchText + "%' OR longName like '%" + filterSearchText + "%')";
		}

        if(requestParam.getOrderBy()== Constants.ORDER_TIME){
            queryWhere += " ORDER BY createTime DESC";
        }else if(requestParam.getOrderBy()== Constants.ORDER_PLAY){
            queryWhere += " ORDER BY playCount DESC";
        }

        int pageCount = requestParam.getPageSize();
		int startIndex = pageCount * (requestParam.getPageIndex() - 1);

		queryWhere += "  Limit " + pageCount + " Offset " + startIndex;
        Log.d(">>>>>>getGameList getQueryWhere",queryWhere);
		return queryWhere;
	}

	public static List<GameInfo> getGameList(Context context, HttpRequestParam requestParam)
	{

		List<GameInfo> list = new ArrayList<GameInfo>();

		String queryWhere = getQueryWhere(requestParam);
        Log.d(">>>>>>>>>>>>>h5game SQL:", queryWhere);
        synchronized (LOCK){
            SQLiteDatabase db = getDB(context);
            Cursor cursor = db.query(SQLScript.TAB_GAME, SQLScript.TAB_GAME_CACHE_COLS, queryWhere, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                GameInfo item = new GameInfo();
                item.setId(cursor.getLong(cursor.getColumnIndex("id")));
                item.setGameId(cursor.getString(cursor.getColumnIndex("gameId")));
                item.setShortName(cursor.getString(cursor.getColumnIndex("shortName")));
                item.setLongName(cursor.getString(cursor.getColumnIndex("longName")));
                item.setGameIcon(cursor.getString(cursor.getColumnIndex("gameIcon")));
                item.setGameUrl(cursor.getString(cursor.getColumnIndex("gameUrl")));
                item.setHitCount(cursor.getInt(cursor.getColumnIndex("hitCount")));
                item.setVoteUp(cursor.getInt(cursor.getColumnIndex("voteUp")));
                item.setVoteDown(cursor.getInt(cursor.getColumnIndex("voteDown")));
                item.setHitCount(cursor.getInt(cursor.getColumnIndex("hitCount")));
                item.setScore(cursor.getInt(cursor.getColumnIndex("score")));
                item.setPlayCount(cursor.getInt(cursor.getColumnIndex("playCount")));
                item.setUnPlayCount(cursor.getInt(cursor.getColumnIndex("unPlayCount")));
                item.setCommentCount(cursor.getInt(cursor.getColumnIndex("commentCount")));
                item.setSummery(cursor.getString(cursor.getColumnIndex("summary")));
                item.setCreateTime(cursor.getString(cursor.getColumnIndex("createTime")));
                list.add(item);
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }
        Log.d(">>>>>>>>>>>>>h5game SQL result size:", list.size()+"");
		return list;
	}

    public static List<Category> getCategoryList(Context context){
        List<Category> list = new ArrayList<Category>();
        synchronized (LOCK){
            SQLiteDatabase db = getDB(context);
            Cursor cursor = db.query(SQLScript.TAB_CATEGORY, SQLScript.TAB_CATEGORY_COLS, null, null, null, null, "orderId");
            cursor.moveToFirst();
            int i=0;
            Category category = null;
            while (!cursor.isAfterLast()) {
                if(i%2==0){
                    category = new Category();
                    category.categoryId1 = cursor.getInt(cursor.getColumnIndex("id"));
                    category.title1 = cursor.getString(cursor.getColumnIndex("name"));
                    category.description1 = cursor.getString(cursor.getColumnIndex("description"));
                    list.add(category);
                }else{
                    category.categoryId2 = cursor.getInt(cursor.getColumnIndex("id"));
                    category.title2 = cursor.getString(cursor.getColumnIndex("name"));
                    category.description2 = cursor.getString(cursor.getColumnIndex("description"));
                }
                i++;
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    public static GameInfo getGame(Context context, String gameId)
    {
        GameInfo item = null;
        synchronized (LOCK){
            SQLiteDatabase db = getDB(context);
            Cursor cursor = db.query(SQLScript.TAB_GAME, SQLScript.TAB_GAME_CACHE_COLS, "gameId=?", new String[]{gameId}, null, null, null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast())
            {
                item = new GameInfo();
                item.setId(cursor.getLong(cursor.getColumnIndex("id")));
                item.setGameId(cursor.getString(cursor.getColumnIndex("gameId")));
                item.setShortName(cursor.getString(cursor.getColumnIndex("shortName")));
                item.setLongName(cursor.getString(cursor.getColumnIndex("longName")));
                item.setGameIcon(cursor.getString(cursor.getColumnIndex("gameIcon")));
                item.setGameUrl(cursor.getString(cursor.getColumnIndex("gameUrl")));
                item.setHitCount(cursor.getInt(cursor.getColumnIndex("hitCount")));
                item.setVoteUp(cursor.getInt(cursor.getColumnIndex("voteUp")));
                item.setVoteDown(cursor.getInt(cursor.getColumnIndex("voteDown")));
                item.setHitCount(cursor.getInt(cursor.getColumnIndex("hitCount")));
                item.setScore(cursor.getInt(cursor.getColumnIndex("score")));
                item.setPlayCount(cursor.getInt(cursor.getColumnIndex("playCount")));
                item.setUnPlayCount(cursor.getInt(cursor.getColumnIndex("unPlayCount")));
                item.setCommentCount(cursor.getInt(cursor.getColumnIndex("commentCount")));
                item.setSummery(cursor.getString(cursor.getColumnIndex("summary")));
                item.setCreateTime(cursor.getString(cursor.getColumnIndex("createTime")));
            }
            cursor.close();
            db.close();
        }
        return item;
    }

    public static void updateGameStatCount(Context context,int type, GameInfo gameInfo){
        String colName = null;
        switch (type){
            case 1:
                colName = "hitCount";
                break;
            case 2:
                colName = "commentCount";
                break;
            case 3:
                colName = "voteUp";
                break;
            case 4:
                colName = "voteDown";
                break;
            case 5:
                colName = "playCount";
                break;
            case 6:
                colName = "unPlayCount";
                break;
        }
        if(colName!=null){
            synchronized (LOCK){
                SQLiteDatabase db = getDB(context);
                db.execSQL("update " + SQLScript.TAB_GAME + " set "+colName+"="+colName+"+1 where gameId=?" ,new Object[]{gameInfo.getGameId()});
                db.close();
            }
        }
    }

	public static long getMaxId(Context context, String tabName, String columnId)
	{
        long maxId = 0;
        synchronized (LOCK){
            SQLiteDatabase db = getDB(context);
            Cursor cursor = db.rawQuery("select max(" + columnId + ") from " + tabName, null);
            if (cursor.moveToFirst())
            {
                maxId = cursor.getLong(0);
            }
            cursor.close();
            db.close();
        }
		return maxId;
	}

    public static void delete(Context context, String tabName, String strWhere){
        String srtSql="delete from " + tabName + " where" + strWhere;
        synchronized (LOCK){
            SQLiteDatabase db = getHelper(context).getWritableDatabase();
            db.execSQL(srtSql);
            db.close();
        }
    }

    public static int getCount(Context context, String tabName, String strWhere)
    {
        String strSql = "select count(*) from " + tabName;
        if(Strings.isNotEmpty(strWhere)){
            strSql = strSql + " where " + strWhere;
        }
        int count = 0;
        synchronized (LOCK){
            SQLiteDatabase db = getHelper(context).getWritableDatabase();
            Cursor cursor = db.rawQuery(strSql, null);
            if (cursor.moveToFirst())
            {
                count = cursor.getInt(0);
            }
            cursor.close();
            db.close();
        }
        return count;
    }
}
