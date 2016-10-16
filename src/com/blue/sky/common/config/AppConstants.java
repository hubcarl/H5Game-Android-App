package com.blue.sky.common.config;

public class AppConstants {

    // APP_ID 替换为你的应用从官方网站申请到的合法appId

    public static String QQ_APP_ID = "1103278420";


    /** 当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 4144977484替换该 APP_KEY */
    public static final String SINA_WEIBO_APP_KEY      = "4144977484";

    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     *
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
     * 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String SINA_WEIBO_REDIRECT_URL = "http://www.sina.com";

    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。
     *
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的
     * 使用权限，高级权限需要进行申请。
     *
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     *
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String SINA_WEIBO_SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";


    public static final String WDJ_AD_APP_ID = "100015005";
    public static final String WDJ_AD_APP_Secret_Key = "4630922fbc496587af082478da65fb09";
    public static final String WDJ_AD_APP_BANNER = "b59885bbf0b40f5978c79354f650a471";
    public static final String WDJ_AD_APP_LIST_GAME = "6a2c009ca93a3adca3a8b4a8973a5e6b";
    public static final String WDJ_AD_APP_LIST_SOFT = "6a2c009ca93a3adca3a8b4a8973a5e6b";
    public static final String WDJ_AD_APP_FULLSCREEN = "ade1da606d0537095a8c6080534115a5";


}
