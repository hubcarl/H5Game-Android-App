package com.blue.sky.h5.game;

import android.app.Application;
import android.content.Context;
import com.blue.sky.common.cache.UserInfo;
import com.blue.sky.common.cache.UserSetting;
import com.blue.sky.common.utils.ImageLoadUtil;
import com.blue.sky.common.utils.Strings;


/**
 * 自定义 Application 类
 */
public class MyApplication extends Application {


	private static MyApplication mInstance;

	public static MyApplication getInstance() {
		return mInstance;
	}


	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;

        ImageLoadUtil.configuration(getApplicationContext());
	}

	public static class Cookies {

		private static UserInfo userInfo;

		public static UserInfo getUserInfo() {
			if (userInfo == null) {
				userInfo = new UserInfo(MyApplication.getInstance());
			}
			return userInfo;
		}

        private static UserSetting userSetting;

        public static UserSetting getUserSetting() {
            if (userSetting == null) {
                userSetting = new UserSetting(MyApplication.getInstance());
            }
            return userSetting;
        }


        public static boolean isLogin(){
            return Strings.isNotEmpty(getUserInfo().getUserId());
        }

		/**
		 * 清空用户登录信息
		 */
		public static void clear() {
			if (userInfo != null) {
				userInfo = null;
			}
			MyApplication.getInstance().getSharedPreferences(UserInfo.CODE_STUDY_USER, Context.MODE_PRIVATE).edit().clear().commit();
		}
	}

}
