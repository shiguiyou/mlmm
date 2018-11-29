package com.wanquan.mlmmx.mlmm.sqlite;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wanquan.mlmmx.mlmm.R;

import java.io.File;
import java.io.IOException;

public class ExternalActivity extends AppCompatActivity {
    TextView mTextView;
    String mPath;//外部存储器公共文件夹目录
    File mFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external);
        initViews();

    }



    private void initViews() {
        mTextView = (TextView) findViewById(R.id.path);
    }

    public void saveExternal(View view) throws IOException {
        //保存文件到外部存储器,创建一个相册的文件夹
        File file = new File(mFile.getAbsolutePath(),"album.png");
        if (!file.exists()){
            show("文件不存在，正在创建");

            file.createNewFile();
        } else {
            show("文件存在");
        }

    }

    public void showExternal(View view) {
        mFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        mPath = mFile.toString();
        mTextView.setText(mPath);
    }
    //用来检查当前外部存储器是否可用
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void show(String text){
        Toast.makeText(ExternalActivity.this,text, Toast.LENGTH_LONG).show();
    }

    public void showExternalPrivate(View view) {
        //查看外部存储器私有目录
        mFile = getExternalFilesDir(null);
        mTextView.setText(mFile.toString());
    }

    public void saveExternaPrivate(View view) {
        File file = new File(mFile.getAbsolutePath(),"test");
        if (!file.exists()){
            show("文件夹不存在，需要创建");
            file.mkdirs();
        } else {
            show("文件夹存在");
        }
    }
}
