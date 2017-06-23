package cn.berfy.looking;

import android.os.Bundle;
import android.view.View;

import cn.berfy.framework.base.BaseActivity;
import cn.berfy.framework.base.BaseFragment;
import cn.berfy.looking.ui.home.HomeFragment;

public class MainActivity extends BaseActivity {

    private HomeFragment mHomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setClickTwoExit(true);
        mHomeFragment = new HomeFragment();
        mHomeFragment.setListener(new BaseFragment.OnFragmentStatusListener() {
            @Override
            public void onViewCreate() {

            }

            @Override
            public void onActivityCreated() {

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onResume() {

            }

            @Override
            public void onPause() {

            }

            @Override
            public void onStop() {

            }
        });
        update(R.id.fragment_mian,mHomeFragment);
    }

    @Override
    protected void initVariable() {
    }

    @Override
    protected View initContentView() {
        return null;
    }

    @Override
    protected int initContentViewById() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void doClickEvent(int viewId) {
    }
}
