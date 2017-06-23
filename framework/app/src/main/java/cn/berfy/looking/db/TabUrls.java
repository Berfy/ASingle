package cn.berfy.looking.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.berfy.framework.utils.GsonUtil;
import cn.berfy.framework.utils.LogUtil;
import cn.berfy.framework.utils.TimeUtil;
import cn.berfy.looking.bean.UrlsBean;
import cn.berfy.looking.db.base.DBHelper;
import cn.berfy.looking.db.base.OpenDBUtil;

/**
 * Created by Berfy on 2017/4/10.
 * 内容表
 */

public class TabUrls {

    private final String TAG = "TabUrls";
    private static TabUrls mTabUrls;
    private SQLiteDatabase mDb;
    private DBHelper mDbHelper;

    synchronized public static TabUrls getInstances() {
        synchronized (TabUrls.class) {
            if (null == mTabUrls) {
                mTabUrls = new TabUrls();
            }
            return mTabUrls;
        }
    }

    private TabUrls() {
        mDbHelper = DBHelper.getInstance();
        mDb = mDbHelper.getDb();
    }

    public void addUrl(UrlsBean urlsBean) {
        try {
            mDb.beginTransaction();
            UrlsBean old = isHas(urlsBean);
            if (null != old) {
                LogUtil.e(TAG, "更新 " + GsonUtil.getInstance().toJson(urlsBean));
                mDb.update(OpenDBUtil.TAB_URLS, getValues(urlsBean), "_id = ? or url = ?"
                        , new String[]{urlsBean.getId() + ""});
            } else {
                LogUtil.e(TAG, "插入 " + GsonUtil.getInstance().toJson(urlsBean));
                mDb.insert(OpenDBUtil.TAB_URLS, null, getValues(urlsBean));
            }
            mDb.setTransactionSuccessful();
            mDb.endTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            mDb.endTransaction();
        }
    }

    public void deleteUrl(UrlsBean urlsBean) {
        LogUtil.e(TAG, "删除内容 " + GsonUtil.getInstance().toJson(urlsBean));
        mDb.delete(OpenDBUtil.TAB_URLS, "_id = ? or url = ?"
                , new String[]{urlsBean.getId() + "", urlsBean.getUrl()});
    }

    public UrlsBean isHas(UrlsBean urlsBean) {
        LogUtil.e(TAG, "isHas " + GsonUtil.getInstance().toJson(urlsBean));
        Cursor cursor = mDb.query(OpenDBUtil.TAB_URLS, null, "_id = ? or url = ? "
                , new String[]{urlsBean.getId() + "", urlsBean.getUrl()}, null, null, null);
        try {
            UrlsBean urlsBean1 = new UrlsBean();
            if (cursor.moveToFirst()) {
                urlsBean1.setId(cursor.getLong(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_URLS[0])));
                urlsBean1.setUrl(cursor.getString(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_URLS[1])));
                urlsBean1.setUpdateTime(TimeUtil.timeFormat(cursor.getLong(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_URLS[2]))
                        , "yyyy-MM-dd HH:mm:ss"));
                return urlsBean1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
        return null;
    }

    public List<UrlsBean> getAllData() {
        LogUtil.e(TAG, "getAllData ");
        Cursor cursor = mDb.query(OpenDBUtil.TAB_URLS, null, null
                , null, null, null, OpenDBUtil.KEYS_TAB_URLS[2]+" desc");
        List<UrlsBean> urlsBeens = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                UrlsBean urlsBean = new UrlsBean();
                urlsBean.setId(cursor.getLong(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_URLS[0])));
                urlsBean.setUrl(cursor.getString(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_URLS[1])));
                urlsBean.setUpdateTime(TimeUtil.timeFormat(cursor.getLong(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_URLS[2]))
                        , "yyyy-MM-dd HH:mm:ss"));
                urlsBeens.add(urlsBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
        LogUtil.e(TAG,"所有数据"+GsonUtil.getInstance().toJson(urlsBeens));
        return urlsBeens;
    }

    public void deleteAllData() {
        LogUtil.e(TAG, "删除所有内容");
        mDb.delete(OpenDBUtil.TAB_URLS, null, null);
    }

    private ContentValues getValues(UrlsBean contentBean) {
        ContentValues cv = new ContentValues();
        cv.put(OpenDBUtil.KEYS_TAB_URLS[1], contentBean.getUrl());
        cv.put(OpenDBUtil.KEYS_TAB_URLS[2], System.currentTimeMillis());
        return cv;
    }
}
