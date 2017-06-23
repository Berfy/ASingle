package cn.berfy.looking.ui.demo.mvp.presenter;

import cn.berfy.looking.ui.demo.mvp.model.IUserModel;
import cn.berfy.looking.ui.demo.mvp.model.UserModel;
import cn.berfy.looking.ui.demo.mvp.view.IUserView;

/**
 * Created by Berfy on 2017/4/25.
 * 观察者
 */

public class UserPresenter {

    private IUserModel mUserModel;
    private IUserView mUserView;

    public UserPresenter(IUserView userView) {
        mUserView = userView;
        mUserModel = new UserModel();
    }

    public void setData(String name, int sex, int age) {
        mUserView.setName(name);
        mUserView.setSex(sex);
        mUserView.setAge(age);
    }
}
