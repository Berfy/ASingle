package cn.berfy.looking.ui.demo.mvp.model;

/**
 * Created by Administrator on 2017/4/25.
 */

public class UserModel implements IUserModel {

    private String mName;
    private int mAge;
    private int mSex;

    @Override
    public void setName(String name) {
        mName = name;
    }

    @Override
    public void setAge(int age) {
        mAge = age;
    }

    @Override
    public void setSex(int sex) {
        mSex = sex;
    }
}
