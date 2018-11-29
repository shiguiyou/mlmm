package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.RelativesGridViewAdapter;
import com.wanquan.mlmmx.mlmm.adapter.RelativesListViewAdapter;
import com.wanquan.mlmmx.mlmm.beans.RelativesGridViewBeans;
import com.wanquan.mlmmx.mlmm.beans.RelativesListViewBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.phone.MyGridView;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MyListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：亲友团
 * 作者：薛昌峰
 * 时间：2018.01.30
 */
public class RelativesTeamActivity extends BaseActivity {
    private PullToRefreshScrollView mRelativesTeamPullToRefreshScrollView;
    private ImageView mRelativesTeamXin1;
    private ImageView mRelativesTeamXin2;
    private TextView mRelativesTeamTV;
    private CircleImageView mRelativesTeamImg1;
    private TextView mRelativesTeamTV1;
    private CircleImageView mRelativesTeamImg2;
    private TextView mRelativesTeamTV2;
    private MyListView mRelativesTeamListView;
    private MyGridView mRelativesTeamGridView;
    private List<RelativesListViewBeans.DataBean> mList1 = new ArrayList<>();//原始数据
    private List<RelativesListViewBeans.DataBean> mList5 = new ArrayList<>();//去除爸爸妈妈的数据
    private List<String> mList2 = new ArrayList<>();//截取过的数据
    private List<String> mList3 = new ArrayList<>();//所有的亲人名称
    private List<String> mList4 = new ArrayList<>();
    private List<String> mList6 = new ArrayList<>();
    private RelativesListViewAdapter mRelativesListViewAdapter;
    private RelativesGridViewAdapter mRelativesGridViewAdapter;
    private int babyid;
    private String relationship;
    private String nickname;

    @Override
    protected void onResume() {
        super.onResume();
        initData(babyid);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(RelativesTeamActivity.this, R.color.black);

        babyid = getIntent().getIntExtra("babyid", 0);
        relationship = getIntent().getStringExtra("relationship");
        nickname = getIntent().getStringExtra("nickname");
//        Log.e("fffffffff", babyid + "xcf");

        initListeners();
        initRefresh();

        mRelativesListViewAdapter = new RelativesListViewAdapter(mList5, this);
        mRelativesTeamListView.setAdapter(mRelativesListViewAdapter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_relatives_team;
    }

    @Override
    public void initView() throws Exception {
        mRelativesTeamPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.RelativesTeam_PullToRefreshScrollView);
        mRelativesTeamXin1 = (ImageView) findViewById(R.id.RelativesTeam_Xin1);
        mRelativesTeamXin2 = (ImageView) findViewById(R.id.RelativesTeam_Xin2);
        mRelativesTeamTV = (TextView) findViewById(R.id.RelativesTeam_TV);
        mRelativesTeamImg1 = (CircleImageView) findViewById(R.id.RelativesTeam_Img1);
        mRelativesTeamTV1 = (TextView) findViewById(R.id.RelativesTeam_TV1);
        mRelativesTeamImg2 = (CircleImageView) findViewById(R.id.RelativesTeam_Img2);
        mRelativesTeamTV2 = (TextView) findViewById(R.id.RelativesTeam_TV2);
        mRelativesTeamListView = (MyListView) findViewById(R.id.RelativesTeam_ListView);
        mRelativesTeamGridView = (MyGridView) findViewById(R.id.RelativesTeam_GridView);
    }

