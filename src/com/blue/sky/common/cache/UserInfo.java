package com.blue.sky.common.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.blue.sky.common.http.Action;
import com.blue.sky.common.utils.Strings;

/**
 * 用户信息实体类
 * 
 */
public class UserInfo {

	public final static String CODE_STUDY_USER = "code_study_user";

	private SharedPreferences sharedPreferences;
	private Context context;

    private String userId;
    private String userName;
    private String userEmail;
    private String password;
    private String userIcon;
    private int roleId;
    private String mobile;
    private Action.Login loginType;

	public UserInfo(Context context) {
		this.context = context;
	}

    private SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CODE_STUDY_USER, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public String getLoginType(){
        return getSharedPreferences().getString("loginType", Strings.EMPTY_STRING);
    }

    public void setLoginType(Action.Login loginType){
        this.loginType = loginType;
        Editor editor = getSharedPreferences().edit();
        editor.putString("loginType", loginType.toString());
        editor.commit();
    }

	public String getUserId() {
        return getSharedPreferences().getString("userId", Strings.EMPTY_STRING);
	}

	public void setUserId(String id) {
		this.userId = id;
		Editor editor = getSharedPreferences().edit();
		editor.putString("userId", id);
		editor.commit();
	}

	public String getUserName() {
		if(Strings.isEmpty(userName)) {
            userName = getSharedPreferences().getString("userName", Strings.EMPTY_STRING);
		}
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
		Editor editor = getSharedPreferences().edit();
		editor.putString("userName", userName);
		editor.commit();
	}

    public String getUserEmail() {
        if(Strings.isEmpty(userEmail)) {
            userEmail = getSharedPreferences().getString("userEmail", Strings.EMPTY_STRING);
        }
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        Editor editor = getSharedPreferences().edit();
        editor.putString("userEmail", userEmail);
        editor.commit();
    }

	// 密码
	public String getPassword() {
		if(Strings.isEmpty(password)) {
			password = getSharedPreferences().getString("password", Strings.EMPTY_STRING);
		}
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		Editor editor = getSharedPreferences().edit();
		editor.putString("password", password);
		editor.commit();
	}

	public String getUserIcon() {
		if(Strings.isEmpty(userIcon)) {
            userIcon = getSharedPreferences().getString("userIcon", Strings.EMPTY_STRING);
		}
		return userIcon;
	}

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
        Editor editor = getSharedPreferences().edit();
        editor.putString("userIcon", userIcon);
        editor.commit();
    }
	
	// 用户的身份(2 普通老师  3 班主任  4 级长 5 园长   )
	public int getRoleId()
	{
		if(roleId<=0) {
            roleId = Integer.valueOf(getSharedPreferences().getString("roleId", "-1"));
		}
		return roleId;
	}

	public void setRoleId(int roleId)
	{
		this.roleId = roleId;
		Editor editor = getSharedPreferences().edit();
		editor.putInt("roleId", roleId);
		editor.commit();
	}
}
