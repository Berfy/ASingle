package cn.berfy.looking.ui.demo.bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * Created by Berfy on 2017/5/24.
 * 怡成血糖怡状态
 */
public class MBluetoothDevice {

    private BluetoothDevice bluetoothDevice;
    private int status;//0未连接 1已连接 2连接中
    private boolean isEnable;//按钮点击是否可用

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }
}
