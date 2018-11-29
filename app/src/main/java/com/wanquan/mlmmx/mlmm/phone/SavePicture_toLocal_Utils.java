package com.wanquan.mlmmx.mlmm.phone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 描述：保存图片到本地 和 存放缓存 功能
 * 作者：xcf
 * 时间：2016.7.16
 */
public class SavePicture_toLocal_Utils {
    private static final String SAVE_PIC_PATH= Environment.getExternalStorageState().
            equalsIgnoreCase(Environment.MEDIA_MOUNTED) ? Environment.
            getExternalStorageDirectory().getAbsolutePath():"/mnt/sdcard";//保存到SD卡
    private static final String SAVE_REAL_PATH = SAVE_PIC_PATH+"/Multshows/MS_savePic";//保存的确切位置

     //保存至本地路径下的文件夹
    public static void saveFile(Bitmap bm, String fileName, String path, Context context) throws IOException {
        String subForder = SAVE_REAL_PATH +path;
        //判断该路径是否存在，如否，则创建
        File foder = new File(subForder);
        if (!foder.exists()) {
            foder.mkdirs();
        }
        File myCaptureFile = new File(subForder, fileName);
        if (!myCaptureFile.exists()) {
            myCaptureFile.createNewFile();
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.PNG, 80, bos);
        bos.flush();
        bos.close();
        Toast.makeText(context, "保存本地成功", Toast.LENGTH_SHORT).show();
        //广播通知更新图库
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(myCaptureFile);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    //保存至本地路径APP临时缓存文件夹
    public static File saveCacheFile(Bitmap bm, String fileName, Context context) throws IOException {
        String subForder=context.getExternalCacheDir().getPath();
        Log.e("PATH",subForder);
        //String subForder = SAVE_REAL_PATH +path;
        //判断该路径是否存在，如否，则创建
        File foder = new File(subForder);
        if (!foder.exists()) {
            foder.mkdirs();
        }
        File myCaptureFile = new File(subForder, fileName);
        if (!myCaptureFile.exists()) {
            myCaptureFile.createNewFile();
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.PNG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }
}