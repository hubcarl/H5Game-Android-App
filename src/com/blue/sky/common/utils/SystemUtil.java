package com.blue.sky.common.utils;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import com.blue.sky.h5.game.MyApplication;

public class SystemUtil
{
	/****************************************************
	 * 1 (0x00000001) Android 1.0 BASE
	 * 
	 * 2 (0x00000002) Android 1.1 BASE_1_1
	 * 
	 * 3 (0x00000003) Android 1.5 CUPCAKE
	 * 
	 * 4 (0x00000004) Android 1.6 DONUT
	 * 
	 * 5 (0x00000005) Android 2.0 ECLAIR
	 * 
	 * 6 (0x00000006) Android 2.0.1 ECLAIR_0_1
	 * 
	 * 7 (0x00000007) Android 2.1 ECLAIR_MR1
	 * 
	 * 8 (0x00000008) Android 2.2 FROYO
	 * 
	 * 9 (0x00000009) Android 2.3 GINGERBREAD
	 * 
	 * 10 (0x0000000a) Android 2.3.3 GINGERBREAD_MR1
	 * 
	 * 11 (0x0000000b) Android 3.0 HONEYCOMB
	 * 
	 * 12 (0x0000000c) Android 3.1 HONEYCOMB_MR1
	 * 
	 * 13 (0x0000000d) Android 3.2 HONEYCOMB_MR2
	 ***************************************************/

	public static int getSDKVersion()
	{
		return android.os.Build.VERSION.SDK_INT;
	}
	
	public static boolean isHighVersion()
	{
		return getSDKVersion()>10;
	}

    public static String getDeviceId()
    {
        TelephonyManager tm = (TelephonyManager) MyApplication.getInstance().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static String getMobile()
    {
        TelephonyManager tm = (TelephonyManager) MyApplication.getInstance().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static String getUniqueId(){
        if(MyApplication.Cookies.isLogin()){
            return MyApplication.Cookies.getUserInfo().getUserId();
        }else{
          return getDeviceId();
        }
    }

    public static String getApkVersionCode(){
        // 获取packagemanager的实例
        PackageManager packageManager = MyApplication.getInstance().getApplicationContext().getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(MyApplication.getInstance().getApplicationContext().getPackageName(),0);
            return packInfo==null ? Strings.EMPTY_STRING : packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return Strings.EMPTY_STRING;
        }
    }

}
