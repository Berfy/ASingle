package cn.berfy.looking.ui.demo.chat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.netease.nimlib.sdk.auth.LoginInfo;

import cn.berfy.framework.utils.ToastUtil;
import cn.berfy.looking.BaseActivity;
import cn.berfy.looking.R;
import cn.berfy.looking.ui.demo.DemoListActivity;
import cn.berfy.looking.util.ChatUtil;

/**
 * Created by Administrator on 2017/4/27.
 */

public class LoginActivity extends BaseActivity {

    public static final String EXTRA_ISRELOGIN = "isRelogin";
    private boolean mIsReLogin;//需要重新登录则返回到Demolist页面，不需要则正常返回页面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View initContentView() {
        return null;
    }

    @Override
    protected int initContentViewById() {
        return R.layout.activity_demo_chat_login;
    }

    @Override
    protected void initVariable() {
        mIsReLogin = getIntent().getBooleanExtra(EXTRA_ISRELOGIN, false);
    }

    @Override
    protected void initView() {
        getTitleBar().showLeftTitle("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsReLogin) {
                    startActivityWithAnim(new Intent(mContext, DemoListActivity.class));
                    finish();
                } else {
                    doBackAction();
                }
            }
        });
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_regist).setOnClickListener(this);
    }

    @Override
    protected void doClickEvent(int viewId) {
        switch (viewId) {
            case R.id.btn_login:
                String user = ((EditText) findViewById(R.id.edt_user)).getText().toString().trim();
                String pwd = ((EditText) findViewById(R.id.edt_pwd)).getText().toString().trim();
                if (TextUtils.isEmpty(user)) {
                    ToastUtil.getInstance().showToast("请输入用户名");
                } else if (TextUtils.isEmpty(pwd)) {
                    ToastUtil.getInstance().showToast("请输入密码");
                } else {
                    ChatUtil.mLoginInfo = new LoginInfo(user, pwd);
                    ChatUtil.getInstance().login();
                }
                break;
            case R.id.btn_regist:
                startActivityWithAnim(new Intent(mContext, RegistActivity.class));
                break;
        }
    }

    private void doLogin() {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsReLogin) {
                startActivityWithAnim(new Intent(mContext, DemoListActivity.class));
                finish();
            } else {
                doBackAction();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
