package com.wanquan.mlmmx.mlmm.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;

import java.util.List;

/**
 * Created by mhdong on 2016/5/4.
 */
public class MyAdapter extends BaseAdapter{
    List<Student> mList;
    Context mContext;
    LayoutInflater mInflater;

    public MyAdapter(Context context, List<Student> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(mContext);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.item,null);

        TextView nameTextView = (TextView) convertView.findViewById(R.id.t_name);
        TextView ageTextView = (TextView) convertView.findViewById(R.id.t_age);

        Student student = mList.get(position);
        String name = student.getName();
        int age = student.getAge();

        nameTextView.setText(name);
        ageTextView.setText("" + age);

        return convertView;
    }
}
