package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.BabyActivity;
import com.wanquan.mlmmx.mlmm.activity.RelativesTeamActivity;
import com.wanquan.mlmmx.mlmm.beans.BabyActivityBeans;
import com.wanquan.mlmmx.mlmm.beans.HomeListViewBeans;
import com.wanquan.mlmmx.mlmm.beans.SettingBabyMessageBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.sqlite.DataBaseHelper;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/16.
 */

public class BabyAdapter extends BaseAdapter {
    private List<BabyActivityBeans.DataBean.BabyMessageBean> mList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private String mPreChildDate;
    private ViewHolder viewHolder;
    private String hyz_head;
    private ImgFinishInterface mImgFinishInterface;
    private int dayAll;
    private DataBaseHelper dataBaseHelper3;
    private SQLiteDatabase mDatabase3;
    private Cursor mCursor3;


    public BabyAdapter(List<BabyActivityBeans.DataBean.BabyMessageBean> list, Context context, String hyz_heads) {
        mList = list;
//        hyz_head = hyz_heads;
        Log.e("fdfaaafhyz_headzzzzz", String.valueOf(hyz_head));
        mContext = context;
        mImgFinishInterface = (ImgFinishInterface) context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public interface ImgFinishInterface {
        void finishImg();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        CircleImageView mItemBabyImg;
        ImageView mItemBabyImg2;
        TextView mItemBabyName;
        TextView mItemBabyNameship;
        TextView mItemBabySize;
        LinearLayout mItemBabyTextView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_baby, null);
            viewHolder = new ViewHolder();

            viewHolder.mItemBabyTextView = (LinearLayout) convertView.findViewById(R.id.item_baby_TextView);
            viewHolder.mItemBabyImg = (CircleImageView) convertView.findViewById(R.id.item_baby_img);
            viewHolder.mItemBabyImg2 = (ImageView) convertView.findViewById(R.id.item_baby_img2);
            viewHolder.mItemBabyName = (TextView) convertView.findViewById(R.id.item_baby_name);
            viewHolder.mItemBabyNameship = (TextView) convertView.findViewById(R.id.item_baby_nameship);
            viewHolder.mItemBabySize = (TextView) convertView.findViewById(R.id.item_baby_size);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        //判断是否显示邀请按钮
        if (mList.get(position).getAuthority() == 1) {
            viewHolder.mItemBabyTextView.setVisibility(View.VISIBLE);
        } else if (mList.get(position).getAuthority() == 0) {
            viewHolder.mItemBabyTextView.setVisibility(View.GONE);
        }

        if (mList.get(position).getOrderId() == 1) {
            viewHolder.mItemBabyImg2.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mItemBabyImg2.setVisibility(View.GONE);
        }
        viewHolder.mItemBabyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.put(mContext, "orderId", String.valueOf(mList.get(position).getOrderId()));
                Intent intent = new Intent(mContext, RelativesTeamActivity.class);
                intent.putExtra("babyid", mList.get(position).getBabyId());
                intent.putExtra("relationship", mList.get(position).getRelationship());
                intent.putExtra("nickname", mList.get(position).getNickName());
                mContext.startActivity(intent);
            }
        });
        if (mList.get(position).getSTATUS() == 0) {
            viewHolder.mItemBabyTextView.setVisibility(View.GONE);
            viewHolder.mItemBabyNameship.setVisibility(View.GONE);
            viewHolder.mItemBabyName.setText("备孕中");
            Log.e("fdfdfdfd", String.valueOf((mList.get(position).getBabyId())));
            if (mList.get(position).getHeadIco() != null) {
                Glide.with(mContext).load(mList.get(position).getHeadIco()).into(viewHolder.mItemBabyImg);
            } else {
                viewHolder.mItemBabyImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.sz));
            }
        } else if (mList.get(position).getSTATUS() == 1) {
            viewHolder.mItemBabyName.setText("怀孕中");
            viewHolder.mItemBabyNameship.setVisibility(View.GONE);
            //设置怀孕中宝宝头像
            if (mList.get(position).getPreChildDate() != null) {
                //当前时间
                DateTime now = DateTime.now();
                DateTime dateTime = DateTime.parse(String.valueOf(mList.get(position).getPreChildDate()));//设置的时间
                DateTime nows = dateTime.minusDays(280);//向前推280
                dayAll = Days.daysBetween(nows, now).getDays();//总天
                initSQLite();
            }
//            Log.e("宝宝头像", hyz_head + "xcf");
//            if (hyz_head != null) {
//                Glide.with(mContext).load("http://api.env365.cn/img/head/" + hyz_head).into(viewHolder.mItemBabyImg);
//            } else {
//                viewHolder.mItemBabyImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_launcher));
//            }
        } else if (mList.get(position).getSTATUS() == 2) {
            viewHolder.mItemBabyNameship.setVisibility(View.VISIBLE);
            viewHolder.mItemBabyName.setText(mList.get(position).getNickName());
            if (mList.get(position).getHeadIco() != null) {
                Glide.with(mContext).load(mList.get(position).getHeadIco()).into(viewHolder.mItemBabyImg);
            } else {
                viewHolder.mItemBabyImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.babyavalatar));
            }
        }
        if (mList.get(position).getRelationship().equals("爸爸")) {
            if (mList.get(position).getSex() == 1) {
                viewHolder.mItemBabyNameship.setText("儿子");
            } else {
                viewHolder.mItemBabyNameship.setText("女儿");
            }
        } else if (mList.get(position).getRelationship().equals("妈妈")) {
            if (mList.get(position).getSex() == 1) {
                viewHolder.mItemBabyNameship.setText("儿子");
            } else {
                viewHolder.mItemBabyNameship.setText("女儿");
            }
        } else if (mList.get(position).getRelationship().equals("爷爷")) {
            if (mList.get(position).getSex() == 1) {
                viewHolder.mItemBabyNameship.setText("孙子");
            } else {
                viewHolder.mItemBabyNameship.setText("孙女");
            }
        } else if (mList.get(position).getRelationship().equals("奶奶")) {
            if (mList.get(position).getSex() == 1) {
                viewHolder.mItemBabyNameship.setText("孙子");
            } else {
                viewHolder.mItemBabyNameship.setText("孙女");
            }
        } else if (mList.get(position).getRelationship().equals("姑姑")) {
            if (mList.get(position).getSex() == 1) {
                viewHolder.mItemBabyNameship.setText("侄子");
            } else {
                viewHolder.mItemBabyNameship.setText("侄女");
            }
        } else if (mList.get(position).getRelationship().equals("叔叔")) {
            if (mList.get(position).getSex() == 1) {
                viewHolder.mItemBabyNameship.setText("侄子");
            } else {
                viewHolder.mItemBabyNameship.setText("侄女");
            }
        } else if (mList.get(position).getRelationship().equals("阿姨")) {
            if (mList.get(position).getSex() == 1) {
                viewHolder.mItemBabyNameship.setText("外甥");
            } else {
                viewHolder.mItemBabyNameship.setText("外甥女");
            }
        } else if (mList.get(position).getRelationship().equals("舅舅")) {
            if (mList.get(position).getSex() == 1) {
                viewHolder.mItemBabyNameship.setText("外甥");
            } else {
                viewHolder.mItemBabyNameship.setText("外甥女");
            }
        } else if (mList.get(position).getRelationship().equals("外公")) {
            if (mList.get(position).getSex() == 1) {
                viewHolder.mItemBabyNameship.setText("外孙");
            } else {
                viewHolder.mItemBabyNameship.setText("外孙女");
            }
        } else if (mList.get(position).getRelationship().equals("外婆")) {
            if (mList.get(position).getSex() == 1) {
                viewHolder.mItemBabyNameship.setText("外孙");
            } else {
                viewHolder.mItemBabyNameship.setText("外孙女");
            }
        } else if (mList.get(position).getRelationship().equals("其它")) {
            viewHolder.mItemBabyNameship.setText("其它");
        }
        viewHolder.mItemBabySize.setText(mList.get(position).getRelativeCount() + "位亲人>>");
        return convertView;
    }

    private void initSQLite() {
        hyz_head = "";
        //怀孕中
        int newDatTime = dayAll + 1;
        if (newDatTime >= 280) {
            newDatTime = 280;
        }

        Log.e("fdfaaafnewDatTime", String.valueOf(newDatTime));

        dataBaseHelper3 = new DataBaseHelper(mContext);
        try {
            dataBaseHelper3.createDataBase();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        dataBaseHelper3.openDataBase();
        mDatabase3 = dataBaseHelper3.getReadableDatabase();
        String querySQL3 = "select * from pregnancy where day = " + newDatTime;
        mCursor3 = mDatabase3.rawQuery(querySQL3, null);
        while (mCursor3.moveToNext()) {
//            int hyz_id = mCursor3.getInt(mCursor3.getColumnIndex("id"));
//            int hyz_week = mCursor3.getInt(mCursor3.getColumnIndex("day"));
//            Double hyz_height = mCursor3.getDouble(mCursor3.getColumnIndex("height"));
//            Double hyz_weight = mCursor3.getDouble(mCursor3.getColumnIndex("weight"));
            hyz_head = mCursor3.getString(mCursor3.getColumnIndex("head"));
//            String hyz_voice = mCursor3.getString(mCursor3.getColumnIndex("voice"));
        }
        Log.e("fdfaaafhyz_head", String.valueOf(hyz_head));
        Glide.with(mContext).load("http://api.env365.cn/img/head/" + hyz_head).into(viewHolder.mItemBabyImg);

        mCursor3.close();
        mDatabase3.close();
        dataBaseHelper3.close();
    }

}

