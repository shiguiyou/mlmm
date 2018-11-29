package com.wanquan.mlmmx.mlmm.sqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.wanquan.mlmmx.mlmm.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class InternalActivity extends AppCompatActivity {
    public static final String SAVE = "isave.txt";
    FileOutputStream fos;
    FileInputStream fis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal);
//        InputStream is = getResources().openRawResource(R.raw.hello);
       /* deleteFile(SAVE);
        deleteFile("isave");*/
    }

    public void iWrite(View view) {
        //第一个参数，文件名称，第二个参数，文件访问模式
        try {
            fos = openFileOutput(SAVE,MODE_PRIVATE);
            //向文件中写入内容
            fos.write("hello".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void iRead(View view) {
        try {
            fis = openFileInput(SAVE);
            //字节数组，缓存读取的内容
            byte[] buffer = new byte[fis.available()];
            int len;
            //把多个字符串连接成一个字符串，效率最高的是StringBuilder
            StringBuilder builder = new StringBuilder();
            while((len = fis.read(buffer)) != -1){
                builder.append(new String(buffer));
            }
            show(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void show(String text){
        Toast.makeText(InternalActivity.this, text, Toast.LENGTH_SHORT).show();
    }

}
