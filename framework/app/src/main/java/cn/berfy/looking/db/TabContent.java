package cn.berfy.looking.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import cn.berfy.framework.utils.GsonUtil;
import cn.berfy.framework.utils.LogUtil;
import cn.berfy.framework.utils.TimeUtil;
import cn.berfy.looking.bean.ContentBean;
import cn.berfy.looking.db.base.DBHelper;
import cn.berfy.looking.db.base.OpenDBUtil;

/**
 * Created by Berfy on 2017/4/10.
 * 内容表
 */

public class TabContent {

    private final String TAG = "TabContent";
    private static TabContent mTabContent;
    private SQLiteDatabase mDb;
    private DBHelper mDbHelper;

    synchronized public static TabContent getInstances() {
        synchronized (TabContent.class) {
            if (null == mTabContent) {
                mTabContent = new TabContent();
            }
            return mTabContent;
        }
    }

    private TabContent() {
        mDbHelper = DBHelper.getInstance();
        mDb = mDbHelper.getDb();
    }

    public void addContent(ContentBean contentBean) {
        try {
            mDb.beginTransaction();
            ContentBean old = isHas(contentBean);
            if (null != old) {
                LogUtil.e(TAG, "更新 " + GsonUtil.getInstance().toJson(contentBean));
                mDb.update(OpenDBUtil.TAB_CONTENTS, getValues(contentBean), "_id = ? or (title = ? and content = ? and url = ?) "
                        , new String[]{contentBean.getId() + "", contentBean.getTitle(), contentBean.getContent(), contentBean.getUrl()});
            } else {
                LogUtil.e(TAG, "插入 " + GsonUtil.getInstance().toJson(contentBean));
                mDb.insert(OpenDBUtil.TAB_CONTENTS, null, getValues(contentBean));
            }
            mDb.setTransactionSuccessful();
            mDb.endTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            mDb.endTransaction();
        }
    }

    public void deleteContent(ContentBean contentBean) {
        LogUtil.e(TAG, "删除内容 " + GsonUtil.getInstance().toJson(contentBean));
        mDb.delete(OpenDBUtil.TAB_CONTENTS, "_id = ? or (title = ? and content = ? and url = ?) "
                , new String[]{contentBean.getId() + "", contentBean.getTitle(), contentBean.getContent(), contentBean.getUrl()});
    }

    public void deleteAllData() {
        LogUtil.e(TAG, "删除所有内容");
        mDb.delete(OpenDBUtil.TAB_CONTENTS, null, null);
    }

    public List<ContentBean> getAllData(int page, int pageSize, boolean isRecommand) {
        LogUtil.e(TAG, "getAllData ");
        Cursor cursor = mDb.query(OpenDBUtil.TAB_CONTENTS, null, (isRecommand ? (OpenDBUtil.KEYS_TAB_CONTENTS[6] + " != null or " + OpenDBUtil.KEYS_TAB_CONTENTS[6] + " != \"\"") : null)
                , null, null, null, OpenDBUtil.KEYS_TAB_CONTENTS[7] + " desc", (page * pageSize - pageSize) + "," + pageSize);
        List<ContentBean> contentBeens = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                ContentBean contentBean1 = new ContentBean();
                contentBean1.setId(cursor.getLong(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_CONTENTS[0])));
                contentBean1.setFromUrl(cursor.getString(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_CONTENTS[1])));
                contentBean1.setUrl(cursor.getString(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_CONTENTS[2])));
                contentBean1.setThumb(cursor.getString(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_CONTENTS[3])));
                contentBean1.setTitle(cursor.getString(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_CONTENTS[4])));
                contentBean1.setContent(cursor.getString(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_CONTENTS[5])));
                contentBean1.setKeyWord(cursor.getString(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_CONTENTS[6])));
                contentBean1.setUpdateTime(TimeUtil.timeFormat(cursor.getLong(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_CONTENTS[7]))
                        , "yyyy-MM-dd HH:mm:ss"));
                contentBeens.add(contentBean1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
        LogUtil.e(TAG, "所有数据" + GsonUtil.getInstance().toJson(contentBeens));
        return contentBeens;
    }

    public ContentBean isHas(ContentBean contentBean) {
        LogUtil.e(TAG, "isHas " + GsonUtil.getInstance().toJson(contentBean));
        Cursor cursor = mDb.query(OpenDBUtil.TAB_CONTENTS, null, "_id = ? or (title = ? and content = ? and url = ?) "
                , new String[]{contentBean.getId() + "", TextUtils.isEmpty(contentBean.getTitle()) ? "" : contentBean.getTitle(), TextUtils.isEmpty(contentBean.getContent()) ? "" : contentBean.getContent(), TextUtils.isEmpty(contentBean.getUrl()) ? "" : contentBean.getUrl()}, null, null, null);
        try {
            ContentBean contentBean1 = new ContentBean();
            if (cursor.moveToFirst()) {
                contentBean1.setId(cursor.getLong(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_CONTENTS[0])));
                contentBean1.setFromUrl(cursor.getString(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_CONTENTS[1])));
                contentBean1.setUrl(cursor.getString(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_CONTENTS[2])));
                contentBean1.setThumb(cursor.getString(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_CONTENTS[3])));
                contentBean1.setTitle(cursor.getString(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_CONTENTS[4])));
                contentBean1.setContent(cursor.getString(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_CONTENTS[5])));
                contentBean1.setKeyWord(cursor.getString(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_CONTENTS[6])));
                contentBean1.setUpdateTime(TimeUtil.timeFormat(cursor.getLong(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_CONTENTS[7]))
                        , "yyyy-MM-dd HH:mm:ss"));
                return contentBean1;
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

    private ContentValues getValues(ContentBean contentBean) {
        ContentValues cv = new ContentValues();
        cv.put(OpenDBUtil.KEYS_TAB_CONTENTS[1], contentBean.getFromUrl());
        cv.put(OpenDBUtil.KEYS_TAB_CONTENTS[2], contentBean.getUrl());
        cv.put(OpenDBUtil.KEYS_TAB_CONTENTS[3], contentBean.getThumb());
        cv.put(OpenDBUtil.KEYS_TAB_CONTENTS[4], contentBean.getTitle());
        cv.put(OpenDBUtil.KEYS_TAB_CONTENTS[5], contentBean.getContent());
        cv.put(OpenDBUtil.KEYS_TAB_CONTENTS[6], contentBean.getKeyWord());
        cv.put(OpenDBUtil.KEYS_TAB_CONTENTS[7], System.currentTimeMillis());
        return cv;
    }
}
