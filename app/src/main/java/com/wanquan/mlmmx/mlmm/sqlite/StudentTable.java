package com.wanquan.mlmmx.mlmm.sqlite;

import android.provider.BaseColumns;

/**
 * Created by mhdong on 2016/5/3.
 */
public final class StudentTable {
    //创建静态内部类，具有以下属性：表的名称、学生表中主键字段，姓名字段，年龄字段
    public static class Field implements BaseColumns{
        public static final String TABLE_NAME = "student";
        public static final String STUDENT_NAME = "name";
        public static final String STUDENT_AGE = "age";
    }
}
