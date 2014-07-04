package com.blue.sky.core.db;



/**
 * 
 * @author 
 * @attention 不要随便修改数据库版本，如果修改，请先慎重考虑是否必须要修改某业务表；并统一反馈到项目数据库文档中
 * 
 */
public class DBConfig {
	
	// 数据库名字
	public static final String DB_NAME = "bluesky2014.db";
	
	
	public static final int DB_VERSION = 12;

	// 数据库业务类（每一个不同的数据库业务类（创建数据表，修改数据表等），都需要将完整的类路径写入下方数组中）
	public static final String[] DBOPERATIONS = {
		
	};
	
}
