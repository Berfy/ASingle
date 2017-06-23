package cn.berfy.looking.ui.demo;

import android.os.Bundle;
import android.view.View;

import cn.berfy.framework.http.NetResponse;
import cn.berfy.framework.http.RequestCallBack;
import cn.berfy.framework.http.ResponseHandler;
import cn.berfy.framework.http.VolleyHttp;
import cn.berfy.framework.utils.LogUtil;
import cn.berfy.looking.BaseActivity;
import cn.berfy.looking.R;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Berfy on 2017/4/25.
 * Rxjava架构
 */
public class RxJavaActivity extends BaseActivity {

    private final String TAG = "测试RXJAVA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Subscriber<Integer> mySubscriber = new Subscriber<Integer>() {
            @Override
            public void onNext(Integer s) {
                LogUtil.e("测试RXJAVA输出", s + "");
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }
        };

        Observable.from(new String[]{"123"})
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String o) {
                        return Integer.valueOf(o);
                    }
                }).subscribe(mySubscriber);


        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                VolleyHttp.getInstances().get("https://www.baidu.com", null, new ResponseHandler<String>(mContext, new RequestCallBack<String>() {
                    @Override
                    public void start() {
                        subscriber.onStart();
                    }

                    @Override
                    public void finish(NetResponse<String> result) {
//                        if (result.netMsg.code == 1) {
                            subscriber.onNext("fafafafafa");
//                        } else {
//                            subscriber.onError(new Throwable(result.netMsg.msg));
//                        }
                        subscriber.onCompleted();
                    }
                }) {
                    @Override
                    public String getContent(String json) {
                        return null;
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s.equals("没有数据");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    private Subscriber<String> s = new Subscriber<String>() {

        @Override
        public void onNext(String s) {
            LogUtil.e(TAG, "onNext" + "  " + s);
        }

        @Override
        public void onStart() {
            super.onStart();
            LogUtil.e(TAG, "onStart");
        }

        @Override
        public void onCompleted() {
            LogUtil.e(TAG, "onCompleted");
        }

        @Override
        public void onError(Throwable throwable) {
            LogUtil.e(TAG, "onError" + "  " + throwable.getMessage());
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int initContentViewById() {
        return R.layout.activity_listview;
    }

    @Override
    protected View initContentView() {
        return null;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void doClickEvent(int viewId) {

    }
}
