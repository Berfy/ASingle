package cn.berfy.looking.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import cn.berfy.framework.utils.ToastUtil;
import cn.berfy.looking.BaseActivity;
import cn.berfy.looking.R;
import cn.berfy.looking.adapter.setting.KeyWordsAdapter;
import cn.berfy.looking.bean.KeyWordsBean;
import cn.berfy.looking.db.TabKeyWords;
import cn.berfy.looking.util.PopupWindowUtil;

/**
 * Created by Berfy on 2017/4/11.
 * 管理关键字
 */
public class KeyWordsActivity extends BaseActivity {

    private ListView mListView;
    private KeyWordsAdapter mAdapter;
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
        return R.layout.activity_setting_keywords;
    }

    @Override
    protected void initVariable() {
        mPopupWindowUtil = new PopupWindowUtil(mContext);
    }

    @Override
    protected void initView() {
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new KeyWordsAdapter(mContext);
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
                    for (int i = 0; i < mAdapter.getData().size(); i++) {
                        mAdapter.getData().get(i).setCheck(false);
                    }
                    mAdapter.notifyDataSetChanged();
                    getTitleBar().showRight2Title(getString(R.string.title_delete), getDeleteClickListener());
                    getTitleBar().showRightTitle(getString(R.string.title_finish), getRightTitleClickListener());
                } else {
                    getTitleBar().dismissRight2Tile();
                    getTitleBar().showRightTitle(getString(R.string.title_edit), getRightTitleClickListener());
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
                        KeyWordsBean keyWordsBean = mAdapter.getData().get(i);
                        if (keyWordsBean.isCheck()) {
                            isHas = true;
                            TabKeyWords.getInstances().deleteKey(keyWordsBean);
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
        mAdapter.getData().addAll(TabKeyWords.getInstances().getAllData());
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
                mPopupWindowUtil.showPopInout(findViewById(R.id.btn_add), getString(R.string.title_setting_add_keyword), getString(R.string.title_setting_hint_add_keyword), R.color.hint_gray, "", new PopupWindowUtil.OnPopInputListener() {
                    @Override
                    public void ok(String text) {
                        mPopupWindowUtil.dismiss();
                        KeyWordsBean keyWordsBean = new KeyWordsBean();
                        keyWordsBean.setKeyWords(text);
                        TabKeyWords.getInstances().addKey(keyWordsBean);
                        getData();
                    }
                });
                break;
        }
    }
}
