package cn.berfy.looking.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import cn.berfy.framework.utils.DeviceUtil;
import cn.berfy.framework.utils.LogUtil;
import cn.berfy.framework.utils.ToastUtil;
import cn.berfy.looking.R;

public class PopupWindowUtil {

    private Context mContext;
    private PopupWindow mPopupWindow;
    private TranslateAnimation mAnim_open = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f);
    private TranslateAnimation mAnim_close = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);

    public PopupWindowUtil(Context context) {
        mContext = context;
        mPopupWindow = new PopupWindow(mContext);
        mAnim_open.setDuration(300);
        mAnim_close.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }
        });
        mAnim_close.setDuration(300);
    }

    public void showPopInout(final View parent, String title, String hintText, int hintColor, final String btnOkText, final OnPopInputListener onPopInputListener) {
        final View popView = View.inflate(mContext, R.layout.pop_input, null);
        mPopupWindow.setContentView(popView);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setWidth(LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(LayoutParams.MATCH_PARENT);
        mPopupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                parent.setEnabled(true);
            }
        });
        if (!TextUtils.isEmpty(title)) {
            ((TextView) popView.findViewById(R.id.tv_title)).setText(title);
        }
        if (!TextUtils.isEmpty(hintText)) {
            ((TextView) popView.findViewById(R.id.edit_input)).setHint(hintText);
        }
        if (hintColor > 0) {
            ((EditText) popView.findViewById(R.id.edit_input)).setHintTextColor(mContext.getResources().getColor(hintColor));
        }
        if (!TextUtils.isEmpty(btnOkText)) {
            ((Button) popView.findViewById(R.id.btn_add)).setText(btnOkText);
        }
        popView.findViewById(R.id.bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceUtil.closeKeyboard(mContext, ((EditText) popView.findViewById(R.id.edit_input)).getWindowToken());
            }
        });
        popView.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = ((EditText) popView.findViewById(R.id.edit_input)).getText().toString().trim().replace("\n", "");
                if (TextUtils.isEmpty(text)) {
                    ToastUtil.getInstance().showToast(R.string.tip_setting_tip_null);
                } else {
                    DeviceUtil.closeKeyboard(mContext, ((EditText) popView.findViewById(R.id.edit_input)).getWindowToken());
                    if (null != onPopInputListener) {
                        onPopInputListener.ok(text);
                    }
                }
            }
        });
        show(parent, Gravity.BOTTOM);
    }

    private boolean isCanShow() {
        try {
            if (!((Activity) mContext).isFinishing() && null != mPopupWindow && !mPopupWindow.isShowing()) {//!((Activity) view.getContext()).isFinishing() &&
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void show(View view, int gravity) {
        LogUtil.e("判断显示", "===");
        dismiss();
        try {
            if (isCanShow()) {//!((Activity) view.getContext()).isFinishing() &&
                LogUtil.e("显示", "===");
                mPopupWindow.showAtLocation(view, gravity, 0, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismiss() {
        try {
            if (!((Activity) mContext).isFinishing() && null != mPopupWindow && mPopupWindow.isShowing()) {//!((Activity) mContext).isFinishing() &&
                mPopupWindow.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnPopInputListener {
        void ok(String text);
    }
}
