package cn.berfy.looking.ui.demo.mvp.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.berfy.looking.BaseActivity;
import cn.berfy.looking.R;
import cn.berfy.looking.ui.demo.mvp.presenter.UserPresenter;

/**
 * Created by Berfy on 2017/4/25.
 * MVP模式
 */
public class UserActivity extends BaseActivity implements IUserView {

    private UserPresenter mUserPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int initContentViewById() {
        return R.layout.activity_demo_mvp_user;
    }

    @Override
    protected View initContentView() {
        return null;
    }

    @Override
    protected void initVariable() {
        mUserPresenter = new UserPresenter(this);
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_show).setOnClickListener(this);
        findViewById(R.id.btn_show1).setOnClickListener(this);
    }

    @Override
    protected void doClickEvent(int viewId) {
        switch (viewId) {
            case R.id.btn_show:
                ((TextView) findViewById(R.id.tv_show)).setText("姓名:" + getName()
                        + "\n" + "年龄:" + getAge()
                        + "\n" + "性别:" + getSex());
                break;
            case R.id.btn_show1:
                mUserPresenter.setData("哈哈", 11, 22);
                ((TextView) findViewById(R.id.tv_show)).setText("姓名:" + getName()
                        + "\n" + "年龄:" + getAge()
                        + "\n" + "性别:" + getSex());
                break;
        }
    }

    @Override
    public void setName(String name) {
        ((EditText) findViewById(R.id.edt_name)).setText(name);
    }

    @Override
    public String getName() {
        return ((EditText) findViewById(R.id.edt_name)).getText().toString().trim();
    }

    @Override
    public void setAge(int age) {
        ((EditText) findViewById(R.id.edt_age)).setText(age + "");
    }

    @Override
    public int getAge() {
        String age = ((EditText) findViewById(R.id.edt_age)).getText().toString().trim();
        return TextUtils.isEmpty(age) ? 0 : Integer.valueOf(age);
    }

    @Override
    public void setSex(int sex) {
        ((EditText) findViewById(R.id.edt_sex)).setText(sex + "");
    }

    @Override
    public int getSex() {
        String sex = ((EditText) findViewById(R.id.edt_sex)).getText().toString().trim();
        return TextUtils.isEmpty(sex) ? 0 : Integer.valueOf(sex);
    }
}
