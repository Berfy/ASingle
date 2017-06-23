package cn.berfy.looking.ui.home;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.berfy.framework.base.BaseFragment;
import cn.berfy.framework.utils.DeviceUtil;
import cn.berfy.framework.utils.ToastUtil;
import cn.berfy.looking.R;
import cn.berfy.looking.adapter.home.HomeAdapter;
import cn.berfy.looking.ui.setting.SettingActivity;

/**
 * Created by Berfy on 2017/4/7.
 * 首页
 */
public class HomeFragment extends BaseFragment {

    private EditText mEdtSearch;
    private HomeRecommandFragment mHomeRecommandFragment;
    private HomeContentFragment mHomeContentFragment;
    private final int PAGE_RECOMMAND = 0;
    private final int PAGE_CONTENT = 1;
    private ViewPager mViewPager;
    private HomeAdapter mHomeAdapter;

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mHomeRecommandFragment = new HomeRecommandFragment();
        mHomeContentFragment = new HomeContentFragment();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(mHomeRecommandFragment);
        fragments.add(mHomeContentFragment);
        mHomeAdapter = new HomeAdapter(getFragmentManager());
        mHomeAdapter.getData().addAll(fragments);
        mViewPager.setAdapter(mHomeAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updatePage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mEdtSearch = (EditText) findViewById(R.id.edt_search);
        mEdtSearch.setSingleLine();
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEdtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ToastUtil.getInstance().showToast("搜索" + mEdtSearch.getText().toString().trim());
                    DeviceUtil.closeKeyboard(mContext, mEdtSearch.getWindowToken());
                }
                return false;
            }
        });
        findViewById(R.id.tv_setting).setOnClickListener(this);
        findViewById(R.id.layout_content).setOnClickListener(this);
        findViewById(R.id.layout_recommand).setOnClickListener(this);
        updatePage(PAGE_RECOMMAND);
        mViewPager.setCurrentItem(PAGE_RECOMMAND);
    }

    private void updatePage(int page) {
        findViewById(R.id.layout_content).setBackgroundResource(R.color.bg);
        findViewById(R.id.layout_recommand).setBackgroundResource(R.color.bg);
        switch (page) {
            case PAGE_RECOMMAND:
                findViewById(R.id.layout_recommand).setBackgroundResource(R.color.bg_press);
                break;
            case PAGE_CONTENT:
                findViewById(R.id.layout_content).setBackgroundResource(R.color.bg_press);
                break;
        }
    }

    @Override
    protected int initContentViewById() {
        return R.layout.fragment_home;
    }

    @Override
    protected void doClickEvent(int viewId) {
        switch (viewId) {
            case R.id.layout_recommand:
                updatePage(PAGE_RECOMMAND);
                mViewPager.setCurrentItem(PAGE_RECOMMAND);
                break;
            case R.id.layout_content:
                updatePage(PAGE_CONTENT);
                mViewPager.setCurrentItem(PAGE_CONTENT);
                break;
            case R.id.tv_setting:
                startActivityWithAnim(new Intent(mContext, SettingActivity.class));
                break;
        }
    }
}