    private void initRefresh() {
//        mRelativesTeamPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mRelativesTeamPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                initData(babyid);
                initGridView();
                mRelativesTeamGridView.setAdapter(mRelativesGridViewAdapter);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//                initData(babyid);
//                initGridView();
            }
        });
    }

    private void initData(int babyid) {
        Log.e("Relatives_babyid", String.valueOf(babyid));
        Log.e("Relatives_token", String.valueOf(SPUtils.get(this, "token", "")));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "082");
        hashMap.put("babyId", babyid);
        hashMap.put("token", SPUtils.get(this, "token", ""));
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<RelativesListViewBeans>(RelativesTeamActivity.this) {
                    @Override
                    public void onSuccess(RelativesListViewBeans mRelativesListViewBeans, Call call, Response response) {
                        if (mRelativesListViewBeans.getResultCode() == 1) {
                            mList1.clear();
                            mList5.clear();
                            mList1.addAll(mRelativesListViewBeans.getData());
                            for (int i = 0; i < mList1.size(); i++) {
                                if ("妈妈".equals(mList1.get(i).getRelationship())) {
                                    continue;
                                } else if ("爸爸".equals(mList1.get(i).getRelationship())) {
                                    continue;
                                } else {
                                    mList5.add(mList1.get(i));
                                }
                            }
                            for (int i = 0; i < mList1.size(); i++) {
                                mList3.add(mList1.get(i).getRelationship());
                                if ("妈妈".equals(mList1.get(i).getRelationship())) {
                                    if (SPUtils.get(RelativesTeamActivity.this, "userid", "").equals(String.valueOf(mList1.get(i).getId()))) {
                                        mRelativesTeamXin1.setVisibility(View.VISIBLE);
                                    } else if (!SPUtils.get(RelativesTeamActivity.this, "userid", "").equals(String.valueOf(mList1.get(i).getId()))) {
                                        mRelativesTeamXin1.setVisibility(View.GONE);
                                    }
                                    Glide.with(RelativesTeamActivity.this).load(mList1.get(i).getHead_ico()).into(mRelativesTeamImg1);
                                    mRelativesTeamTV1.setText("妈妈");
                                    break;
                                } else if (!"妈妈".equals(mList1.get(i).getRelationship())) {
                                    mRelativesTeamImg1.setImageDrawable(getResources().getDrawable(R.mipmap.relativeinvitation));
                                    mRelativesTeamTV1.setText("邀请妈妈加入");
                                }
                            }

                            for (int i = 0; i < mList1.size(); i++) {
                                mList3.add(mList1.get(i).getRelationship());
                                if ("爸爸".equals(mList1.get(i).getRelationship())) {
                                    if (SPUtils.get(RelativesTeamActivity.this, "userid", "").equals(String.valueOf(mList1.get(i).getId()))) {
                                        mRelativesTeamXin2.setVisibility(View.VISIBLE);
                                    } else if (!SPUtils.get(RelativesTeamActivity.this, "userid", "").equals(String.valueOf(mList1.get(i).getId()))) {
                                        mRelativesTeamXin2.setVisibility(View.GONE);
                                    }
                                    Glide.with(RelativesTeamActivity.this).load(mList1.get(i).getHead_ico()).into(mRelativesTeamImg2);
                                    mRelativesTeamTV2.setText("爸爸");
                                    break;
                                } else if (!"爸爸".equals(mList1.get(i).getRelationship())) {
                                    mRelativesTeamImg2.setImageDrawable(getResources().getDrawable(R.mipmap.relativeinvitation));
                                    mRelativesTeamTV2.setText("邀请爸爸加入");
                                }
                            }
                            initGridView();
                            mRelativesListViewAdapter.notifyDataSetChanged();
                            mRelativesGridViewAdapter.notifyDataSetChanged();
                            mRelativesTeamPullToRefreshScrollView.onRefreshComplete();
                        } else {
                            App.ErrorToken(mRelativesListViewBeans.getResultCode(), mRelativesListViewBeans.getMsg());
                        }
                    }
                });
    }

    private void initGridView() {
        mList2.clear();
        mList4.clear();
        mList6.clear();
        for (int i = 0; i < mList5.size(); i++) {
            mList6.add(mList5.get(i).getRelationship());
//            Log.e("fdfdmList6", mList6.toString());
        }
        String[] str = null;
        str = new String[]{"爷爷", "奶奶", "姑姑", "叔叔", "阿姨", "舅舅", "外婆", "外公", "其它"};
        for (String s : str) {
            mList4.add(s);
            Log.e("fdfdmList6", mList6.toString());
        }
        //比较两个集合中不同的数据
        Collection name1 = new ArrayList<String>(mList6);
        Collection name2 = new ArrayList<String>(mList4);
        name2.removeAll(mList6);
        String title1 = name2.toString().substring(1);
        String title2 = title1.substring(0, title1.length() - 1);
        String[] ts = title2.split(",");
        for (String t : ts) {
            mList2.add(t);
//            Log.e("fdfdmList2", mList2.toString());
        }
        mRelativesGridViewAdapter = new RelativesGridViewAdapter(mList2, this);
        mRelativesTeamGridView.setAdapter(mRelativesGridViewAdapter);
        mRelativesGridViewAdapter.notifyDataSetChanged();
    }

    private void initListeners() {
        //邀请记录
        mRelativesTeamTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RelativesTeamActivity.this, InviteRecordActivity.class);
                intent.putExtra("babyid", babyid);
                intent.putExtra("nickname", nickname);
                startActivity(intent);
            }
        });
        mRelativesTeamListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.e("ffgfgfg=or", String.valueOf(SPUtils.get(RelativesTeamActivity.this, "orderId", "")));
