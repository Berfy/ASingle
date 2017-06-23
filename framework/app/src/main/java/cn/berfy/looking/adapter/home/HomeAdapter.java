package cn.berfy.looking.adapter.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Berfy on 2017/4/10.
 * 首页
 */

public class HomeAdapter extends FragmentPagerAdapter {

    private List<Fragment> mDatas = new ArrayList<>();

    public HomeAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public List<Fragment> getData(){
        return mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mDatas.get(position);
    }
}
