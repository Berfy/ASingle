package cn.berfy.framework.utils;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.Arrays;

public class GpsUtil {

	private Context mContext;
	private static GpsUtil mGpsUtil;
	// 百度的一些api 获取经纬度
	private LocationClientOption option = new LocationClientOption();
	private MyLocationListenner myListener = new MyLocationListenner();
	private LocationClient mLocClient;
	private OnGpsListener mOnGpsListener;
	private String[] mCitys = new String[] { "北京", "上海", "天津", "重庆" };
	private boolean mIsGetData = false;

	public static GpsUtil getInstances(Context context) {
		if (null == mGpsUtil) {
			mGpsUtil = new GpsUtil(context);
		}
		return mGpsUtil;
	}

	public GpsUtil(Context context) {
		mContext = context;
		Arrays.sort(mCitys);
	}

	public void setListener(OnGpsListener onGpsListener) {
		mOnGpsListener = onGpsListener;
	}

	/** 定位 */
	public void startGps() {
		LogUtil.e("定位开始", "=====");
		mIsGetData = false;
		if (null == mLocClient) {
			mLocClient = new LocationClient(mContext);
			option.setAddrType("all");
			option.setCoorType("gcj02");
			mLocClient.setLocOption(option);
			mLocClient.registerLocationListener(myListener);
		}
		mLocClient.start();
		mHandler.postDelayed(mRunnable, 5000);
	}

	Runnable mRunnable = new Runnable() {

		@Override
		public void run() {
			stopGps();
			if (!mIsGetData)
				if (null != mOnGpsListener) {
					mOnGpsListener.onError();
				}
		}
	};

	public void stopGps() {
		if (null != mLocClient) {
			LogUtil.e("定位停止", "=====");
			mLocClient.stop();
		}
	}

	private Handler mHandler = new Handler() {
	};

	/** 监听获取经纬度 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (null == location) {
				if (null != mOnGpsListener) {
					mOnGpsListener.onError();
				}
				return;
			} else {
				double longitude = location.getLongitude();
				double latitude = location.getLatitude();
				System.out.println("listener::" + longitude + "__" + latitude);
				if (String.valueOf(longitude).contains("E")
						|| String.valueOf(latitude).contains("E")) {
					if (null != mOnGpsListener) {
						mOnGpsListener.onError();
					}
					return;
				}
				if (null != mOnGpsListener) {
					String province = location.getProvince();
					String city = location.getCity();
					if (!TextUtils.isEmpty(province)) {// 如果是直辖市或者省市一样，删除省
						if (Arrays.binarySearch(mCitys, province) >= 0
								|| province.equals(location.getCity())) {
							province = "";
							city = city.replace("市", "");
						}
					} else {// 获取城市失败
						mOnGpsListener.onError();
						return;
					}
					mIsGetData = true;
					mOnGpsListener.onReceiveLocation(location.getLatitude(),
							location.getLongitude(), TextUtils
									.isEmpty(province) ? "" : province,
							TextUtils.isEmpty(city) ? "" : city, TextUtils
									.isEmpty(location.getDistrict()) ? ""
									: location.getDistrict(), TextUtils
									.isEmpty(location.getAddrStr()) ? ""
									: location.getAddrStr());
					mHandler.removeCallbacks(mRunnable);
				}
				stopGps();
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {

		}
	}

	public interface OnGpsListener {
		void onReceiveLocation(double lat, double lng, String province,
							   String city, String distrct, String address);

		void onError();
	}
}
