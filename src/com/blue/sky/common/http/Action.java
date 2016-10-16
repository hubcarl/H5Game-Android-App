package com.blue.sky.common.http;

/**
 * Created by Administrator on 2014/7/27.
 */
public class Action {

    public static void main(String[] arg){

        System.out.println(REST.LIST);
    }
    public enum REST
    {
        LIST("编程语言"),
        HOT("最热"),
        REC("推荐"),
        LATEST("最新"),
        DETAIL("详情"),
        CATEGORY("分类"),
        LOGIN("注册"),
        REGISTER("登录"),
        COMMENT("评论"),
        FAVORITE("收藏"),
        FEEDBACK("反馈"),
        PARAMETER("参数"),
        COUNT("更新统计数"),
        SCORE("评分"),
        GAME("游戏"),
        SCREEN_SHOT("截图"),
        FREE_TEXT_IMAGE("图片文字自由列表"),
        SEARCH("搜索");


        // 成员变量
        private String name;

        // 构造方法
        private REST(String name) {
            this.name = name;
        }
    }

    public enum RESULT
    {
        DataItem("文章列表"),
        Category("分类"),
        Comment("评论");

        // 成员变量
        private String name;

        // 构造方法
        private RESULT(String name) {
            this.name = name;
        }
    }

    public enum Login
    {
        Email("电子邮件"),
        Mobile("手机"),
        QQ("QQ"),
        Sina("新浪微博");
        // 成员变量
        private String name;

        // 构造方法
        private Login(String name) {
            this.name = name;
        }
    }
}
