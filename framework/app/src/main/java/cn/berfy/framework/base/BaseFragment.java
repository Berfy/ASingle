package cn.berfy.framework.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import cn.berfy.framework.utils.AnimUtil;
import cn.berfy.framework.utils.LogUtil;

public abstract class BaseFragment extends Fragment implements
        OnClickListener {

    protected Activity mContext;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = View.inflate(mContext, getContentViewLayoutId(), null);
        if (null == mView) {
            mView = getContentView();
            if (null == mView) {
                return null;
            }
        }
        initViews();
        LogUtil.i(getClass().getName(), "onCreateView()");
        return mView;
    }

    @Nullable
    @Override
    public View getView() {
        return mView;
    }

    protected View findViewById(int redId) {
        return getView().findViewById(redId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        initVariable();
        LogUtil.i(getClass().getName(), "onCreate()");
    }

    abstract protected View getContentView();

    abstract protected int getContentViewLayoutId();

    /**
     * 初始化函数
     */
    abstract protected void initVariable();

    /**
     * 初始化视图
     */
    abstract protected void initViews();

    /**
     * 点击事件
     */
    abstract protected void doClickEvent(View v);

    @Override
    public void onClick(View view) {
        doClickEvent(view);
    }

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.i(getClass().getName(), "onActivityCreated()");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.i(getClass().getName(), "onViewCreated()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.i(getClass().getName(), "onDestroyView()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.i(getClass().getName(), "onDetach()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i(getClass().getName(), "onDestroy()");
    }
}