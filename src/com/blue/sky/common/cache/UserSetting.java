package com.blue.sky.common.cache;

import android.content.Context;
import com.blue.sky.h5.game.MyApplication;
import com.blue.sky.common.utils.SharedPreferenceUtil;


/**
 * 用户设置<br>
 * 消息通知业务逻辑: 如果个人设置#消息通知关闭, 那么所有消息都不通知; 如果开启, 再判断圈子是否开启, 如果是则根据个人设置#通知类型通知,
 * 否则不通知
 * 
 */
public class UserSetting {

	public static final String SETTING_NEW_NOTIFI = "setting_new_notifi";
	public static final String SETTING_MSG_CONTENT_NOTIFI = "setting_msg_content_notifi";
	public static final String SETTING_VOICE_NOTIFI = "setting_voice_notifi";
	public static final String SETTING_SHAKE_NOTIFI = "setting_shake_notifi";

	/** 是否开启消息提醒. 0-开启; 1-关闭 */
	private boolean isNotice;

	/** 是否显示内容. 0-开启; 1-关闭 */
	private boolean isNoticeWithContent;

	/** 是否提示声音. 0-开启; 1-关闭 */
	private boolean isMusic;

	/** 是否振动 . 0-开启; 1-关闭 */
	private boolean isShake;

	private Context context;

	private SharedPreferenceUtil spUtil;

	public UserSetting(Context context) {
		this.context = context;
		spUtil = SharedPreferenceUtil.getInstance(context);
	}

	public boolean isNotice() {
		return spUtil.getBoolean(SETTING_NEW_NOTIFI + MyApplication.Cookies.getUserInfo().getUserId(), true);
	}

	public void setNotice(boolean isNotice) {
		spUtil.putBoolean(SETTING_NEW_NOTIFI + MyApplication.Cookies.getUserInfo().getUserId(), isNotice);
	}

	public boolean isNoticeWithContent() {
		return spUtil.getBoolean(SETTING_MSG_CONTENT_NOTIFI + MyApplication.Cookies.getUserInfo().getUserId(), true);
	}

	public void setNoticeWithContent(boolean isNoticeWithContent) {
		spUtil.putBoolean(SETTING_MSG_CONTENT_NOTIFI + MyApplication.Cookies.getUserInfo().getUserId(), isNoticeWithContent);
	}

	public boolean isMusic() {
		return spUtil.getBoolean(SETTING_VOICE_NOTIFI + MyApplication.Cookies.getUserInfo().getUserId(), true);
	}

	public void setMusic(boolean isMusic) {
		spUtil.putBoolean(SETTING_VOICE_NOTIFI + MyApplication.Cookies.getUserInfo().getUserId(), isMusic);
	}

	public boolean isShake() {
		return spUtil.getBoolean(SETTING_SHAKE_NOTIFI + MyApplication.Cookies.getUserInfo().getUserId(), true);
	}

	public void setShake(boolean isShake) {
		spUtil.putBoolean(SETTING_SHAKE_NOTIFI + MyApplication.Cookies.getUserInfo().getUserId(), isShake);
	}

}
