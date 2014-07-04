package com.blue.sky.core.db;

import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blue.sky.common.LogUtil;
import com.blue.sky.core.db.SQLiteHelper.SQLiteTable;

public abstract class SQLiteOperation implements SQLiteTable{

	private Context context;
	private SQLiteDatabase sqliteDatabase;
	
	/**
	 * 不能使用activity的context, 因为activity销毁后会对这个有影响, 所以用Application
	 * @author 
	 * @param context
	 */
	public SQLiteOperation(Application context){
		this.context = context;
	}
	
	/**
	 * 获取数据库连接连接对象<br>
	 * <i>暂时没必要销毁, 一个App保持一个数据库连接对象就可以了</i>
	 * @return
	 */
	protected SQLiteDatabase getDatabase(){
		return SQLiteHelper.newInstance(context).getWritableDatabase();
	}
	
	protected Context getContext(){
		return this.context;
	}
	
	public void beginTransaction(){
		sqliteDatabase.beginTransaction();
	}

	public void endTransaction(){
		sqliteDatabase.endTransaction();
	}
	
	public void setTransactionSuccessful(){
		sqliteDatabase.setTransactionSuccessful();
	}
	
	protected ArrayList getList(String sql){
		LogUtil.v(">>>sql:"+sql);
		Cursor cursor = execQuery(sql);
		return cursorToList(cursor);
	}
	
	public int getCount(String tableName,String pk,String condition){
		StringBuffer sql = new StringBuffer("SELECT ");
		sql.append(pk);
		sql.append(" FROM ");
		sql.append(tableName);
		if(condition != null){
			sql.append(" WHERE 1=1 ");
			sql.append(condition);
		}
		Cursor cursor = execQuery(sql.toString());
		int count = cursor.getCount();
		
		close(cursor);
		return count;
	}
	
	public abstract Object getModel(Cursor cursor);
	
	private Cursor execQuery(String sql){
		return getDatabase().rawQuery(sql, null);
	}
	
	private ArrayList cursorToList(Cursor cursor){
		ArrayList list = new ArrayList();
		while(cursor.moveToNext()){
			Object object = getModel(cursor);
			list.add(object);
		}
		
		close(cursor);
		return list;
	}
	
	/**
	 * 关闭连接
	 * @author 
	 * @param cursor
	 */
	protected void close(Cursor cursor) {
		if(cursor != null && !cursor.isClosed()) {
			//Log.d("close Cursor");
			cursor.close();
			cursor = null;
		}
	}
	//
	public void onOpen(SQLiteDatabase db)
	{}
}
