package com.wanquan.mlmmx.mlmm.phone;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Photo_Bimp {
    public static int max = 0;
    public static ArrayList<Photo_ImageItem_Beans> tempSelectBitmap = new ArrayList<>();   //选择的图片的临时列表
    public static ArrayList<Photo_ImageItem_Beans> albumSelectBitmap = new ArrayList<>();   //相册图片上传的临时列表
    public static ArrayList<Photo_ImageItem_Beans> taskSelectBitmap = new ArrayList<>();   //发表任务的临时列表
    public static ArrayList<Photo_ImageItem_Beans> chatSelectBitmap = new ArrayList<>();   //聊天的临时列表
    public static int ChatType = 0;
    public static String Album_type = "";//打开相册的方式
    public static int Album_open = 0;//打开相册的方式
    public static List<Activity> mList_activity = new ArrayList<Activity>(); //activity集合
    public static List<Activity> mPreview_activity = new ArrayList<Activity>(); //activity集合

    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 1000) && (options.outHeight >> i <= 1000)) {
                in = new BufferedInputStream(new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }
}
