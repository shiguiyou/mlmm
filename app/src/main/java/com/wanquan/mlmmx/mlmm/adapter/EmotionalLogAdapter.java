package com.wanquan.mlmmx.mlmm.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.BabyPhonesDeleteBeans;
import com.wanquan.mlmmx.mlmm.beans.EmotionalLogBeans;
import com.wanquan.mlmmx.mlmm.beans.EmotionalLogImgBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MessagePicturesLayout;
import com.wanquan.mlmmx.mlmm.voice.AudioEntity;
import com.wanquan.mlmmx.mlmm.voice.AudioPlaybackManager;
import com.wanquan.mlmmx.mlmm.voice.CommonSoundItemView;
import com.wanquan.mlmmx.mlmm.voice.PaoPaoTips;

import org.apache.commons.io.IOUtils;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/19.
 */

public class EmotionalLogAdapter extends BaseAdapter {
    private List<EmotionalLogBeans.DataBean> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private voideInterface voideInterface;
    private MediaPlayer mediaPlayer;
    private boolean isPlay = true;
    private MessagePicturesLayout.Callback mCallback;


    public EmotionalLogAdapter(Context mContext, List<EmotionalLogBeans.DataBean> mList) {
        this.mList = mList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public EmotionalLogAdapter setPictureClickLogCallback(MessagePicturesLayout.Callback callback) {
        mCallback = callback;
        return this;
    }

    public void setVoide(voideInterface voideInterface) {
        this.voideInterface = voideInterface;
    }

    public interface voideInterface {
        void voideEmotion(String work);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //缓存布局中的文件
    class ViewHolder {
        LinearLayout mItemLl;
        ImageView mItemEmotionalLv;
        TextView mItemEmotionalTitle;
        TextView mItemEmotionalContent;
        ImageView mItemEmotionalVoice;
        TextView mItemEmotionalSize;
        TextView mItemEmotionalTime;
        TextView mItemEmotionalDelete;
        MessagePicturesLayout mItemEmotionalPictures;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_emotional_log, null);
            viewHolder = new ViewHolder();
            viewHolder.mItemLl = (LinearLayout) convertView.findViewById(R.id.Item_ll);
            viewHolder.mItemEmotionalLv = (ImageView) convertView.findViewById(R.id.item_Emotional_lv);
            viewHolder.mItemEmotionalTitle = (TextView) convertView.findViewById(R.id.item_Emotional_Title);
            viewHolder.mItemEmotionalContent = (TextView) convertView.findViewById(R.id.item_Emotional_Content);
            viewHolder.mItemEmotionalPictures = (MessagePicturesLayout) convertView.findViewById(R.id.item_Emotional_pictures);
            viewHolder.mItemEmotionalVoice = (ImageView) convertView.findViewById(R.id.item_Emotional_Voice);
            viewHolder.mItemEmotionalSize = (TextView) convertView.findViewById(R.id.item_Emotional_Size);
            viewHolder.mItemEmotionalTime = (TextView) convertView.findViewById(R.id.item_Emotional_Time);
            viewHolder.mItemEmotionalDelete = (TextView) convertView.findViewById(R.id.item_Emotional_Delete);
            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
            viewHolder.mItemEmotionalPictures.setCallback(mCallback);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //显示内容
        final EmotionalLogBeans.DataBean mEmotionalLogBeans = mList.get(position);
        if (!"".equals(mEmotionalLogBeans.getDateReminder())) {
            viewHolder.mItemEmotionalTitle.setText(mEmotionalLogBeans.getDateReminder());
            viewHolder.mItemEmotionalTitle.setVisibility(View.VISIBLE);
        }
        if (!"".equals(mEmotionalLogBeans.getContent())) {
            viewHolder.mItemEmotionalContent.setText(mEmotionalLogBeans.getContent());
            viewHolder.mItemEmotionalContent.setVisibility(View.VISIBLE);
        }
        if (mEmotionalLogBeans.getVoice() != null) {
            viewHolder.mItemEmotionalVoice.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mItemEmotionalVoice.setVisibility(View.GONE);
        }
        if (mEmotionalLogBeans.getTime() != null) {
            viewHolder.mItemEmotionalSize.setText(mEmotionalLogBeans.getTime() + "''");
        }
        viewHolder.mItemEmotionalTime.setText(mEmotionalLogBeans.getCreateTime());
        String img = mEmotionalLogBeans.getImg();
        if (img != null) {
            String[] imgs = img.split("\\|");
            viewHolder.mItemEmotionalPictures.set(Arrays.asList(imgs), Arrays.asList(imgs));
        }
//        viewHolder.mItemEmotionalVoice.setBackground(mContext.getResources().getDrawable(R.mipmap.voice));

        viewHolder.mItemEmotionalVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlay) {
                    isPlay = false;
//                    viewHolder.mItemEmotionalVoice.setBackground(mContext.getResources().getDrawable(R.mipmap.bofang));
                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(mList.get(position).getVoice());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                viewHolder.mItemEmotionalVoice.setClickable(true);
//                                viewHolder.mItemEmotionalVoice.setBackground(mContext.getResources().getDrawable(R.mipmap.zanting));
                                isPlay = true;
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    mediaPlayer.pause();
//                    viewHolder.mItemEmotionalVoice.setBackground(mContext.getResources().getDrawable(R.mipmap.zanting));
                    isPlay = true;
                }
            }
        });


        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).equals("0")) {
                viewHolder.mItemEmotionalLv.setImageDrawable(mContext.getResources().getDrawable(R.color.white));
            } else {
                viewHolder.mItemEmotionalLv.setImageDrawable(mContext.getResources().getDrawable(R.color.hui));
            }
        }
        viewHolder.mItemEmotionalDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alert;
                alert = new AlertDialog.Builder(mContext, R.style.AlertDialogs).create();
                alert.show();
                //加载自定义dialog
                alert.getWindow().setContentView(R.layout.delete_dialogs);
                TextView cart_delete_title = alert.getWindow().findViewById(R.id.cart_delete_title);
                TextView cart_delete_content = alert.getWindow().findViewById(R.id.cart_delete_content);
                TextView cart_delete_cancle = alert.getWindow().findViewById(R.id.cart_delete_cancle);
                TextView cart_delete_confirm = alert.getWindow().findViewById(R.id.cart_delete_confirm);

                cart_delete_title.setText("提示消息");
                cart_delete_content.setText("慎重，再点就再也见不到我啦！这可是无法恢复的哦~");
                cart_delete_cancle.setText("取消");
                cart_delete_confirm.setText("确认");
                //取消
                alert.getWindow().findViewById(R.id.cart_delete_cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                        return;
                    }
                });
                alert.getWindow().findViewById(R.id.cart_delete_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("itfaceId", "043");
                        hashMap.put("token", SPUtils.get(mContext, "token", ""));
                        hashMap.put("id", mList.get(position).getId());
                        JSONObject jsonObject = new JSONObject(hashMap);

                        OkGo.post(UrlContent.URL).tag(this)
                                .upJson(jsonObject.toString())
                                .connTimeOut(10_000)
                                .execute(new CustomCallBackNoLoading<BabyPhonesDeleteBeans>(mContext) {
                                    @Override
                                    public void onSuccess(BabyPhonesDeleteBeans mBabyPhonesDeleteBeans, Call call, Response response) {
                                        if (mBabyPhonesDeleteBeans.getResultCode() == 1) {
//                                            voideInterface.voideEmotion(mList.get(position).getVoice());//向外暴露接口
                                            MessageEvent messageEvent = new MessageEvent();
                                            messageEvent.setDeleteId(String.valueOf(mList.get(position).getId()));
                                            EventBus.getDefault().post(messageEvent);

                                            Toast toast = Toast.makeText(mContext, "亲，删除成功啦~", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();
                                            alert.dismiss();
                                        } else {
                                            Toast.makeText(mContext, mBabyPhonesDeleteBeans.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
            }
        });

        return convertView;
    }

}
