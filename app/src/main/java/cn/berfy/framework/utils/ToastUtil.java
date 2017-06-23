package cn.berfy.framework.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	private static ToastUtil mToastUtil;
	private Context mContext;
	private Toast mToast;

	public static ToastUtil getInstance(Context context) {
		if (mToastUtil == null) {
			mToastUtil = new ToastUtil(context);
		}
		return mToastUtil;
	}

	private ToastUtil(Context context) {
		mContext = context;
	}

	/**
	 * toast string消息,时间2秒
	 * 
	 * @param msg
	 */
	public void showToast(String msg) {
		init();
		mToast.setText(msg);
		mToast.setDuration(Toast.LENGTH_SHORT);
		mToast.show();
	}

	/**
	 * toast string消息,时间2秒
	 * 
	 * @param msg
	 */
	public void showToast(String msg, int time) {
		init();
		mToast.setText(msg);
		mToast.setDuration(time);
		mToast.show();
	}

	/**
	 * toast string消息,时间2秒
	 * 
	 * @param resId
	 */
	public void showToast(int resId) {
		init();
		mToast.setText(mContext.getResources().getString(resId));
		mToast.setDuration(Toast.LENGTH_SHORT);
		mToast.show();
	}

	private void init() {
		if (null == mToast) {
			mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
		}
	}
}
