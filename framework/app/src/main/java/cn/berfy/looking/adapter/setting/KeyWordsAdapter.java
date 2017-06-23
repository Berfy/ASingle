package cn.berfy.looking.adapter.setting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.berfy.framework.utils.LogUtil;
import cn.berfy.looking.R;
import cn.berfy.looking.bean.KeyWordsBean;

/**
 * Created by Berfy on 2017/4/12.
 * 关键字列表
 */
public class KeyWordsAdapter extends BaseAdapter {

    private Context mContext;
    private List<KeyWordsBean> mData = new ArrayList<>();
    private boolean mIsEdit;

    public KeyWordsAdapter(Context context) {
        mContext = context;
    }

    public List<KeyWordsBean> getData() {
        return mData;
    }

    public void setEdit(boolean isEdit) {
        mIsEdit = isEdit;
    }

    public boolean isEdit() {
        return mIsEdit;
    }

    @Override
    public int getCount() {
        return mData.size();
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
            convertView = View.inflate(mContext, R.layout.adapter_setting_keywords_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        final KeyWordsBean keyWordsBean = mData.get(position);
        holder.tv_title.setText(keyWordsBean.getKeyWords());
        holder.checkBox.setVisibility(mIsEdit ? View.VISIBLE : View.GONE);
        holder.checkBox.setChecked(keyWordsBean.isCheck());
        LogUtil.e("点击checked", holder.checkBox.isChecked() + "");
        if (isEdit()) {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    keyWordsBean.setCheck(!keyWordsBean.isCheck());
                    notifyDataSetChanged();
                }
            });
        } else {
            convertView.setOnClickListener(null);
        }
        return convertView;
    }

    class Holder {
        TextView tv_title;
        CheckBox checkBox;

        Holder(View view) {
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
        }
    }
}
