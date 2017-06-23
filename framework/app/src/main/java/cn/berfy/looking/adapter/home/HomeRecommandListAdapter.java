package cn.berfy.looking.adapter.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import cn.berfy.looking.R;
import cn.berfy.looking.bean.ContentBean;

/**
 * Created by Berfy on 2017/4/7.
 * 首页推荐内容列表
 */
public class HomeRecommandListAdapter extends BaseAdapter {

    private Context mContext;
    private List<ContentBean> mData = new ArrayList<>();

    public HomeRecommandListAdapter(Context context) {
        mContext = context;
    }

    public List<ContentBean> getData() {
        return mData;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (null == convertView) {
            convertView = View.inflate(mContext, R.layout.adapter_home_list_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        ContentBean contentBean = mData.get(position);
        holder.tv_title.setText(contentBean.getTitle());
        holder.tv_content.setText(contentBean.getContent());
        holder.tv_time.setText(contentBean.getUpdateTime());
        return convertView;
    }

    class Holder {
        SimpleDraweeView iv_icon;
        TextView tv_title, tv_content, tv_time;

        Holder(View view) {
            iv_icon = (SimpleDraweeView) view.findViewById(R.id.iv_icon);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
        }
    }
}
