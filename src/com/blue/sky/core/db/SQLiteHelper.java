package com.blue.sky.core.db;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.blue.sky.animate.MyApplication;
import com.blue.sky.common.LogUtil;
import com.blue.sky.common.Reflection;

public class SQLiteHelper extends SQLiteOpenHelper {

	private Context context;
	
	public SQLiteHelper(Context context) {
		super(context, DBConfig.DB_NAME, null, DBConfig.DB_VERSION);
	}
	
    private static class HelperHolder {
        public static SQLiteHelper helper = new SQLiteHelper(MyApplication.getInstance());
     }
  
	public static SQLiteHelper newInstance(Context context){
		 return HelperHolder.helper;
	}

	
	public interface SQLiteTable{
		public void onCreate(SQLiteDatabase sqliteDatabase);
		public void onUpgrade(SQLiteDatabase sqliteDatabase, int oldversion, int newversion);
		public void onOpen(SQLiteDatabase db);
	}

	// 此处完成对所有数据表的批量创建
	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase) {
		String[] tables = DBConfig.DBOPERATIONS;
		Reflection mReflection = new Reflection();
		for (int i = 0,len = tables.length; i < len; i++) {
			try {
				SQLiteOperation sqliteOperation = (SQLiteOperation)mReflection.newInstance(
						tables[i],
						new Object[]{context},
						new Class[]{Application.class});
				sqliteOperation.onCreate(sqliteDatabase);
			} catch (Exception e) {
				LogUtil.e("operate table fail! "+tables[i]);
				LogUtil.e(e);
			}
		}

	}
	
	// 当数据库版本信息出现变动时调用该方法；（onUpgrade方法中的后两个参数分别为老版本号，新版本号）
	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase, int oldversion, int newversion) {
		String[] tables = DBConfig.DBOPERATIONS;
		Reflection mReflection = new Reflection();
		for (int i = 0,len = tables.length; i < len; i++) {
			try {
				SQLiteOperation sqliteOperation = (SQLiteOperation)mReflection.newInstance(
						tables[i],
						new Object[]{context},
						new Class[]{Application.class});
				sqliteOperation.onUpgrade(sqliteDatabase, oldversion, newversion);
			} catch (Exception e) {
				LogUtil.e("update table fail! "+tables[i]);
				LogUtil.e(e);
			}

		}

		/** 以后可能替代的版本策略  **/
		// 获取所有现有数据表中数据
		// 删除所有数据表
		// 创建所有新数据表
		// 将获取的数据插入到新数据表
	}
	//
	@Override
	public void onOpen(SQLiteDatabase db)
	{
		super.onOpen(db);
	}
}
