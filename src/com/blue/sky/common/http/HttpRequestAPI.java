package com.blue.sky.common.http;

import android.util.Log;
import com.blue.sky.common.entity.GameInfo;
import com.blue.sky.common.utils.SystemUtil;
import com.blue.sky.h5.game.MyApplication;
import com.blue.sky.common.db.SQLScript;
import com.blue.sky.common.entity.Comment;
import com.blue.sky.common.entity.User;
import com.blue.sky.common.task.DBCacheTask;
import com.blue.sky.common.utils.Constants;
import com.blue.sky.common.utils.Encrypt;
import com.blue.sky.common.utils.JsonHelper;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/7/27.
 */
public class HttpRequestAPI {

    private static final MyAsyncHttpClient CLIENT = new MyAsyncHttpClient();

    private static final String HTTP_DOMAIN = Constants.WEBSITE_DOMAIN + "/api/";

    private static final String UNIT_HTTP_DOMAIN = Constants.UNIT_HTTP_DOMAIN + "/api/";

    private static Map<Action.REST, String> interfaceMap = new HashMap<Action.REST, String>();

    static {
        interfaceMap.put(Action.REST.LIST, "list.php");
        interfaceMap.put(Action.REST.DETAIL, "detail.php");
        interfaceMap.put(Action.REST.HOT, "hot.php");
        interfaceMap.put(Action.REST.REC, "latest.php");
        interfaceMap.put(Action.REST.LATEST, "latest.php");
        interfaceMap.put(Action.REST.CATEGORY, "category.php");
        interfaceMap.put(Action.REST.LOGIN, "login.php");
        interfaceMap.put(Action.REST.REGISTER, "register.php");
        interfaceMap.put(Action.REST.COMMENT, "comment.php");
        interfaceMap.put(Action.REST.FAVORITE, "favorite.php");
        interfaceMap.put(Action.REST.SEARCH, "search.php");
        interfaceMap.put(Action.REST.FEEDBACK, "feedback.php");
        interfaceMap.put(Action.REST.PARAMETER, "parameter.php");
        interfaceMap.put(Action.REST.COUNT, "count.php");
        interfaceMap.put(Action.REST.SCORE, "score.php");
        interfaceMap.put(Action.REST.FREE_TEXT_IMAGE, "free.php");
        interfaceMap.put(Action.REST.GAME, "game.php");
        interfaceMap.put(Action.REST.SCREEN_SHOT, "screen.php");
    }

