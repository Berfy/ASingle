package cn.berfy.looking.ui;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.berfy.framework.utils.AnimUtil;
import cn.berfy.looking.R;

/**
 * Created by Berfy on 2017/4/11.
 * 标题栏
 */

public class TitleBar extends LinearLayout {

    private Context mContext;
    private View mView;

    public TitleBar(Context context, AttributeSet attr) {
        super(context, attr);
        init(context);
    }

    public TitleBar(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mView = View.inflate(mContext, R.layout.view_titlebar, null);
        addView(mView);
        mView.findViewById(R.id.tv_left).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mContext).finish();
                AnimUtil.pushRightInAndOut((Activity) mContext);
            }
        });
        try {
            setTitle(((Activity) mContext).getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTitle(int resourceId) {
        if (null != mView) {
            ((TextView) mView.findViewById(R.id.tv_title)).setText(getResources().getString(resourceId));
        }
    }

    public void setTitle(CharSequence title) {
        if (null != mView) {
            ((TextView) mView.findViewById(R.id.tv_title)).setText(title);
        }
    }

    public void showLeftTitle(boolean isShow, OnClickListener onClickListener) {
        findViewById(R.id.tv_left).setVisibility(isShow ? VISIBLE : GONE);
        findViewById(R.id.tv_left).setOnClickListener(onClickListener);
    }

    public void showLeftTitle(String title, OnClickListener onClickListener) {
        if (null != mView) {
            mView.findViewById(R.id.tv_left).setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(title))
                ((TextView) mView.findViewById(R.id.tv_left)).setText(title);
            mView.findViewById(R.id.tv_left).setOnClickListener(onClickListener);
        }
    }

    public void dismissLeftTile() {
        mView.findViewById(R.id.tv_left).setVisibility(View.GONE);
    }

    public void showRightTitle(String title, OnClickListener onClickListener) {
        if (null != mView) {
            mView.findViewById(R.id.tv_right).setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(title))
                ((TextView) mView.findViewById(R.id.tv_right)).setText(title);
            mView.findViewById(R.id.tv_right).setOnClickListener(onClickListener);
        }
    }

    public void dismissRightTile() {
        mView.findViewById(R.id.tv_right).setVisibility(View.GONE);
    }

    public void showRight2Title(String title, OnClickListener onClickListener) {
        if (null != mView) {
            mView.findViewById(R.id.tv_right2).setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(title))
                ((TextView) mView.findViewById(R.id.tv_right2)).setText(title);
            mView.findViewById(R.id.tv_right2).setOnClickListener(onClickListener);
        }
    }

    public void dismissRight2Tile() {
        mView.findViewById(R.id.tv_right2).setVisibility(View.GONE);
    }

    public void showLeftIcon(int resourceId, OnClickListener onClickListener) {
        if (null != mView) {
            mView.findViewById(R.id.iv_left).setVisibility(View.VISIBLE);
            if (resourceId > 0)
                ((ImageView) mView.findViewById(R.id.iv_left)).setBackgroundResource(resourceId);
            mView.findViewById(R.id.iv_left).setOnClickListener(onClickListener);
        }
    }

    public void dismissLeftIcon() {
        mView.findViewById(R.id.iv_left).setVisibility(View.GONE);
    }

    public void showRightIcon(int resourceId, OnClickListener onClickListener) {
        if (null != mView) {
            mView.findViewById(R.id.iv_right).setVisibility(View.VISIBLE);
            if (resourceId > 0)
                ((ImageView) mView.findViewById(R.id.iv_right)).setBackgroundResource(resourceId);
            mView.findViewById(R.id.iv_right).setOnClickListener(onClickListener);
        }
    }

    public void dismissRightIcon() {
        mView.findViewById(R.id.iv_right).setVisibility(View.GONE);
    }
}
