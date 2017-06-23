package cn.berfy.looking.ui.demo;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import cn.berfy.framework.support.slidingmenu.SlidingMenu;
import cn.berfy.framework.utils.FragmentUtil;
import cn.berfy.framework.utils.ViewUtils;
import cn.berfy.looking.BaseSlidingMenuActivity;
import cn.berfy.looking.R;
import cn.berfy.looking.ui.home.HomeContentFragment;

/**
 * Created by Berfy on 2017/4/20.
 * 抽屉
 */
public class SlidingMenuActivity extends BaseSlidingMenuActivity {

    private HomeContentFragment mHomeContentFragment;
    private FragmentUtil mFragmentUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBehindContentView(R.layout.slidingmenu_left_menu);
        getSlidingMenu().setBackgroundColor(getResources().getColor(R.color.bg));
        getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
        getSlidingMenu().setContent(R.layout.activity_demo_slidingmenu);
        getSlidingMenu().setSecondaryMenu(R.layout.slidingmenu_right_menu);
        getSlidingMenu().setBehindOffsetPx(ViewUtils.getScreenWidth(mContext) / 2);
        getSlidingMenu().setFadeDegree(0.65f);
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
    }

    @Override
    protected void initVariable() {
        mFragmentUtil = new FragmentUtil(this);
    }

    @Override
    protected int initContentViewById() {
        return R.layout.activity_demo_slidingmenu;
    }

    @Override
    protected View initContentView() {
        return null;
    }

    @Override
    protected void initView() {
        getTitleBar().dismissLeftTile();
        getTitleBar().showLeftIcon(R.mipmap.ic_launcher, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSlidingMenu().showMenu();
            }
        });
        getTitleBar().showRightIcon(R.mipmap.ic_launcher, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSlidingMenu().showSecondaryMenu();
            }
        });
        mHomeContentFragment = new HomeContentFragment();
        mFragmentUtil.update(R.id.fragment_content, mHomeContentFragment);
    }

    @Override
    protected void doClickEvent(int viewId) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getSlidingMenu().isMenuShowing()) {
                getSlidingMenu().showContent();
            } else {
                doBackAction();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