    private static void request(String url, MyRequestParams params, final HttpAsyncStringResult result) {
        Log.d(">>>HTTP url:" + url, params.toString());
        final String localUrl = url;
        CLIENT.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (responseBody != null) {
                    String response = new String(responseBody);
                    Log.d(">>>HTTP " + localUrl, response);
                    result.process(response);
                } else {

                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    private static void request(String url, MyRequestParams params, final HttpAsyncResult result) {
        Log.d(">>>HTTP url:" + url, params.toString());
        final String localUrl = url;
        CLIENT.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    String response = new String(responseBody);
                    Log.d(">>>HTTP " + localUrl, response);
                    List<GameInfo> list = JsonHelper.parse(response);
                    result.process(list);
                    new DBCacheTask(MyApplication.getInstance().getApplicationContext(),
                            SQLScript.TAB_GAME,
                            SQLScript.TAB_GAME_CACHE_COLS,
                            SQLScript.TAB_GAME_PRIMARY_KEYS).execute(response);
                } else {

                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                result.process(new ArrayList<GameInfo>());
            }
        });
    }

    /**
     * 文章列表
     *
     * @param req
     * @param result
     */
    public static void request(HttpRequestParam req, final HttpAsyncResult result) {
        MyRequestParams params = new MyRequestParams();
        params.put("action", "list");
        if(req.getCategoryId()>0){
            params.put("c", req.getCategoryId());
        }
        if(req.getFreeId()>0){
            params.put("freeId",req.getFreeId());
        }
        params.put("i", req.getPageIndex());
        params.put("p", req.getPageSize());
        params.put("o",req.getOrderBy());
        String url = HTTP_DOMAIN + interfaceMap.get(Action.REST.LIST);
        request(url, params, result);
    }

    public static void detail(long id, final HttpAsyncStringResult result) {
        MyRequestParams params = new MyRequestParams();
        params.put("action", Action.REST.DETAIL.toString().toLowerCase());
        params.put("id", id);
        String url = HTTP_DOMAIN + interfaceMap.get(Action.REST.DETAIL);
        request(url, params, result);
    }

    public static void getCategory(final HttpAsyncCategoryResult result) {
        MyRequestParams params = new MyRequestParams();
        params.put("action", "category");
        params.put("count", 12);
        String url = HTTP_DOMAIN + interfaceMap.get(Action.REST.CATEGORY);
        CLIENT.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    String response = new String(responseBody);
                    Log.d(">>>getCategory", response);
                    result.process(JsonHelper.parseChildCategory(response));
                    new DBCacheTask(MyApplication.getInstance().getApplicationContext(),
                            SQLScript.TAB_CATEGORY,
                            SQLScript.TAB_CATEGORY_COLS,
                            SQLScript.TAB_CATEGORY_PRIMARY_KEYS).execute(response);
                } else {

                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    public static void login(User user, final HttpAsyncStringResult result) {
        MyRequestParams params = new MyRequestParams();
        params.put("action", "login");
        if (user.getLoginType().equals(Action.Login.Email)) {
            params.put("userEmail", user.getUserEmail());
            params.put("userPwd", user.getPassword());
        }
        String url = UNIT_HTTP_DOMAIN + interfaceMap.get(Action.REST.LOGIN);
        request(url, params, result);
    }

    public static void register(User user, final HttpAsyncStringResult result) {
        MyRequestParams params = new MyRequestParams();
        params.put("action", "register");
        params.put("userName", user.getNickName());
        params.put("userEmail", user.getUserEmail());
        params.put("userPwd", Encrypt.md5(user.getPassword()));
        String url = UNIT_HTTP_DOMAIN + interfaceMap.get(Action.REST.REGISTER);
        request(url, params, result);
    }

    public static void openLogin(User user, final HttpAsyncStringResult result) {
        MyRequestParams params = new MyRequestParams();
        params.put("action", "register");
        params.put("userName", user.getNickName());
        params.put("userEmail", user.getUserEmail());
        params.put("openid", user.getOpenid());
        params.put("userIcon", user.getIcon());
        params.put("loginType", user.getLoginType().toString());
        String url = UNIT_HTTP_DOMAIN + interfaceMap.get(Action.REST.REGISTER);
        request(url, params, result);
    }

    public static void addComment(Comment comment, final HttpAsyncStringResult result) {
        MyRequestParams params = new MyRequestParams();
        params.put("action", "add");
        params.put("gameId", comment.getGameId());
        params.put("comment", comment.getContent());
        params.put("userId", comment.getUserId());
        params.put("userName", comment.getUserName());
        String url = HTTP_DOMAIN + interfaceMap.get(Action.REST.COMMENT);
        request(url, params, result);
    }

    public static void getCommentList(String gameId, int pageIndex, int pageSize, final HttpAsyncCommentResult result) {
        MyRequestParams params = new MyRequestParams();
        params.put("action", "list");
        params.put("gameId", gameId);
        params.put("page", pageIndex);
        params.put("size", pageSize);
        String url = HTTP_DOMAIN + interfaceMap.get(Action.REST.COMMENT);
        CLIENT.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    String response = new String(responseBody);
                    Log.d("getCommentList", response);
                    result.process(JsonHelper.parseComment(response));
                } else {

                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    public static void addFavorite(GameInfo gameInfo, String userId, final HttpAsyncStringResult result) {
        MyRequestParams params = new MyRequestParams();
        params.put("action", "add");
        params.put("gameId", gameInfo.getGameId());
        params.put("userId", userId);
        String reqUrl = HTTP_DOMAIN + interfaceMap.get(Action.REST.FAVORITE);
        request(reqUrl, params, result);
    }

    public static void getFavoriteList(String userId, HttpAsyncResult result) {
        MyRequestParams params = new MyRequestParams();
        params.put("action", "list");
        params.put("userId", userId);
        String url = HTTP_DOMAIN + interfaceMap.get(Action.REST.FAVORITE);
        request(url, params, result);
    }

    public static void search(int moduleId, int categoryId, String searchText, final HttpAsyncResult result) {
        MyRequestParams params = new MyRequestParams();
        //params.put("m", moduleId > -1 ? moduleId : "");
        //params.put("c", categoryId > -1 ? categoryId : "");
        params.put("text", searchText);
        params.put("count", 50);

        String url = HTTP_DOMAIN + interfaceMap.get(Action.REST.SEARCH);
        request(url, params, result);

    }

    public static void feedback(String content,String contact, HttpAsyncStringResult result) {
        MyRequestParams params = new MyRequestParams();
        params.put("action", "feedback");
        params.put("message", content);
        params.put("contact", contact);
        params.put("from", "h5game_android");
        String url = UNIT_HTTP_DOMAIN + interfaceMap.get(Action.REST.FEEDBACK);
        request(url, params, result);
    }

    public static void parameter(String paramId, HttpAsyncStringResult result) {
        MyRequestParams params = new MyRequestParams();
        params.put("action", "parameter");
        params.put("paramId", paramId);
        String url = UNIT_HTTP_DOMAIN + interfaceMap.get(Action.REST.PARAMETER);
        request(url, params, result);
    }

    /**
     * 更新统计数
     * @param type 1  点击数  2 评论数  3 赞  4 贬  5 可玩  6  不可玩
     * @param gameId
     * @param result
     */
    public static void updateCount(int type, String gameId, HttpAsyncStringResult result) {
        MyRequestParams params = new MyRequestParams();
        params.put("gameId", gameId);
        params.put("type", type);
        String url = HTTP_DOMAIN + interfaceMap.get(Action.REST.COUNT);
        request(url, params, result);
    }


    public static void score(String gameId, float score, HttpAsyncStringResult result) {
        MyRequestParams params = new MyRequestParams();
        params.put("gameId", gameId);
        params.put("score", score+"");
        params.put("userId", SystemUtil.getUniqueId());
        String url = HTTP_DOMAIN + interfaceMap.get(Action.REST.SCORE);
        request(url, params, result);
    }

    public static void freeImage(int freeId, int size, final HttpAsyncStringResult result) {
        MyRequestParams params = new MyRequestParams();
        params.put("action", "image");
        params.put("freeId", freeId);
        params.put("size", size);
        String url = HTTP_DOMAIN + interfaceMap.get(Action.REST.FREE_TEXT_IMAGE);
        request(url, params, result);
    }


    public static void gameScreenShot(String gameId, final HttpAsyncStringResult result) {
        MyRequestParams params = new MyRequestParams();
        params.put("action", "screenShot");
        params.put("gameId", gameId);
        String url = HTTP_DOMAIN + interfaceMap.get(Action.REST.SCREEN_SHOT);
        request(url, params, result);
    }

    public static void game(String gameId, final HttpAsyncResult result) {
        MyRequestParams params = new MyRequestParams();
        params.put("action", "game");
        params.put("gameId", gameId);
        String url = HTTP_DOMAIN + interfaceMap.get(Action.REST.GAME);
        request(url, params, result);
    }

}
