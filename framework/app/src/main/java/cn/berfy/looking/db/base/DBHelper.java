package cn.berfy.looking.db.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper mDbHelper;
    private SQLiteDatabase mDb;

    synchronized public static DBHelper init(Context context, int dbVersion, String dbName) {
        synchronized (DBHelper.class) {
            if (mDbHelper == null) {
                mDbHelper = new DBHelper(context, dbName, dbVersion);
            }
        }
        return mDbHelper;
    }

    public static DBHelper getInstance() {
        if (null == mDbHelper) {
            throw new NullPointerException("请在Application中初始化DBHelper");
        }
        return mDbHelper;
    }

    private SQLiteDatabase initDb() {
        // TODO Auto-generated method stub
        SQLiteDatabase db = getReadableDatabase();
        if (Build.VERSION.SDK_INT >= 11) {
            db.enableWriteAheadLogging();// 允许读写同时进行
        }
        if (db.isReadOnly()) {
            db = getWritableDatabase();
        }
        return db;
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }

    private DBHelper(Context context, String dbName, int dbVersion) {
        super(context, dbName, null, dbVersion);
        // TODO Auto-generated constructor stub
        mDb = initDb();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        execSql(db, OpenDBUtil.CREATE_TAB_CONTENTS);
        execSql(db, OpenDBUtil.CREATE_TAB_URLS);
        execSql(db, OpenDBUtil.CREATE_TAB_KEYWORDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }

    private void execSql(SQLiteDatabase db, String sql) {
        try {
            // 添加修改时间字段
            db.execSQL(sql);// 给记血糖表添加是否与服务器同步字段
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
