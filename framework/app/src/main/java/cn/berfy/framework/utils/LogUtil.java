package cn.berfy.framework.utils;

import android.util.Log;

/**
 * Log
 * 
 * @author Berfy
 */
public class LogUtil {
	private static int LOGLEVEL = 10;
	private final static int VERBOSE = 1;
	private final static int DEBUG = 2;
	private final static int INFO = 3;
	private final static int WARN = 4;
	private final static int ERROR = 5;

	private static String TAG = "";

	/**
	 * @param loglevel
	 * */
	public static void setLoglevel(int loglevel) {
		LOGLEVEL = loglevel;
	}

	public static void setTag(String tag) {
		TAG = tag;
	}

	public static void v(String tag, String msg) {
		if (LOGLEVEL > VERBOSE)
			Log.v(TAG, tag + "====" + msg);
	}

	public static void d(String tag, String msg) {

		if (LOGLEVEL > DEBUG)
			Log.d(TAG, tag + "====" + msg);
	}

	public static void i(String tag, String msg) {

		if (LOGLEVEL > INFO)
			Log.i(TAG, tag + "====" + msg);
	}

	public static void w(String tag, String msg) {

		if (LOGLEVEL > WARN)
			Log.w(TAG, tag + "====" + msg);
	}

	public static void e(String tag, String msg) {

		if (LOGLEVEL > ERROR)
			Log.e(TAG, tag + "====" + msg);
	}
}
