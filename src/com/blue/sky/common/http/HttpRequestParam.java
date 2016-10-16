package com.blue.sky.common.http;

/**
 * Created by Administrator on 2014/7/27.
 */
public class HttpRequestParam {

    public HttpRequestParam()
    {

    }

    public HttpRequestParam(Action.REST action)
    {
        this.action = action;
    }

    public HttpRequestParam(int freeId)
    {
        this.freeId = freeId;
    }

    public HttpRequestParam(int c, int orderBy)
    {
        this.categoryId = c;
        this.orderBy = orderBy;
    }

    public HttpRequestParam(int c, int orderBy, int pageSize)
    {
        this.categoryId = c;
        this.orderBy = orderBy;
        this.pageSize= pageSize;
    }

    private Action.REST action;

    // 分类
    private int categoryId;

    private int freeId;


    // 1 最新  2 最热
    private int orderBy = 2;

    private int pageIndex = 1;

    private int pageSize = 10;

    private String searchText;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public int getFreeId() {
        return freeId;
    }

    public void setFreeId(int freeId) {
        this.freeId = freeId;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setPageIndex() {
        this.pageIndex = this.pageIndex + 1;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Action.REST getAction() {
        return action;
    }

    public void setAction(Action.REST action) {
        this.action = action;
    }

}
