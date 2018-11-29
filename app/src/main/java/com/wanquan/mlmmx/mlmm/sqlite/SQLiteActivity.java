package com.wanquan.mlmmx.mlmm.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.wanquan.mlmmx.mlmm.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteActivity extends AppCompatActivity {
    public static final int GROUP_ID = 1;
    public static final int DELETE_ID = 1;
    public static final int DELETE_ORDER = 1;
    public static final String DELETE = "删除";
    public static final int UPDATE_ID = 2;
    public static final int UPDATE_ORDER = 2;
    public static final String UPDATE = "修改";
    EditText mNameEditText, mAgeEditText;
    String mNameString, mAgeString;
    //声明数据库辅助类对象
    MyHelper mHelper;
    DataBaseHelper dataBaseHelper;
    //声明一个数据库操作类
    SQLiteDatabase mDatabase;
    //声明一个游标接口对象，用来遍历查询结果
    Cursor mCursor;
    ListView mListView;
    List<Student> mList;
    MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        initViews();
        initData();
        setAdapter();
        //注册上下文菜单，指明长按哪一个视图可以弹出上下文菜单
        registerForContextMenu(mListView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(GROUP_ID, DELETE_ID, DELETE_ORDER, DELETE);
        menu.add(GROUP_ID, UPDATE_ID, UPDATE_ORDER, UPDATE);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int postion = info.position;//用户单击当前行号
        int sId = mList.get(postion).getId();//当前行号对应学生信息的主键

        int id = item.getItemId();
        switch (id) {
            case DELETE_ID:
                deleteStudentById(sId);
                break;
            case UPDATE_ID:
                break;
        }
        return true;
    }

    private void deleteStudentById(int sId) {
        //删除指定学生信息
        mDatabase = mHelper.getReadableDatabase();
        /*String deleteSQL = "delete from " + StudentTable.Field.TABLE_NAME +
                " where " + StudentTable.Field._ID +
                " = " + sId;
        mDatabase.execSQL(deleteSQL);*/
        mDatabase.delete(StudentTable.Field.TABLE_NAME, StudentTable.Field._ID + " = ?", new String[]{sId + ""});
        mDatabase.close();
        setAdapter();
    }

    private void setAdapter() {
        selectAllStudent();
        mAdapter = new MyAdapter(SQLiteActivity.this, mList);
        mListView.setAdapter(mAdapter);
    }

    private void initViews() {
        mNameEditText = (EditText) findViewById(R.id.name);
        mAgeEditText = (EditText) findViewById(R.id.age);
        mListView = (ListView) findViewById(R.id.listview);
    }

    private void initData() {
        mHelper = new MyHelper(SQLiteActivity.this);

        dataBaseHelper = new DataBaseHelper(this);
        try {
            dataBaseHelper.createDataBase();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        dataBaseHelper.openDataBase();


        mList = new ArrayList<>();
    }

    public void addStudent(View view) {
        //添加学生信息
        mNameString = mNameEditText.getText().toString();
        mAgeString = mAgeEditText.getText().toString();
        //初始化sqlite数据库操作类:read和write都是既可以读又可以写，区别在于write内存不足时会抛出异常，read不会抛出异常
        mDatabase = mHelper.getReadableDatabase();
        //插入数据
        /*String insertSQL = "insert into " + StudentTable.Field.TABLE_NAME +
                "(" + StudentTable.Field.STUDENT_NAME +
                "," + StudentTable.Field.STUDENT_AGE +
                ") values('" + mNameString +
                "'," + mAgeString +
                ")";
        mDatabase.execSQL(insertSQL);*/
        //调用sqlitedatabase自带的插入数据的方法
        ContentValues values = new ContentValues();
        values.put("name", mNameString);
        values.put("age", mAgeString);
        mDatabase.insert(StudentTable.Field.TABLE_NAME, null, values);
        //关闭连接，释放资源
        mDatabase.close();
        //重新显示界面
        setAdapter();
    }

    public void selectAllStudent() {
        //mDatabase = mHelper.getReadableDatabase();
        //mList.clear();
        mDatabase = dataBaseHelper.getReadableDatabase();
        mList.clear();
        String querySQL = "select * from growth week";
        mCursor = mDatabase.rawQuery(querySQL, null);
        /*String querySQL = "select * from " + StudentTable.Field.TABLE_NAME;
        mCursor = mDatabase.rawQuery(querySQL,null);
        //sqlitedatabase提供查接口
        mCursor = mDatabase.query(StudentTable.Field.TABLE_NAME, null, null, null, null, null, null);
        //查询年龄不小于22周岁的学生信息
//        mCursor = mDatabase.query(StudentTable.Field.TABLE_NAME,null,"age >= ?",new String[]{"22"},null,null,null);
*/
        if (mCursor.moveToNext()) {
            //dfa
        }

        while (mCursor.moveToNext()) {
            int id = mCursor.getInt(mCursor.getColumnIndex("id"));
            String name = mCursor.getString(mCursor.getColumnIndex("key"));
            int age = mCursor.getInt(mCursor.getColumnIndex("dataTitle"));

            Student student = new Student(age, id, name);
//            Log.e("dongminghua", student.toString());
            mList.add(student);
        }
        mDatabase.close();
    }
}
