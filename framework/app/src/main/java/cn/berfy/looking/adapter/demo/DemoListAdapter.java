package cn.berfy.looking.adapter.demo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.berfy.looking.R;

/**
 * Created by Berfy on 2017/4/10.
 * 首页
 */

public class DemoListAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mDatas = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public DemoListAdapter(Context context, OnItemClickListener onItemClickListener) {
        mContext = context;
        mOnItemClickListener = onItemClickListener;
    }

    public List<String> getData() {
        return mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (null == convertView) {
            convertView = View.inflate(mContext, R.layout.adapter_demo_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv_title.setText(mDatas.get(position));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(position);
            }
        });
        return convertView;
    }

    class Holder {
        TextView tv_title;

        Holder(View view) {
            tv_title = (TextView) view.findViewById(R.id.tv_title);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
