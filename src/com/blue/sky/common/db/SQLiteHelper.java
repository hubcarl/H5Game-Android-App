package com.blue.sky.common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * 类名 SQLiteHelper.java
 * 说明  
 * 作者  BlueSky
 * 版权  蓝天科技工作室
 * 创建时间  2012-5-25
 */
public class SQLiteHelper extends SQLiteOpenHelper {

	public SQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQLScript.getGameTabScript());
		db.execSQL(SQLScript.getCategoryTabScript());
        db.execSQL(SQLScript.getUserFavoriteTabScript());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
