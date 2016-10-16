package com.blue.sky.common.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/7/26.
 */
public class GameInfo implements Serializable {

    private long id;

    private String gameId;

    private String shortName;

    private String longName;

    private String gameIcon;

    private String gameUrl;

    private String summery="";

    private float score;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    private int hitCount = 1;

    private int commentCount = 0;

    private String  createTime;

    private String userId;

    private int playCount;

    private int unPlayCount;

    private boolean isRecommend;

    public boolean isRecommend() {
        return isRecommend;
    }

    public void setRecommend(boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public int getUnPlayCount() {
        return unPlayCount;
    }

    public void setUnPlayCount(int unPlayCount) {
        this.unPlayCount = unPlayCount;
    }

    public int getVoteUp() {
        return voteUp;
    }

    public void setVoteUp(int voteUp) {
        this.voteUp = voteUp;
    }

    public int getVoteDown() {
        return voteDown;
    }

    public void setVoteDown(int voteDown) {
        this.voteDown = voteDown;
    }

    private int voteUp;

    private int voteDown;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private int categoryId;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    private String categoryName;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getGameIcon() {
        return gameIcon;
    }

    public void setGameIcon(String gameIcon) {
        this.gameIcon = gameIcon;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }

    public String getSummery() {
        return summery;
    }

    public void setSummery(String summery) {
        this.summery = summery;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString(){
        return "gameId:" + gameId + "shortName:" + shortName + " score:" + score + " hitCount:" + hitCount + "playCount:" + playCount + " unPlayCount:" + unPlayCount;
    }
}
