package com.blue.sky.common;


public class LogUtil {

	private static boolean debug = true;//打包改成false
	private static String TAG;

	public static boolean isDebug() {
		return debug;
	}

	public static void v(String msg) {
		if (debug) {
			String s = buildMessage(msg);
			android.util.Log.v(TAG, s);
		}
	}

	public static void v(Throwable e) {
		if (debug) {
			String s = buildMessage();
			android.util.Log.v(TAG, s, e);
		}
	}

	public static void v(String msg, Throwable e) {
		if (debug) {
			String s = buildMessage(msg);
			android.util.Log.v(TAG, s, e);
		}
	}

	public static void d(String msg) {
		if (debug) {
			String s = buildMessage(msg);
			android.util.Log.d(TAG, s);
		}
	}

	public static void d(Throwable e) {
		if (debug) {
			String s = buildMessage();
			android.util.Log.d(TAG, s, e);
		}
	}

	public static void d(String msg, Throwable e) {
		if (debug) {
			String s = buildMessage(msg);
			android.util.Log.d(TAG, s, e);
		}
	}

	public static void i(String msg) {
		if (debug) {
			String s = buildMessage(msg);
			android.util.Log.i(TAG, s);
		}
	}

	public static void i(String msg, Throwable e) {
		if (debug) {
			String s = buildMessage(msg);
			android.util.Log.i(TAG, s, e);
		}
	}

	public static void i(Throwable e) {
		if (debug) {
			String s = buildMessage();
			android.util.Log.i(TAG, s, e);
		}
	}

	public static void w(String msg) {
		if (debug) {
			String s = buildMessage(msg);
			android.util.Log.w(TAG, s);
		}
	}

	public static void w(Throwable e) {
		if (debug) {
			String s = buildMessage();
			android.util.Log.w(TAG, s, e);
		}
	}

	public static void w(String msg, Throwable e) {
		if (debug) {
			String s = buildMessage(msg);
			android.util.Log.w(TAG, s, e);
		}
	}

	public static void e(String msg) {
		if (debug) {
			String s = buildMessage(msg);
			android.util.Log.e(TAG, s);
		}
	}

	public static void e(Throwable e) {
		if (debug) {
			String s = buildMessage();
			android.util.Log.e(TAG, s, e);
		}
	}

	public static void e(String msg, Throwable e) {
		if (debug) {
			String s = buildMessage(msg);
			android.util.Log.e(TAG, s, e);
		}
	}

	protected static String buildMessage() {
		return buildMessage("");
	}

	protected static String buildMessage(String msg) {
		StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
		
		TAG = caller.getClassName();
		return String.format(
				"[method:%s, lineNumber:%d]:%s",
				new Object[] { caller.getMethodName(), caller.getLineNumber(), msg });
	}
}