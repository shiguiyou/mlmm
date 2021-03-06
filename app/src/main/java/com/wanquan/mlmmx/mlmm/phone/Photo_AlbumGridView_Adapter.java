package com.wanquan.mlmmx.mlmm.phone;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.wanquan.mlmmx.mlmm.R;

import java.util.ArrayList;


/**
 * 这个是显示一个文件夹里面的所有图片时用的适配器
 */
public class Photo_AlbumGridView_Adapter extends BaseAdapter {
    final String TAG = getClass().getSimpleName();
    private Context mContext;
    private ArrayList<Photo_ImageItem_Beans> dataList;
    private ArrayList<Photo_ImageItem_Beans> selectedDataList;
    private DisplayMetrics dm;
    Photo_BitmapCache cache;

    public Photo_AlbumGridView_Adapter(Context c, ArrayList<Photo_ImageItem_Beans> dataList, ArrayList<Photo_ImageItem_Beans> selectedDataList) {
        mContext = c;
        cache = new Photo_BitmapCache();
        this.dataList = dataList;
        this.selectedDataList = selectedDataList;
        dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
    }

    public int getCount() {
        return dataList.size();
    }

    public Object getItem(int position) {
        return dataList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    Photo_BitmapCache.ImageCallback callback = new Photo_BitmapCache.ImageCallback() {
        @Override
        public void imageLoad(ImageView imageView, Bitmap bitmap,
                              Object... params) {
            if (imageView != null && bitmap != null) {
                String url = (String) params[0];
                if (url != null && url.equals((String) imageView.getTag())) {
                    ((ImageView) imageView).setImageBitmap(bitmap);
                } else {
                    Log.e(TAG, "callback, bmp not match");
                }
            } else {
                Log.e(TAG, "callback, bmp null");
            }
        }
    };

    /**
     * 存放列表项控件句柄
     */
    private class ViewHolder {
        public ImageView imageView;
        public ToggleButton toggleButton;
        public CheckBox choosetoggle;
        public TextView textView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.home_myphoto_upload_item, parent, false);
            //根据屏幕大小平均分配高度宽度
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth() - 100;
            int height = wm.getDefaultDisplay().getHeight() - 100;
            convertView.setLayoutParams(new GridView.LayoutParams(width / 4, width / 4));

            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.home_myPhotoUpload_item_image);
            viewHolder.choosetoggle = (CheckBox) convertView.findViewById(R.id.home_myPhotoUpload_item_ischeck);
            viewHolder.toggleButton = (ToggleButton) convertView.findViewById(R.id.home_myPhotoUpload_item_toggle_button);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String path;
        if (dataList != null && dataList.size() > position) path = dataList.get(position).imagePath;
        else
            path = "camera_default";
        if (path == null) {
        } else {
            if (path.contains("camera_default")) {
                viewHolder.imageView.setImageResource(Photo_Res_Utils.getDrawableID("plugin_camera_no_pictures"));
            } else {
                final Photo_ImageItem_Beans item = dataList.get(position);
                viewHolder.imageView.setTag(item.imagePath);
                cache.displayBmp(viewHolder.imageView, item.thumbnailPath, item.imagePath, callback);
            }
        }
        viewHolder.toggleButton.setTag(position);
        viewHolder.choosetoggle.setTag(position);
        viewHolder.toggleButton.setOnClickListener(new ToggleClickListener(viewHolder.choosetoggle));
        if (selectedDataList.contains(dataList.get(position))) {
            viewHolder.toggleButton.setChecked(true);
            viewHolder.choosetoggle.setChecked(true);
        } else {
            viewHolder.toggleButton.setChecked(false);
            viewHolder.choosetoggle.setChecked(false);
        }
        return convertView;
    }

    public int dipToPx(int dip) {
        return (int) (dip * dm.density + 0.5f);
    }

    private class ToggleClickListener implements OnClickListener {
        CheckBox chooseBt;
        public ToggleClickListener(CheckBox choosebt) {
            this.chooseBt = choosebt;
        }

        @Override
        public void onClick(View view) {
            if (view instanceof ToggleButton) {
                ToggleButton toggleButton = (ToggleButton) view;
                int position = (Integer) toggleButton.getTag();
                if (dataList != null && mOnItemClickListener != null && position < dataList.size()) {
                    mOnItemClickListener.onItemClick(toggleButton, position, toggleButton.isChecked(), chooseBt);
                }
            }
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }

    //外部接口，回调函数
    public interface OnItemClickListener {
        public void onItemClick(ToggleButton view, int position, boolean isChecked, CheckBox chooseBt);
    }

}
