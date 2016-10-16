package com.blue.sky.common.entity;

import com.blue.sky.common.http.Action;

/**
 * Created by Administrator on 2014/8/9.
 */
public class User {

    private int id;

    private String nickName;

    private String userEmail;

    private String password;

    private String mobile;

    private String icon;

    private int roleId;

    private String openid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    private String description;

    private Action.Login loginType;

    public Action.Login getLoginType() {
        return loginType;
    }

    public void setLoginType(Action.Login loginType) {
        this.loginType = loginType;
    }

    public User(){

    }

    public User(String userEmail, String password, Action.Login loginType){
        this.userEmail = userEmail;
        this.password = password;
        this.loginType = loginType;
    }

    public User(String nickName,String userEmail, String password, Action.Login loginType){
        this.nickName = nickName;
        this.userEmail = userEmail;
        this.password = password;
        this.loginType = loginType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
