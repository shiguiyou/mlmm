package com.wanquan.mlmmx.mlmm.xmly.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MyListView;
import com.wanquan.mlmmx.mlmm.xmly.beans.ParentTaskAddBeans;
import com.wanquan.mlmmx.mlmm.xmly.beans.ParentTaskBeans;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/10/18.
 */

public class ParentTaskAdapter extends BaseAdapter {
    List<ParentTaskBeans.DataBean.VoBean> mList;
    Context mContext;
    LayoutInflater mInflater;
    ParentTaskAdapterInterface mParentTaskAdapterInterface;

    public ParentTaskAdapter(Context mContext, List<ParentTaskBeans.DataBean.VoBean> mList) {
        this.mList = mList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void ParentTaskAdapterInterface(ParentTaskAdapterInterface mParentTaskAdapterInterface) {
        this.mParentTaskAdapterInterface = mParentTaskAdapterInterface;
    }

    public interface ParentTaskAdapterInterface {
        void refresh();
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

    //缓存布局中的文件
    class ViewHolder {
        TextView mParentTaskGroupTitle;
        MyListView mParentTaskGroupListView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_group_parent, null);
            viewHolder = new ViewHolder();

            viewHolder.mParentTaskGroupTitle = (TextView) convertView.findViewById(R.id.ParentTask_Group_Title);
            viewHolder.mParentTaskGroupListView = (MyListView) convertView.findViewById(R.id.ParentTask_Group_ListView);


            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //显示内容
        final ParentTaskBeans.DataBean.VoBean mParentTaskBeans = mList.get(position);

        viewHolder.mParentTaskGroupTitle.setText("第" + mParentTaskBeans.getWeek() + "周");

        ParentTaskChildAdapter mParentTaskChildAdapter = new ParentTaskChildAdapter(mContext, mList.get(position).getList());
        viewHolder.mParentTaskGroupListView.setAdapter(mParentTaskChildAdapter);

        viewHolder.mParentTaskGroupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int positions, long id) {
                if (SPUtils.get(mContext, "authority", "").equals("0")) {
                    Toast.makeText(mContext, "亲，您还没有权限哦！", Toast.LENGTH_SHORT).show();
                } else {
                    if (mParentTaskBeans.getList().get(positions).getDone().equals("0")) {
                        initNetWork("070", positions);
                    } else if (mParentTaskBeans.getList().get(positions).getDone().equals("1")) {
                        initNetWork("071", positions);
                    }
                }
            }

            private void initNetWork(final String itfaceId, int positions) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("itfaceId", itfaceId);
                hashMap.put("token", SPUtils.get(mContext, "token", ""));
                hashMap.put("babyTaskId", mParentTaskBeans.getList().get(positions).getId());
                hashMap.put("babyId", SPUtils.get(mContext, "babyId", ""));

                JSONObject jsonObject = new JSONObject(hashMap);

                OkGo.post(UrlContent.URL).tag(this)
                        .upJson(jsonObject.toString())
                        .connTimeOut(10_000)
                        .execute(new CustomCallBackNoLoading<ParentTaskAddBeans>(mContext) {
                            @Override
                            public void onSuccess(ParentTaskAddBeans mParentTaskAddBeans, Call call, Response response) {
                                if (mParentTaskAddBeans.getResultCode() == 1) {
                                    if (itfaceId.equals("070")) {

                                    } else if (itfaceId.equals("071")) {

                                    }
                                    mParentTaskAdapterInterface.refresh();
                                    if (mParentTaskAddBeans.getData().getExtra() != 0) {
                                        Toast.makeText(mContext, "完成该项任务积分+" + mParentTaskAddBeans.getData().getExtra(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(mContext, mParentTaskAddBeans.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        return convertView;
    }
}




