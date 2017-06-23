package cn.berfy.looking.ui.demo.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import cn.berfy.framework.service.bluetooth.ConnectService;
import cn.berfy.framework.utils.LogUtil;
import cn.berfy.framework.utils.ToastUtil;
import cn.berfy.looking.BaseActivity;
import cn.berfy.looking.R;

import static cn.berfy.framework.service.bluetooth.ConnectService.LANYA_OPEN;

/**
 * @author Berfy
 *         怡成血糖仪搜索设备
 */
@SuppressLint("NewApi")
public class BluetoothDeviceSearchingActivity extends BaseActivity {

    private final String TAG = "BluetoothDeviceSearchingActivity";
    private BluetoothDeviceListAdapter mAdapter;
    private ListView mListView;
    private ConnectService mConnectService;
    private BluetoothDevice mBluetoothDevice;//正在连接的设备

    @SuppressLint("NewApi")

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initContentViewById() {
        return R.layout.activity_bluetooth_device_list_layout;
    }

    @Override
    protected View initContentView() {
        return null;
    }

    @Override
    protected void initVariable() {
    }

    @Override
    protected void initView() {
        setTitle("搜索蓝牙");
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new BluetoothDeviceListAdapter(mContext, new BluetoothDeviceListAdapter.OnDeviceStatusListener() {
            @Override
            public void connenct(BluetoothDevice bluetoothDevice) {
                if (null != mConnectService) {
                    mBluetoothDevice = bluetoothDevice;
                    LogUtil.e("设备点击", bluetoothDevice.getAddress());
                    mConnectService.connect(bluetoothDevice);
                    for (MBluetoothDevice device : mAdapter.getData()) {
                        device.setEnable(false);
                        if (device.getBluetoothDevice().getAddress().equals(mBluetoothDevice.getAddress())) {
                            device.setStatus(2);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        mListView.setAdapter(mAdapter);
        findViewById(R.id.btn_search).setOnClickListener(this);
        if (cn.berfy.framework.utils.AppUtil.isSupportBluttooth40(true)) {//蓝牙4.0
            bindService(new Intent(mContext, ConnectService.class), mServiceConnection, BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mHandler.removeMessages(0);
        if (null != mConnectService) {
            mConnectService.stopSearch();
            mConnectService.removeListener(mContext, mOnBlueServiceStatusListener);
        }
    }

    @Override
    protected void doClickEvent(int viewId) {
        switch (viewId) {
            case R.id.btn_search:
                if (null != mConnectService) {
                    //搜索设备
                    mAdapter.getData().clear();
                    mAdapter.notifyDataSetChanged();
                    mConnectService.checkSupport();
                }
                break;
//            case R.id.btn_open:
//                if (null != mConnectService) {
//                    //搜索设备
//                    mConnectService.checkSupport();
//                }
//                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.e(TAG, "onServiceConnected");
            mConnectService = ((ConnectService.MyBinder) service).getService();
            if (null != mConnectService) {
                mConnectService.setOnStatusListener(mContext, mOnBlueServiceStatusListener);
                mConnectService.checkSupport();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.e(TAG, "onServiceDisconnected");
            mConnectService = null;
        }
    };

    private ConnectService.OnBlueServiceStatusListener mOnBlueServiceStatusListener = new ConnectService.OnBlueServiceStatusListener() {

        @Override
        public void statusUpdate(int status, BluetoothDevice bluetoothDevice) {
            switch (status) {
                case ConnectService.STATUS_BLUTTOOTH_NOT_OPEN:
                case ConnectService.STATUS_BLUTTOOTH_OPEN_NOT_ACCESS:
                    mHandler.removeMessages(0);
                    findViewById(R.id.btn_search).setVisibility(View.VISIBLE);
                    for (MBluetoothDevice device : mAdapter.getData()) {
                        device.setEnable(true);
                        device.setStatus(0);
                    }
                    mAdapter.notifyDataSetChanged();
                    mListView.setVisibility(View.GONE);
                    if (status == ConnectService.STATUS_BLUTTOOTH_NOT_OPEN) {
                        ((TextView) findViewById(R.id.tv_status)).setText(getString(R.string.bluetooth_not_open));
                        if (getActivityStatus() == ACTIVITY_RESUME) {
                            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(enableBtIntent, LANYA_OPEN);
                        }
                    } else {
                        ToastUtil.getInstance().showToast("拒绝打开蓝牙");
                    }
                    break;
                case ConnectService.STATUS_BLUTTOOTH_OPENED:
                    //搜索设备
                    ((TextView) findViewById(R.id.tv_status)).setText(getString(R.string.bluetooth_searching));
                    mHandler.sendEmptyMessageDelayed(0, 1000);
                    for (MBluetoothDevice device : mAdapter.getData()) {
                        device.setEnable(true);
                        device.setStatus(0);
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
                case ConnectService.STATUS_BLUTTOOTH_SEARCHING_ERROR:
//                    ((TextView) findViewById(R.id.tv_status)).setText(getString(R.string.yicheng_device_list));
                    ((TextView) findViewById(R.id.tv_status)).setText(getString(R.string.bluetooth_searching_timeout));
                    findViewById(R.id.btn_search).setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                    for (MBluetoothDevice device : mAdapter.getData()) {
                        device.setEnable(true);
                        device.setStatus(0);
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
                case ConnectService.STATUS_BLUTTOOTH_CONNECTTING:
                    mListView.setVisibility(View.GONE);
                    ((TextView) findViewById(R.id.tv_status)).setText(getString(R.string.bluetooth_connectting));
                    break;
                case ConnectService.STATUS_BLUTTOOTH_CONNECTED:
                case ConnectService.STATUS_BLUTTOOTH_CMD:
                    break;
                case ConnectService.STATUS_BLUTTOOTH_CONNECT_ERROR:
                case ConnectService.STATUS_BLUTTOOTH_DISCONNECT:
                    ((TextView) findViewById(R.id.tv_status)).setText(getString(R.string.yicheng_device_connect_error));
                    findViewById(R.id.btn_search).setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                    for (MBluetoothDevice device : mAdapter.getData()) {
                        device.setEnable(true);
                        device.setStatus(0);
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }

        @Override
        public void bt_searching(BluetoothDevice bluetoothDevice) {
            findViewById(R.id.btn_search).setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.tv_status)).setText(getString(R.string.yicheng_device_list));
            MBluetoothDevice yiChengDevice = new MBluetoothDevice();
            yiChengDevice.setStatus(0);
            yiChengDevice.setEnable(true);
            yiChengDevice.setBluetoothDevice(bluetoothDevice);
            boolean isHas = false;
            for (MBluetoothDevice device : mAdapter.getData()) {
                if (device.getBluetoothDevice().getAddress().equals(bluetoothDevice.getAddress())) {
                    LogUtil.e("设备存在", bluetoothDevice.getAddress());
                    //设备已存在
                    isHas = true;
                }
            }
            if (!isHas) {
                mAdapter.getData().add(yiChengDevice);
            }
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void result_data(String data, long time) {
//                        SugarBean sugarBean = MachineYiChengSugarDb.getInstance(mContext).getLastTimeData(UserSharedData.getInstance(mContext).getUserID());
        }

        @Override
        public void bindSuc() {
        }

        @Override
        public void bindError(int code, String err) {
            //绑定失败断开连接重新搜索
            mConnectService.disConnect();
            mHandler.sendEmptyMessageDelayed(0, 1000);
            findViewById(R.id.btn_search).setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.tv_status)).setText(getString(R.string.yicheng_device_list));
            for (MBluetoothDevice device : mAdapter.getData()) {
                device.setEnable(true);
                device.setStatus(0);
            }
            mAdapter.notifyDataSetChanged();
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mConnectService.startSearch();
        }
    };

    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ConnectService.LANYA_OPEN) {
            if (null != mConnectService) {
                mConnectService.resultOpenStatus();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