//                Log.e("ffgfgfg=userid", String.valueOf(SPUtils.get(RelativesTeamActivity.this, "userid", "")));
//                Log.e("ffgfgfg-=id", String.valueOf(mList5.get(position).getId()));
                if (SPUtils.get(RelativesTeamActivity.this, "orderId", "").equals("1") && !SPUtils.get(RelativesTeamActivity.this, "userid", "").equals(String.valueOf(mList5.get(position).getId()))) {
                    Intent intent = new Intent(RelativesTeamActivity.this, ParentMessageActivity.class);
                    intent.putExtra("babyid", babyid);
                    intent.putExtra("id", String.valueOf(mList5.get(position).getId()));
                    intent.putExtra("relationship", mList5.get(position).getRelationship());
                    intent.putExtra("name", mList5.get(position).getNick_name());
                    intent.putExtra("authority", String.valueOf(mList5.get(position).getAuthority()));
                    intent.putExtra("nickname", nickname);
                    startActivity(intent);
                } else if (SPUtils.get(RelativesTeamActivity.this, "orderId", "").equals("1") && SPUtils.get(RelativesTeamActivity.this, "userid", "").equals(String.valueOf(mList5.get(position).getId()))) {
                    Toast toast = Toast.makeText(RelativesTeamActivity.this, "亲,您没有该权限哦！", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (SPUtils.get(RelativesTeamActivity.this, "orderId", "").equals("0") && SPUtils.get(RelativesTeamActivity.this, "userid", "").equals(String.valueOf(mList5.get(position).getId()))) {
                    Toast toast = Toast.makeText(RelativesTeamActivity.this, "亲,您没有该权限哦！", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (SPUtils.get(RelativesTeamActivity.this, "orderId", "").equals("0") && !SPUtils.get(RelativesTeamActivity.this, "userid", "").equals(String.valueOf(mList5.get(position).getId()))) {
                    Toast toast = Toast.makeText(RelativesTeamActivity.this, "亲,您没有该权限哦！", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        mRelativesTeamGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RelativesTeamActivity.this, WriteKinsfolkPhoneActivity.class);
                intent.putExtra("babyid", babyid);
                intent.putExtra("nickname", nickname);
                intent.putExtra("name", mList2.get(position).toString());
                startActivity(intent);
            }
        });

        mRelativesTeamImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mList3.contains("妈妈")) {
                    Intent intent = new Intent(RelativesTeamActivity.this, WriteKinsfolkPhoneActivity.class);
                    intent.putExtra("babyid", babyid);
                    intent.putExtra("nickname", nickname);
                    intent.putExtra("name", "妈妈");
                    startActivity(intent);
                } else if (mList3.contains("妈妈") && mRelativesTeamTV1.getText().toString().trim().equals("妈妈")) {
                    for (int i = 0; i < mList1.size(); i++) {
                        if (mList1.get(i).getRelationship().equals("妈妈")) {
                            int id = mList1.get(i).getId();
//                            Log.e("ffgfgfg=or", String.valueOf(SPUtils.get(RelativesTeamActivity.this, "orderId", "")));
//                            Log.e("ffgfgfg=userid", String.valueOf(SPUtils.get(RelativesTeamActivity.this, "userid", "")));
//                            Log.e("ffgfgfg-=id", String.valueOf(id));
                            if (SPUtils.get(RelativesTeamActivity.this, "orderId", "").equals("1") && !SPUtils.get(RelativesTeamActivity.this, "userid", "").equals(String.valueOf(id))) {
                                Intent intent = new Intent(RelativesTeamActivity.this, ParentMessageActivity.class);
                                intent.putExtra("babyid", babyid);
                                intent.putExtra("id", String.valueOf(mList1.get(i).getId()));
                                intent.putExtra("relationship", mList1.get(i).getRelationship());
                                intent.putExtra("name", mList1.get(i).getNick_name());
                                intent.putExtra("authority", String.valueOf(mList1.get(i).getAuthority()));
                                intent.putExtra("nickname", nickname);
                                startActivity(intent);
                                break;
                            } else if (SPUtils.get(RelativesTeamActivity.this, "orderId", "").equals("1") && SPUtils.get(RelativesTeamActivity.this, "userid", "").equals(String.valueOf(id))) {
                                Toast toast = Toast.makeText(RelativesTeamActivity.this, "亲,您没有该权限哦！", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            } else if (SPUtils.get(RelativesTeamActivity.this, "orderId", "").equals("0") && SPUtils.get(RelativesTeamActivity.this, "userid", "").equals(String.valueOf(id))) {
                                Toast toast = Toast.makeText(RelativesTeamActivity.this, "亲,您没有该权限哦！", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            } else if (SPUtils.get(RelativesTeamActivity.this, "orderId", "").equals("0") && !SPUtils.get(RelativesTeamActivity.this, "userid", "").equals(String.valueOf(id))) {
                                Toast toast = Toast.makeText(RelativesTeamActivity.this, "亲,您没有该权限哦！", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }
                    }
                }
            }
        });
        mRelativesTeamImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mList3.contains("爸爸")) {
                    Intent intent = new Intent(RelativesTeamActivity.this, WriteKinsfolkPhoneActivity.class);
                    intent.putExtra("babyid", babyid);
                    intent.putExtra("nickname", nickname);
                    intent.putExtra("name", "爸爸");
                    startActivity(intent);
                } else if (mList3.contains("爸爸") && mRelativesTeamTV2.getText().toString().trim().equals("爸爸")) {
                    for (int i = 0; i < mList1.size(); i++) {
                        if (mList1.get(i).getRelationship().equals("爸爸")) {
                            int id = mList1.get(i).getId();
                            if (SPUtils.get(RelativesTeamActivity.this, "orderId", "").equals("1") && !SPUtils.get(RelativesTeamActivity.this, "userid", "").equals(String.valueOf(id))) {
                                Intent intent = new Intent(RelativesTeamActivity.this, ParentMessageActivity.class);
                                intent.putExtra("babyid", babyid);
                                intent.putExtra("id", String.valueOf(mList1.get(i).getId()));
                                intent.putExtra("relationship", mList1.get(i).getRelationship());
                                intent.putExtra("name", mList1.get(i).getNick_name());
                                intent.putExtra("authority", String.valueOf(mList1.get(i).getAuthority()));
                                intent.putExtra("nickname", nickname);
                                startActivity(intent);
                                break;
                            } else if (SPUtils.get(RelativesTeamActivity.this, "orderId", "").equals("1") && SPUtils.get(RelativesTeamActivity.this, "userid", "").equals(String.valueOf(id))) {
                                Toast toast = Toast.makeText(RelativesTeamActivity.this, "亲,您没有该权限哦！", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            } else if (SPUtils.get(RelativesTeamActivity.this, "orderId", "").equals("0") && SPUtils.get(RelativesTeamActivity.this, "userid", "").equals(String.valueOf(id))) {
                                Toast toast = Toast.makeText(RelativesTeamActivity.this, "亲,您没有该权限哦！", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            } else if (SPUtils.get(RelativesTeamActivity.this, "orderId", "").equals("0") && !SPUtils.get(RelativesTeamActivity.this, "userid", "").equals(String.valueOf(id))) {
                                Toast toast = Toast.makeText(RelativesTeamActivity.this, "亲,您没有该权限哦！", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }
                    }
                }
            }
        });
    }

    public void RelativesTeamActivity_Bank(View view) {
        finish();
    }
}
