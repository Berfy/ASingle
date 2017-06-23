package cn.berfy.looking.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import cn.berfy.framework.cache.TempSharedData;
import cn.berfy.framework.utils.LogUtil;
import cn.berfy.framework.utils.TimeUtil;
import cn.berfy.framework.utils.ToastUtil;
import cn.berfy.looking.BaseActivity;
import cn.berfy.looking.R;
import cn.berfy.looking.cons.Constants;
import cn.berfy.looking.db.TabContent;
import cn.berfy.looking.db.TabKeyWords;
import cn.berfy.looking.db.TabUrls;
import cn.berfy.looking.ui.demo.DemoListActivity;
import cn.berfy.looking.util.LookingUtil;

/**
 * Created by Berfy on 2017/4/11.
 * 设置
 * 管理网址和关键字
 */
public class SettingActivity extends BaseActivity {

    private LookingUtil mLookingUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initContentViewById() {
        return R.layout.activity_setting;
    }

    @Override
    protected View initContentView() {
        return null;
    }

    @Override
    protected void initVariable() {
        mLookingUtil = new LookingUtil(mContext);
    }

    @Override
    protected void initView() {
        findViewById(cn.berfy.looking.R.id.layout_urls).setOnClickListener(this);
        findViewById(R.id.layout_keywords).setOnClickListener(this);
        findViewById(R.id.layout_request).setOnClickListener(this);
        findViewById(R.id.layout_clear_cache).setOnClickListener(this);
        findViewById(R.id.layout_demo).setOnClickListener(this);
        updateData();
    }

    private void updateData() {
        String time = TempSharedData.getInstance(mContext).getData(Constants.TEMP_SYNC_TIME);
        if (TextUtils.isEmpty(time)) {
            ((TextView) findViewById(R.id.tv_sync_time)).setText(getString(R.string.title_setting_no_syncdata));
        } else {
            ((TextView) findViewById(R.id.tv_sync_time)).setText(getString(R.string.title_setting_syncdata_lastest) + ": " + time);
        }
    }

    @Override
    protected void doClickEvent(int viewId) {
        switch (viewId) {
            case R.id.layout_urls:
                startActivityWithAnim(new Intent(mContext, UrlsActivity.class));
                break;
            case R.id.layout_keywords:
                startActivityWithAnim(new Intent(mContext, KeyWordsActivity.class));
                break;
            case R.id.layout_request:
                cn.berfy.framework.common.Constants.EXECUTOR.execute(new Runnable() {
                    @Override
                    public void run() {
                        mLookingUtil.look(new LookingUtil.OnLookListener() {
                            @Override
                            public void suc() {
                                LogUtil.e("获取完成", "");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((TextView) findViewById(R.id.tv_progress)).setText("抓取成功");
                                        TempSharedData.getInstance(mContext).save(Constants.TEMP_SYNC_TIME, TimeUtil.getCurrentTime());
                                        TempSharedData.getInstance(mContext).save(Constants.TEMP_REFRESH_HOME_RECOMMAND, "sync");
                                        TempSharedData.getInstance(mContext).save(Constants.TEMP_REFRESH_HOME_CONTENT, "sync");
                                        updateData();
                                    }
                                });
                            }

                            @Override
                            public void progress(final float progress, final String currentTitle) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LogUtil.e("获取", "" + progress);
                                        ((TextView) findViewById(R.id.tv_progress)).setText((currentTitle.length() > 10 ? currentTitle.substring(0, 10) : currentTitle) + " " + progress + "%");
                                    }
                                });
                            }

                            @Override
                            public void error() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((TextView) findViewById(R.id.tv_progress)).setText("抓取失败");
                                    }
                                });
                            }
                        });
                    }
                });
                break;
            case R.id.layout_clear_cache:
                TabContent.getInstances().

                        deleteAllData();
                TabKeyWords.getInstances().

                        deleteAllData();
                TabUrls.getInstances().

                        deleteAllData();
                TempSharedData.getInstance(mContext).

                        save(Constants.TEMP_REFRESH_HOME_RECOMMAND, "sync");
                TempSharedData.getInstance(mContext).

                        save(Constants.TEMP_REFRESH_HOME_CONTENT, "sync");
                ToastUtil.getInstance().

                        showToast(R.string.tip_setting_clear_cache_suc);
                break;
            case R.id.layout_demo:

                startActivityWithAnim(new Intent(mContext, DemoListActivity.class));
                break;
        }
    }
}
