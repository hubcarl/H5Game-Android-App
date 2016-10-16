package com.blue.sky.common.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/10/4.
 */
public class Free implements Serializable{

    private int id;

    private String title;

    private String thumbUrl;

    private String screenShotUrl;

    private String gameId;

    private String href;

    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getScreenShotUrl() {
        return screenShotUrl;
    }

    public void setScreenShotUrl(String screenShotUrl) {
        this.screenShotUrl = screenShotUrl;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
