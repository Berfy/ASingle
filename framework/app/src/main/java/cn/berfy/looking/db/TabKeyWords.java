package cn.berfy.looking.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.berfy.framework.utils.GsonUtil;
import cn.berfy.framework.utils.LogUtil;
import cn.berfy.framework.utils.TimeUtil;
import cn.berfy.looking.bean.KeyWordsBean;
import cn.berfy.looking.db.base.DBHelper;
import cn.berfy.looking.db.base.OpenDBUtil;

/**
 * Created by Berfy on 2017/4/10.
 * 内容表
 */

public class TabKeyWords {

    private final String TAG = "TabKeyWords";
    private static TabKeyWords mTabKeyWords;
    private SQLiteDatabase mDb;
    private DBHelper mDbHelper;

    synchronized public static TabKeyWords getInstances() {
        synchronized (TabKeyWords.class) {
            if (null == mTabKeyWords) {
                mTabKeyWords = new TabKeyWords();
            }
            return mTabKeyWords;
        }
    }

    private TabKeyWords() {
        mDbHelper = DBHelper.getInstance();
        mDb = mDbHelper.getDb();
    }

    public void addKey(KeyWordsBean keyWordsBean) {
        try {
            mDb.beginTransaction();
            KeyWordsBean old = isHas(keyWordsBean);
            if (null != old) {
                LogUtil.e(TAG, "更新 " + GsonUtil.getInstance().toJson(keyWordsBean));
                mDb.update(OpenDBUtil.TAB_KEYWORDS, getValues(keyWordsBean), OpenDBUtil.KEYS_TAB_KEYWORDS[0] + " = ? or " + OpenDBUtil.KEYS_TAB_KEYWORDS[1] + "=?"
                        , new String[]{keyWordsBean.getId() + "", keyWordsBean.getKeyWords()});
            } else {
                LogUtil.e(TAG, "插入 " + GsonUtil.getInstance().toJson(keyWordsBean));
                mDb.insert(OpenDBUtil.TAB_KEYWORDS, null, getValues(keyWordsBean));
            }
            mDb.setTransactionSuccessful();
            mDb.endTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            mDb.endTransaction();
        }
    }

    public void deleteKey(KeyWordsBean keyWordsBean) {
        LogUtil.e(TAG, "删除内容 " + GsonUtil.getInstance().toJson(keyWordsBean));
        mDb.delete(OpenDBUtil.TAB_KEYWORDS, OpenDBUtil.KEYS_TAB_KEYWORDS[0] + " = ? or " + OpenDBUtil.KEYS_TAB_KEYWORDS[1] + "=?"
                , new String[]{keyWordsBean.getId() + "", keyWordsBean.getKeyWords()});
    }

    public KeyWordsBean isHas(KeyWordsBean keyWordsBean) {
        LogUtil.e(TAG, "isHas " + GsonUtil.getInstance().toJson(keyWordsBean));
        Cursor cursor = mDb.query(OpenDBUtil.TAB_KEYWORDS, null, OpenDBUtil.KEYS_TAB_KEYWORDS[0] + "=? or " + OpenDBUtil.KEYS_TAB_KEYWORDS[1] + "=?"
                , new String[]{keyWordsBean.getId() + "", keyWordsBean.getKeyWords()}, null, null, null);
        try {
            KeyWordsBean keyWordsBean1 = new KeyWordsBean();
            if (cursor.moveToFirst()) {
                keyWordsBean1.setId(cursor.getLong(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_KEYWORDS[0])));
                keyWordsBean1.setKeyWords(cursor.getString(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_KEYWORDS[1])));
                keyWordsBean1.setUpdateTime(TimeUtil.timeFormat(cursor.getLong(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_CONTENTS[7]))
                        , "yyyy-MM-dd HH:mm:ss"));
                return keyWordsBean1;
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

    public List<KeyWordsBean> getAllData() {
        LogUtil.e(TAG, "getAllData ");
        Cursor cursor = mDb.query(OpenDBUtil.TAB_KEYWORDS, null, null
                , null, null, null, OpenDBUtil.KEYS_TAB_KEYWORDS[2]+" desc");
        List<KeyWordsBean> keyWordsBeans = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                KeyWordsBean keyWordsBean = new KeyWordsBean();
                keyWordsBean.setId(cursor.getLong(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_KEYWORDS[0])));
                keyWordsBean.setKeyWords(cursor.getString(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_KEYWORDS[1])));
                keyWordsBean.setUpdateTime(TimeUtil.timeFormat(cursor.getLong(cursor.getColumnIndex(OpenDBUtil.KEYS_TAB_KEYWORDS[2]))
                        , "yyyy-MM-dd HH:mm:ss"));
                keyWordsBeans.add(keyWordsBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
        LogUtil.e(TAG, "所有数据" + GsonUtil.getInstance().toJson(keyWordsBeans));
        return keyWordsBeans;
    }

    public void deleteAllData() {
        LogUtil.e(TAG, "删除所有内容");
        mDb.delete(OpenDBUtil.TAB_KEYWORDS, null, null);
    }

    private ContentValues getValues(KeyWordsBean keyWordsBean) {
        ContentValues cv = new ContentValues();
        cv.put(OpenDBUtil.KEYS_TAB_KEYWORDS[1], keyWordsBean.getKeyWords());
        cv.put(OpenDBUtil.KEYS_TAB_KEYWORDS[2], System.currentTimeMillis());
        return cv;
    }
}
