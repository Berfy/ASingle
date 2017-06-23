package cn.berfy.looking.ui.demo.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.berfy.looking.R;

/**
 * Created by Berfy on 2017/5/24.
 * 蓝牙搜索怡成血糖仪列表
 */
public class BluetoothDeviceListAdapter extends BaseAdapter {

    private Context mContext;
    private OnDeviceStatusListener mOnDeviceStatusListener;
    private List<MBluetoothDevice> mDevices = new ArrayList<>();

    public BluetoothDeviceListAdapter(Context context, OnDeviceStatusListener onDeviceStatusListener) {
        mContext = context;
        mOnDeviceStatusListener = onDeviceStatusListener;
    }

    public List<MBluetoothDevice> getData() {
        return mDevices;
    }

    @Override
    public int getCount() {
        return mDevices.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (null == convertView) {
            convertView = View.inflate(mContext, R.layout.adapter_bluetooth_device_list_layout, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        final MBluetoothDevice yiChengDevice = mDevices.get(position);
        if (position == 0) {
            holder.v_top_line.setVisibility(View.VISIBLE);
        } else {
            holder.v_top_line.setVisibility(View.GONE);
        }
        holder.tv_name.setText(yiChengDevice.getBluetoothDevice().getName());
        holder.tv_address.setText(yiChengDevice.getBluetoothDevice().getAddress());
        holder.btn_status.setEnabled(yiChengDevice.isEnable());
        switch (yiChengDevice.getStatus()) {
            case 0://未连接
                holder.btn_status.setText(R.string.yicheng_status_connect);
                holder.btn_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mOnDeviceStatusListener) {
                            mOnDeviceStatusListener.connenct(yiChengDevice.getBluetoothDevice());
                        }
                    }
                });
                break;
            case 1://已连接
                holder.btn_status.setText(R.string.yicheng_status_connected);
                break;
            case 2://ing
                holder.btn_status.setText(R.string.yicheng_status_connectting);
                break;
        }
        return convertView;
    }

    public interface OnDeviceStatusListener {
        void connenct(BluetoothDevice bluetoothDevice);
    }

    class Holder {
        View v_top_line;
        TextView tv_name, tv_address;
        Button btn_status;

        Holder(View view) {
            v_top_line = view.findViewById(R.id.v_top_line);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_address = (TextView) view.findViewById(R.id.tv_address);
            btn_status = (Button) view.findViewById(R.id.btn_status);
        }
    }
}
