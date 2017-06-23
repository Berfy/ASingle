package cn.berfy.looking;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

/**
 * Created by Berfy on 2017/4/6.
 * 启动页
 */
public class LoadingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler.sendEmptyMessageDelayed(0, 2000);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            finish();
            startActivityWithAnim(new Intent(mContext, MainActivity.class));
        }
    };

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initView() {
        hideTitle();
    }

    @Override
    protected int initContentViewById() {
        return R.layout.activity_loading;
    }

    @Override
    protected View initContentView() {
        return null;
    }

    @Override
    protected void doClickEvent(int viewId) {

    }
}
