package cn.berfy.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ListView;

import cn.berfy.framework.cache.TempSharedData;

public class SoftKeyboardUtil {

    private static String TEMP_HEIGHT = "ime_height";

    public static void observeSoftKeyboard(final Activity activity, final OnSoftKeyboardChangeListener listener) {
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            int previousKeyboardHeight = -1;

            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int displayHeight = rect.bottom - rect.top;
                int height = decorView.getHeight();
                int keyboardHeight = height - displayHeight;
                if (previousKeyboardHeight != keyboardHeight) {
                    boolean hide = (double) displayHeight / height > 0.8;
                    listener.onSoftKeyBoardChange(keyboardHeight, !hide);
                }
                TempSharedData.getInstance(activity).save(TEMP_HEIGHT, keyboardHeight);
                previousKeyboardHeight = height;
            }
        });
    }

    public static void autoScroll(Context context, ListView view, View clickView) {
        view.setSmoothScrollbarEnabled(true);
        int[] location = new int[2];
        clickView.getLocationOnScreen(location);
        LogUtil.e("计算键盘位置", location[1] + "    " + clickView.getHeight());
        location[1] = location[1] + clickView.getHeight();
        int topHeight = ViewUtils.getScreenHeight(context) - TempSharedData.getInstance(context).getData(TEMP_HEIGHT, ViewUtils.getScreenHeight(context) / 2) - ViewUtils.dip2px(context, 40);
        //点击在键盘区域，列表向上滑动，点击在上方区域，向下滑动
        LogUtil.e("计算键盘位置", location[1] + "    " + topHeight);
        view.smoothScrollBy(location[1] - topHeight, 200);
    }

    public interface OnSoftKeyboardChangeListener {
        void onSoftKeyBoardChange(int softKeybardHeight, boolean visible);
    }
}  