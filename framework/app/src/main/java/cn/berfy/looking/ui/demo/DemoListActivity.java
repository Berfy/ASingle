package cn.berfy.looking.ui.demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

import cn.berfy.framework.support.views.qrcode.zxing.activity.CaptureActivity;
import cn.berfy.framework.utils.DeviceUtil;
import cn.berfy.looking.BaseActivity;
import cn.berfy.looking.R;
import cn.berfy.looking.adapter.demo.DemoListAdapter;
import cn.berfy.looking.ui.demo.bluetooth.BluetoothDeviceSearchingActivity;
import cn.berfy.looking.ui.demo.carplate.CarPlateActivity;
import cn.berfy.looking.ui.demo.chat.LoginActivity;
import cn.berfy.looking.ui.demo.mvp.view.UserActivity;

/**
 * Created by Berfy on 2017/4/21.
 * Demo展示列表
 */
public class DemoListActivity extends BaseActivity {

    private ListView mListView;
    private DemoListAdapter mDemoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        return R.layout.activity_listview;
    }

    @Override
    protected void initView() {
        mListView = (ListView) findViewById(R.id.listView);
        mDemoListAdapter = new DemoListAdapter(mContext, new DemoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        startActivityWithAnim(new Intent(mContext, SlidingMenuActivity.class));
                        break;
                    case 1:
                        startActivityWithAnim(new Intent(mContext, RxJavaActivity.class));
                        break;
                    case 2:
                        startActivityWithAnim(new Intent(mContext, UserActivity.class));
                        break;
                    case 3:
                        startActivityWithAnim(new Intent(mContext, LoginActivity.class));
                        break;
                    case 4:
                        if (DeviceUtil.selfPermissionGranted(mContext, Manifest.permission.ACCESS_COARSE_LOCATION, 2)) {
                            startActivityWithAnim(new Intent(mContext, BluetoothDeviceSearchingActivity.class));
                        }
                        break;
                    case 5:
                        if (DeviceUtil.selfPermissionGranted(mContext, Manifest.permission.CAMERA, 0)) {
                            startActivityWithAnim(new Intent(mContext, CaptureActivity.class));
                        }
                        break;
                    case 6:
                        if (DeviceUtil.selfPermissionGranted(mContext, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1)) {
                            startActivityWithAnim(new Intent(mContext, CarPlateActivity.class));
                        }
                        break;
                }
            }
        });
        mListView.setAdapter(mDemoListAdapter);
        mDemoListAdapter.getData().add(getString(R.string.title_demo_slidingmenu));
        mDemoListAdapter.getData().add(getString(R.string.title_demo_rx));
        mDemoListAdapter.getData().add(getString(R.string.title_demo_mvp));
        mDemoListAdapter.getData().add(getString(R.string.title_demo_chat_login));
        mDemoListAdapter.getData().add(getString(R.string.title_demo_bluetooth));
        mDemoListAdapter.getData().add(getString(R.string.title_demo_qr));
        mDemoListAdapter.getData().add(getString(R.string.title_demo_car_plate));
        mDemoListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isSuc = true;
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                isSuc = false;
            }
        }
        if (isSuc) {
            switch (requestCode) {
                case 0:
                    startActivityWithAnim(new Intent(mContext, CaptureActivity.class));
                    break;
                case 1:
                    startActivityWithAnim(new Intent(mContext, CarPlateActivity.class));
                    break;
                case 2:
                    startActivityWithAnim(new Intent(mContext, BluetoothDeviceSearchingActivity.class));
                    break;
            }
        }
    }

    @Override
    protected void doClickEvent(int viewId) {

    }
}
