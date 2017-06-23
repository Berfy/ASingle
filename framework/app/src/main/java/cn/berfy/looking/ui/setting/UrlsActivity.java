package cn.berfy.looking.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import cn.berfy.framework.utils.CheckUtil;
import cn.berfy.framework.utils.ToastUtil;
import cn.berfy.looking.BaseActivity;
import cn.berfy.looking.R;
import cn.berfy.looking.adapter.setting.UrlsAdapter;
import cn.berfy.looking.bean.UrlsBean;
import cn.berfy.looking.db.TabUrls;
import cn.berfy.looking.util.PopupWindowUtil;

/**
 * Created by Berfy on 2017/4/11.
 * 管理网址
 */
public class UrlsActivity extends BaseActivity {

    private ListView mListView;
    private UrlsAdapter mAdapter;
    private PopupWindowUtil mPopupWindowUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    @Override
    protected View initContentView() {
        return null;
    }

    @Override
    protected int initContentViewById() {
        return R.layout.activity_setting_urls;
    }

    @Override
    protected void initVariable() {
        mPopupWindowUtil = new PopupWindowUtil(mContext);
    }

    @Override
    protected void initView() {
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new UrlsAdapter(mContext);
        mListView.setAdapter(mAdapter);
        findViewById(R.id.btn_add).setOnClickListener(this);
        getTitleBar().showRightTitle(getString(R.string.title_edit), getRightTitleClickListener());
    }

    private View.OnClickListener getRightTitleClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.setEdit(!mAdapter.isEdit());
                mAdapter.notifyDataSetChanged();
                if (mAdapter.isEdit()) {//编辑模式下，显示完成，点击取消操作
                    mAdapter.notifyDataSetChanged();
                    getTitleBar().showRight2Title(getString(R.string.title_delete), getDeleteClickListener());
                    getTitleBar().showRightTitle(getString(R.string.title_finish), getRightTitleClickListener());
                } else {
                    getTitleBar().dismissRight2Tile();
                    getTitleBar().showRightTitle(getString(R.string.title_edit), getRightTitleClickListener());
                    for (int i = 0; i < mAdapter.getData().size(); i++) {
                        mAdapter.getData().get(i).setCheck(false);
                    }
                }
            }
        };
    }

    private View.OnClickListener getDeleteClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter.isEdit()) {
                    boolean isHas = false;
                    for (int i = 0; i < mAdapter.getData().size(); i++) {
                        UrlsBean urlsBean = mAdapter.getData().get(i);
                        if (urlsBean.isCheck()) {
                            isHas = true;
                            TabUrls.getInstances().deleteUrl(urlsBean);
                        }
                    }
                    if (!isHas) {
                        ToastUtil.getInstance().showToast(R.string.tip_delete_null);
                    } else {
                        getData();
                    }
                }
            }
        };
    }

    private void getData() {
        mAdapter.getData().clear();
        mAdapter.getData().addAll(TabUrls.getInstances().getAllData());
        if (mAdapter.getData().size() == 0) {//没有数据显示默认
            getTitleBar().dismissRight2Tile();
            getTitleBar().showRightTitle(getString(R.string.title_edit), getRightTitleClickListener());
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void doClickEvent(int viewId) {
        switch (viewId) {
            case R.id.btn_add:
                mPopupWindowUtil.showPopInout(findViewById(R.id.btn_add), getString(R.string.title_setting_add_url), getString(R.string.title_setting_hint_add_url), R.color.hint_gray, "", new PopupWindowUtil.OnPopInputListener() {
                    @Override
                    public void ok(String text) {
                        if (!text.startsWith("http://") && !text.startsWith("https://")) {
                            text = "https://" + text;
                        }
                        if (!CheckUtil.checkUrl(text)) {
                            ToastUtil.getInstance().showToast(R.string.tip_setting_input_url);
                            return;
                        }
                        mPopupWindowUtil.dismiss();
                        UrlsBean urlsBean = new UrlsBean();
                        urlsBean.setUrl(text);
                        TabUrls.getInstances().addUrl(urlsBean);
                        getData();
                    }
                });
                break;
        }
    }
}
