package cn.berfy.looking.ui.home;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import cn.berfy.framework.base.BaseFragment;
import cn.berfy.framework.cache.TempSharedData;
import cn.berfy.framework.support.RefreshableView;
import cn.berfy.framework.support.WapActivity;
import cn.berfy.framework.support.views.FooterListview;
import cn.berfy.looking.R;
import cn.berfy.looking.adapter.home.HomeContentListAdapter;
import cn.berfy.looking.bean.ContentBean;
import cn.berfy.looking.cons.Constants;
import cn.berfy.looking.db.TabContent;

/**
 * Created by Berfy on 2017/4/7.
 * 首页内容列表
 */
public class HomeContentFragment extends BaseFragment implements RefreshableView.RefreshListener, FooterListview.OnRefreshListener, AdapterView.OnItemClickListener {

    private FooterListview mListview;
    private RefreshableView mRefreshableView;
    private HomeContentListAdapter mAdapter;
    private int PAGE = 1, PAGESIZE = 20;

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initView() {
        mListview = (FooterListview) findViewById(R.id.listView);
        mListview.setOnItemClickListener(this);
        mListview.setOnRefreshListener(this);
        mRefreshableView = (RefreshableView) findViewById(R.id.refreshView);
        mRefreshableView.setBgColor(R.color.bg, R.color.white, R.color.white);
        mRefreshableView.setRefreshListener(this);
        mAdapter = new HomeContentListAdapter(mContext);
        mListview.setAdapter(mAdapter);
        getData(false);
    }

    private void getData(boolean isCross) {
        if (!isCross) {
            PAGE = 1;
            mAdapter.getData().clear();
        }
        List<ContentBean> contentBeens = TabContent.getInstances().getAllData(PAGE, PAGESIZE, false);
        if (contentBeens.size() == 0) {
            mListview.noData();
        } else {
            mAdapter.getData().addAll(contentBeens);
            PAGE++;
        }
        mAdapter.notifyDataSetChanged();
        mListview.finishFooterLoading();
        mRefreshableView.finishRefresh();
    }

    @Override
    protected int initContentViewById() {
        return R.layout.fragment_listview;
    }

    @Override
    protected void doClickEvent(int viewId) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(TempSharedData.getInstance(mContext).getData(Constants.TEMP_REFRESH_HOME_CONTENT))) {
            getData(false);
            TempSharedData.getInstance(mContext).save(Constants.TEMP_REFRESH_HOME_CONTENT, "");
        }
    }

    @Override
    public void onRefresh(ViewGroup view) {
        getData(false);
    }

    @Override
    public void onFootLoading() {
        getData(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mAdapter.getData().size() > position) {
            ContentBean contentBean = mAdapter.getData().get(position);
            if (null != contentBean) {
                Intent intent = new Intent(mContext, WapActivity.class);
                intent.putExtra(WapActivity.INTENT_EXTRA_URL, TextUtils.isEmpty(contentBean.getUrl()) ? contentBean.getTitle() : contentBean.getUrl());
                startActivityWithAnim(intent);
            }
        }
    }
}
