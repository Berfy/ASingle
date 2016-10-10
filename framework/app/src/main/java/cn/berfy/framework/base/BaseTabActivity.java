package cn.berfy.framework.base;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.google.gson.Gson;

import cn.berfy.framework.cache.TempSharedData;
import cn.berfy.framework.manager.ActivityManager;
import cn.berfy.framework.utils.AnimUtil;
import cn.berfy.framework.utils.DeviceUtil;
import cn.berfy.framework.utils.LogUtil;
import cn.berfy.framework.utils.ToastUtil;

public abstract class BaseTabActivity extends TabActivity implements
        OnClickListener {

    private Activity mContext;
    private Gson mGson;
    private TempSharedData mTempSharedData;
    public static final int ACTIVITY_RESUME = 0;
    public static final int ACTIVITY_STOP = 1;
    public static final int ACTIVITY_PAUSE = 2;
    public static final int ACTIVITY_DESTROY = 3;
    private int mActivityState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(getClass().getName(), "onCreate()");
        // 加入栈管理
        ActivityManager.getInstance().pushActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = this;
        if (null != getParent()) {
            mContext = getParent();
        }
        initVariable();
        initContentView();
        initView();
    }

    /**当前生命周期*/
    protected int getActivityState(){
        return mActivityState;
    }

    /**
     * 让App字体不随系统改变
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();//
        config.fontScale = 1.0f;
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    private TempSharedData getCacheUtil() {// 用到再实例化
        if (null == mTempSharedData) {
            mTempSharedData = TempSharedData.getInstance(mContext);
        }
        return mTempSharedData;
    }

    /**
     * Gson
     */
    protected Gson getGson() {
        if (null == mGson) {
            mGson = new Gson();
        }
        return mGson;
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mActivityState =ACTIVITY_RESUME;
        LogUtil.i(getClass().getName(), "onResume()");
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mActivityState =ACTIVITY_PAUSE;
        LogUtil.i(getClass().getName(), "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mActivityState =ACTIVITY_STOP;
        LogUtil.i(getClass().getName(), "onStop()");
    }

    /**
     * 该方法用于初始化页面变量
     */
    protected abstract void initVariable();

    /**
     * 设置页面布局
     */
    protected abstract void initContentView();

    /**
     * 对页面控件进行设置
     */
    protected abstract void initView();

    @Override
    public void onClick(View v) {
        doClickAction(v.getId());
    }

    /**
     * 控件点击事件
     *
     * @param viewId
     */
    protected abstract void doClickAction(int viewId);

    /**
     * 网络错误刷新
     */
    protected abstract void onNetworkErrorClickRefresh();

    private long mClickTime = 0;

    /**
     * 跳转Activity+动画
     */
    public void startActivityWithAnim(Intent intent) {
        if (System.currentTimeMillis() - mClickTime > 1000) {
            LogUtil.e("跳转", "====" + intent.getComponent());
            mClickTime = System.currentTimeMillis();
            startActivity(intent);
            AnimUtil.pushLeftInAndOut(mContext);
        } else {
            LogUtil.e("等待跳转", "====");
        }
    }

    /**
     * 物理返回键执行的动作
     */
    protected void doBackAction() {
        finish();
        AnimUtil.pushRightInAndOut(mContext);
    }

    /**
     * toast string消息,时间2秒
     *
     * @param msg
     */
    protected void showToast(String msg) {
        ToastUtil.getInstance(mContext).showToast(msg);
    }

    /**
     * toast 资源中的消息,时间2秒
     *
     * @param resId
     */
    protected void showToast(int resId) {
        ToastUtil.getInstance(mContext).showToast(resId);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        DeviceUtil.closeKeyboard(mContext, getWindow().getDecorView().getWindowToken());
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().popActivity(this);
        mActivityState =ACTIVITY_DESTROY;
        LogUtil.i(getClass().getName(), "onDestroy()");
    }
}