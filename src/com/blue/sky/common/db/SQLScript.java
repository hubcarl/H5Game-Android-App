package com.blue.sky.common.db;

import java.util.HashMap;
import java.util.Map;

public class SQLScript
{

	public final static int DB_VERSION = 1;

	public final static String DB_NAME = "H5Game.db";

	public final static String TAB_GAME = "h5_game";

    public final static String[] TAB_GAME_PRIMARY_KEYS ={"id"};

    public final static String TAB_CATEGORY= "h5_category";

	public final static Map<String, String[]> TAB_MAP = new HashMap<String, String[]>();

	public final static Map<String, String> TAB_KEY_MAP = new HashMap<String, String>();

    public final static String[] TAB_GAME_COLS = { "id", "gameId", "shortName", "longName", "gameIcon", "gameUrl",
            "categoryId", "categoryName", "score", "hitCount", "commentCount", "voteUp","voteDown","playCount",
            "unPlayCount","summary","createTime", "sortOrder", "status"};

	public final static String[] TAB_GAME_CACHE_COLS = { "id", "gameId", "shortName", "longName", "gameIcon", "gameUrl",
            "categoryId", "categoryName", "score", "hitCount", "commentCount", "voteUp","voteDown","playCount",
            "unPlayCount", "summary","createTime", "sortOrder", "status"};

    public final static String[] TAB_CATEGORY_COLS={"id","name","count","orderId","status","description"};

    public final static String[] TAB_CATEGORY_PRIMARY_KEYS={"id"};


	static
	{
		TAB_MAP.put(TAB_GAME, TAB_GAME_COLS);
		TAB_KEY_MAP.put(TAB_GAME, "gameId");
	}

	public static String getGameTabScript()
	{

		StringBuilder sb = new StringBuilder();

		sb.append("CREATE TABLE IF NOT EXISTS h5_game(");
		sb.append("id integer primary key,");
		sb.append("gameId nvarchar(16),");
        sb.append("shortName nvarchar(64),");
        sb.append("longName nvarchar(64),");
		sb.append("gameIcon nvarchar(512),");
		sb.append("gameUrl nvarchar(512),");
        sb.append("categoryId integer,");
        sb.append("categoryName nvarchar(64),");
        sb.append("score float,");
		sb.append("hitCount integer,");
        sb.append("voteUp integer,");
        sb.append("voteDown integer,");
        sb.append("playCount integer,");
        sb.append("unPlayCount integer,");
		sb.append("createTime datetime,");
		sb.append("summary nvarchar(256),");
		sb.append("commentCount integer default 0,");
		sb.append("sortOrder integer,");
		sb.append("status integer");
		sb.append(")");

		return sb.toString();
	}

    public static String getCategoryTabScript()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS h5_category(");
        sb.append("id integer primary key,");
        sb.append("name nvarchar(64),");
        sb.append("count integer,");
        sb.append("orderId integer,");
        sb.append("status integer,");
        sb.append("description nvarchar(128)");
        sb.append(")");
        return sb.toString();
    }

    public static String getFreeListTabScript()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS h5_free_list (");
        sb.append("id integer,");
        sb.append("freeId integer NOT NULL,");
        sb.append("freeName nvarchar (64),");
        sb.append("gameId nvarchar (32),");
        sb.append("orderId integer (4),");
        sb.append("status integer(1)");
        sb.append(")");

        return sb.toString();
    }

    public static String getUserFavoriteTabScript()
	{
		StringBuilder sb = new StringBuilder();

		sb.append("CREATE TABLE IF NOT EXISTS h5_favorite (");
		sb.append("id integer,");
		sb.append("gameId integer NOT NULL,");
		sb.append("status integer DEFAULT 1,");
		sb.append("createTime datetime DEFAULT NULL");
		sb.append(")");
		
		return sb.toString();
	}
	
	public static String getDropTable(String tabName)
	{
		return "DROP TABLE IF EXISTS "+tabName;
	}
}
