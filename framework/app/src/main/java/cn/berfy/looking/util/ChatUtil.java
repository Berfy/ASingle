package cn.berfy.looking.util;

import android.content.Context;
import android.util.Log;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.auth.constant.LoginSyncStatus;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;

import cn.berfy.framework.utils.GsonUtil;
import cn.berfy.framework.utils.LogUtil;
import cn.berfy.framework.utils.ToastUtil;

/**
 * Created by Berfy on 2017/4/27.
 * 聊天Model
 */
public class ChatUtil {

    private static ChatUtil mChatUtil;
    private Context mContext;
    public static LoginInfo mLoginInfo;
    private final String TAG = getClass().getName();

    public static ChatUtil init(Context c) {
        if (null == mChatUtil) {
            mChatUtil = new ChatUtil(c);
        }
        return mChatUtil;
    }

    synchronized public static ChatUtil getInstance() {
        synchronized (ChatUtil.class) {
            if (null == mChatUtil) {
                throw new NullPointerException("没有初始化ChatUtil，请在Applicaiton中init(getApplicationContext)");
            }
            return mChatUtil;
        }
    }

    private ChatUtil(Context c) {
        mContext = c;
    }

    public void login() {
        if (null == mLoginInfo) {
            ToastUtil.getInstance().showToast("用户名密码为空");
            return;
        }
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                    @Override
                    public void onSuccess(LoginInfo loginInfo) {
                        LogUtil.d(TAG, "登录成功" + GsonUtil.getInstance().toJson(loginInfo));
                        mLoginInfo = loginInfo;
                        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(
                                new Observer<StatusCode>() {
                                    public void onEvent(StatusCode status) {
                                        Log.i(TAG, "用户状态改变" + status);
                                        if (status.wontAutoLogin()) {
                                            // 被踢出、账号被禁用、密码错误等情况，自动登录失败，需要返回到登录界面进行重新登录操作
                                            AppUtil.logout(mContext);
                                        }
                                    }
                                }, true);
                        NIMClient.getService(AuthServiceObserver.class).observeLoginSyncDataStatus(new Observer<LoginSyncStatus>() {
                            @Override
                            public void onEvent(LoginSyncStatus status) {
                                if (status == LoginSyncStatus.BEGIN_SYNC) {
                                    LogUtil.i(TAG, "login sync data begin");
                                } else if (status == LoginSyncStatus.SYNC_COMPLETED) {
                                    LogUtil.i(TAG, "login sync data completed");
                                }
                            }
                        }, true);
                        Observer<List<IMMessage>> incomingMessageObserver =
                                new Observer<List<IMMessage>>() {
                                    @Override
                                    public void onEvent(List<IMMessage> messages) {
                                        for (IMMessage message : messages) {
                                            LogUtil.e("新消息", GsonUtil.getInstance().toJson(message));
                                        }
                                    }
                                };
                        NIMClient.getService(MsgServiceObserve.class)
                                .observeReceiveMessage(incomingMessageObserver, true);
                    }

                    @Override
                    public void onFailed(int i) {
                        LogUtil.d(TAG, "登录失败" + i);
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        LogUtil.d(TAG, "登录出错" + throwable.getMessage());
                    }
                };
        NIMClient.getService(AuthService.class).login(mLoginInfo)
                .setCallback(callback);
    }

    public void logout() {
        NIMClient.getService(AuthService.class).logout();
    }

    public void getCacheData(String account) {
        NIMClient.getService(AuthService.class).openLocalCache(account);
    }

    public void getList() {
    }
}
