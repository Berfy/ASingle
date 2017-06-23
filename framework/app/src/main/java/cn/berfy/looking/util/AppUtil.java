package cn.berfy.looking.util;

import android.content.Context;
import android.content.Intent;

import cn.berfy.looking.ui.demo.chat.LoginActivity;

/**
 * Created by Administrator on 2017/4/27.
 */

public class AppUtil {

    public static void logout(Context context) {
        ChatUtil.mLoginInfo = null;
        ChatUtil.getInstance().logout();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(LoginActivity.EXTRA_ISRELOGIN, true);
        context.startActivity(intent);
    }
}
