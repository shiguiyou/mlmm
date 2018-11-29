package com.wanquan.mlmmx.mlmm.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mhdong on 2016/5/3.
 */
public class MyHelper extends SQLiteOpenHelper {
    //数据库名称，为了使用第三方软件打开数据库，最后加上后缀名
    public static final String NAME = "students.db";
    public static final int VERSION = 1;
    public static final String CREATE_TABLE = "create table " + StudentTable.Field.TABLE_NAME +
            "(" + StudentTable.Field._ID +
            " integer primary key autoincrement," + StudentTable.Field.STUDENT_NAME +
            " text," + StudentTable.Field.STUDENT_AGE +
            " integer)";

    /**
     * @param context 上下文参数
     * @param name    数据库的名称，最后在后面添加一个.db标志
     * @param factory 数据库操作的辅助类，一般不用
     * @param version 当前数据库版本
     */
    public MyHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    /**
     * 第一次创建数据库的时候调用，如果数据库已经存在，此方法不会再调用
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //execSQL用来执行除了查询意外的数据库操作，例如增、删、改
        db.execSQL(CREATE_TABLE);
    }

    /**
     * 升级数据库的时候使用，当发现两次数据库的VERSION值不一致，则认为数据库会升级
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
