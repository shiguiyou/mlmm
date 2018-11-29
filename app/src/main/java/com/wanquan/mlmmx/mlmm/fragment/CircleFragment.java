package com.wanquan.mlmmx.mlmm.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.CircleDetailNewActivity;
import com.wanquan.mlmmx.mlmm.activity.CircleDetailsActivity;
import com.wanquan.mlmmx.mlmm.activity.IntegralStrategyActivity;
import com.wanquan.mlmmx.mlmm.activity.LoginActivity;
import com.wanquan.mlmmx.mlmm.activity.MyCircleActivity;
import com.wanquan.mlmmx.mlmm.activity.PostPersonageCenterActivity;
import com.wanquan.mlmmx.mlmm.activity.SelectCircleActivity;
import com.wanquan.mlmmx.mlmm.activity.SendInvitationActivity;
import com.wanquan.mlmmx.mlmm.activity.SendInvitationClassifyActivity;
import com.wanquan.mlmmx.mlmm.adapter.CircleFragmentOneListViewAdapter;
import com.wanquan.mlmmx.mlmm.adapter.CircleFragmentTwoListViewAdapter;
import com.wanquan.mlmmx.mlmm.adapter.CircleFragmentTwoListViewTwoAdapter;
import com.wanquan.mlmmx.mlmm.adapter.MyAdapter;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentBeans;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentThreeBeans;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentThreessBeans;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentTwoBeans;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentTwoBeansTwo;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.DensityUtil;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.DKDragView;
import com.wanquan.mlmmx.mlmm.view.MyListView;
import com.wanquan.mlmmx.mlmm.view.MyScrollView;
import com.wanquan.mlmmx.mlmm.view.NoScrollViewPager;
import com.wanquan.mlmmx.mlmm.view.SonnyJackDragView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.net.IDN;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：圈子
 * 时间：2017/11/15
 * 作者：薛昌峰
 */
public class CircleFragment extends BaseFragment implements CircleFragmentOneListViewAdapter.InterfaceIsCollection {
    private ImageView mCircleFragmentSave;
    private LinearLayout mCircleFragmentLinearLayout1;
    private TextView mCircleFragmentOneName;
    private ImageView mCircleFragmentOneImg;
    private LinearLayout mCircleFragmentLinearLayout2;
    private TextView mCircleFragmentTwoName;
    private ImageView mCircleFragmentTwoImg;
    private TextView mCircleFragmentThreeName;
    private TextView mCircleFragmentContent;
    private PullToRefreshScrollView mCircleFragmentPullToRefreshScrollView;
    private MyListView mCircleFragmentMyListView;
    private LinearLayout mCircleFragmentThreeLinearLayout;
    private MyListView mCircleFragmentThreeMyListViewLeft;
    private MyListView mCircleFragmentThreeMyListViewRight;
    private LinearLayout mCircleFragmentButtonLl;
    private TextView mCircleFragmentButtonContent;
    private TextView mCircleFragmentButtonTv;
    private ImageView mCircleFragmentSendTextView;


    private List<CircleFragmentBeans.DataBean> mList1 = new ArrayList();
    private CircleFragmentOneListViewAdapter mCircleFragmentOneRecyclerViewAdapter;

    private CircleFragmentTwoListViewAdapter mCircleFragmentTwoListViewAdapter;
    private CircleFragmentTwoListViewTwoAdapter mCircleFragmentTwoListViewTwoAdapter;
    private List<CircleFragmentTwoBeans.DataBean> mList2 = new ArrayList<>();
    private List<CircleFragmentTwoBeansTwo.DataBean> mList3 = new ArrayList<>();

    private CircleFragmentThreeLiftAdapter mCircleFragmentThreeLiftAdapter;
    private CircleFragmentThreeRightAdapter mCircleFragmentThreeRightAdapter;
    private List<CircleFragmentThreeBeans.DataBean> mList4 = new ArrayList<>();
    private List<CircleFragmentThreessBeans.DataBean> mList5 = new ArrayList<>();
    public static int mLiftId;
    private String s = "";

    private PopupWindow popupWindow = null;//更多弹出框

    private int page = 1;
    private View view;
    private int flag = 1;//标记是点击了动态，关注还是圈子
    private int dongtaiFlag = 1;//标记是全部动态是好友动态
    private String finishId = "048";
    private int page2;
    private String circleflag;

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {

            if (circleflag != null) {
                if (circleflag.equals("1")) {
                    mCircleFragmentOneRecyclerViewAdapter = new CircleFragmentOneListViewAdapter(mList1, getContext(), 1);
                    mCircleFragmentMyListView.setAdapter(mCircleFragmentOneRecyclerViewAdapter);
                }
            }
            initData(finishId);
            if ("108".equals(finishId)) {
                initData3();
                mCircleFragmentContent.setVisibility(View.VISIBLE);
                mCircleFragmentContent.setText("");
            }

        } else {
            mList1.clear();
            mList2.clear();
            mList3.clear();
            mList4.clear();
            mList5.clear();

            mCircleFragmentOneRecyclerViewAdapter = new CircleFragmentOneListViewAdapter(mList1, getContext(), 1);
            mCircleFragmentMyListView.setAdapter(mCircleFragmentOneRecyclerViewAdapter);

            mCircleFragmentTwoListViewAdapter = new CircleFragmentTwoListViewAdapter(mList2, getContext());
            mCircleFragmentMyListView.setAdapter(mCircleFragmentTwoListViewAdapter);

            mCircleFragmentTwoListViewTwoAdapter = new CircleFragmentTwoListViewTwoAdapter(mList3, getContext());
            mCircleFragmentMyListView.setAdapter(mCircleFragmentTwoListViewTwoAdapter);


            mCircleFragmentThreeLiftAdapter = new CircleFragmentThreeLiftAdapter(mList4, getContext(), 0);
            mCircleFragmentThreeMyListViewLeft.setAdapter(mCircleFragmentThreeLiftAdapter);

            mCircleFragmentThreeRightAdapter = new CircleFragmentThreeRightAdapter(mList5, getContext(), 0);
            mCircleFragmentThreeMyListViewRight.setAdapter(mCircleFragmentThreeRightAdapter);


            mCircleFragmentContent.setVisibility(View.GONE);
            mCircleFragmentMyListView.setEmptyView(mCircleFragmentButtonLl);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_circle, container, false);

        //注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

//        Intent intent = getActivity().getIntent();
//        //从intent取出bundle
//        Bundle bundle = intent.getBundleExtra("circle");
//        circleflag = bundle.getString("circleflag");

        initViews();
//        initData("048");
        initRefresh();
        initListeners();


        return view;
    }

    private void initViews() {
        mCircleFragmentSave = (ImageView) view.findViewById(R.id.CircleFragment_Save);
        mCircleFragmentLinearLayout1 = (LinearLayout) view.findViewById(R.id.CircleFragment_LinearLayout1);
        mCircleFragmentOneName = (TextView) view.findViewById(R.id.CircleFragment_One_Name);
        mCircleFragmentOneImg = (ImageView) view.findViewById(R.id.CircleFragment_One_Img);
        mCircleFragmentLinearLayout2 = (LinearLayout) view.findViewById(R.id.CircleFragment_LinearLayout2);
        mCircleFragmentTwoName = (TextView) view.findViewById(R.id.CircleFragment_Two_Name);
        mCircleFragmentTwoImg = (ImageView) view.findViewById(R.id.CircleFragment_Two_Img);
        mCircleFragmentThreeName = (TextView) view.findViewById(R.id.CircleFragment_Three_Name);
        mCircleFragmentContent = (TextView) view.findViewById(R.id.CircleFragment_Content);
        mCircleFragmentPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.CircleFragment_PullToRefreshScrollView);
        mCircleFragmentMyListView = (MyListView) view.findViewById(R.id.CircleFragment_MyListView);
        mCircleFragmentThreeLinearLayout = (LinearLayout) view.findViewById(R.id.CircleFragmentThree_LinearLayout);
        mCircleFragmentThreeMyListViewLeft = (MyListView) view.findViewById(R.id.CircleFragmentThree_MyListView_Left);
        mCircleFragmentThreeMyListViewRight = (MyListView) view.findViewById(R.id.CircleFragmentThree_MyListView_Right);
        mCircleFragmentButtonLl = (LinearLayout) view.findViewById(R.id.CircleFragmentButton_ll);
        mCircleFragmentButtonContent = (TextView) view.findViewById(R.id.CircleFragmentButton_Content);
        mCircleFragmentButtonTv = (TextView) view.findViewById(R.id.CircleFragmentButton_Tv);
        mCircleFragmentSendTextView = (ImageView) view.findViewById(R.id.CircleFragment_Send_TextView);

    }

    private void initRefresh() {
        mCircleFragmentPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mCircleFragmentPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                initData(finishId);
                mCircleFragmentPullToRefreshScrollView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page++;
                initData(finishId);
                mCircleFragmentPullToRefreshScrollView.onRefreshComplete();
            }
        });
    }

    private void initData(String id) {
        Log.e("finishId", finishId);
        Log.e("finishIdpage", String.valueOf(page));

        if ("048".equals(id)) {
            mCircleFragmentContent.setText("全部动态");
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("itfaceId", "048");
            hashMap.put("token", SPUtils.get(getContext(), "token", ""));
            hashMap.put("pageNo", page);
            hashMap.put("pageSize", "10");
            JSONObject jsonObject = new JSONObject(hashMap);

            OkGo.post(UrlContent.URL).tag(this)
                    .upJson(jsonObject.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<CircleFragmentBeans>(getContext()) {
                        @Override
                        public void onSuccess(CircleFragmentBeans mCircleFragmentBeans, Call call, Response response) {
                            if (mCircleFragmentBeans.getResultCode() == 1) {
                                if (page == 1) {
                                    mList1.clear();
                                }

                                mList1.addAll(mCircleFragmentBeans.getData());
                                mCircleFragmentOneRecyclerViewAdapter = new CircleFragmentOneListViewAdapter(mList1, getContext(), 1);
                                mCircleFragmentMyListView.setAdapter(mCircleFragmentOneRecyclerViewAdapter);

                                if (mList1.size() == 0) {
                                    mCircleFragmentButtonTv.setVisibility(View.GONE);
                                    mCircleFragmentButtonContent.setText("亲，暂时没有任何动态哦~");
                                    mCircleFragmentButtonLl.setVisibility(View.VISIBLE);
                                } else {
                                    mCircleFragmentButtonLl.setVisibility(View.GONE);
                                }
                                mCircleFragmentPullToRefreshScrollView.onRefreshComplete();
                            } else {
                                mCircleFragmentPullToRefreshScrollView.onRefreshComplete();
                                App.ErrorToken(mCircleFragmentBeans.getResultCode(), mCircleFragmentBeans.getMsg());
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                                Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else if ("121".equals(id)) {
            mCircleFragmentContent.setText("朋友动态");
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("itfaceId", "121");
            hashMap.put("token", SPUtils.get(getContext(), "token", ""));
            hashMap.put("pageNo", page);
            hashMap.put("pageSize", "10");
            JSONObject jsonObject = new JSONObject(hashMap);

            OkGo.post(UrlContent.URL).tag(this)
                    .upJson(jsonObject.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<CircleFragmentBeans>(getContext()) {
                        @Override
                        public void onSuccess(CircleFragmentBeans mCircleFragmentBeans, Call call, Response response) {
                            if (mCircleFragmentBeans.getResultCode() == 1) {
                                if (mCircleFragmentBeans.getData() != null) {
                                    if (page == 1) {
                                        mList1.clear();
                                    }
                                    mList1.addAll(mCircleFragmentBeans.getData());
                                    mCircleFragmentOneRecyclerViewAdapter = new CircleFragmentOneListViewAdapter(mList1, getContext(), 1);
                                    mCircleFragmentMyListView.setAdapter(mCircleFragmentOneRecyclerViewAdapter);

                                    if (mList1.size() == 0) {
                                        mCircleFragmentButtonTv.setVisibility(View.GONE);
                                        mCircleFragmentButtonContent.setText("亲，暂时没有任何动态哦~");
                                        mCircleFragmentButtonLl.setVisibility(View.VISIBLE);
                                    } else {
                                        mCircleFragmentButtonLl.setVisibility(View.GONE);
                                    }
                                }
                                mCircleFragmentPullToRefreshScrollView.onRefreshComplete();
                            } else {
                                mCircleFragmentPullToRefreshScrollView.onRefreshComplete();
                                App.ErrorToken(mCircleFragmentBeans.getResultCode(), mCircleFragmentBeans.getMsg());
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            Toast.makeText(mActivity, "网络连接异常，请稍后重试！", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if ("116".equals(id)) {
            mCircleFragmentContent.setText("关注的圈子");
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("itfaceId", "116");
            hashMap.put("token", SPUtils.get(getContext(), "token", ""));
            JSONObject jsonObject = new JSONObject(hashMap);

            OkGo.post(UrlContent.URL).tag(this)
                    .upJson(jsonObject.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<CircleFragmentTwoBeans>(getContext()) {
                        @Override
                        public void onSuccess(CircleFragmentTwoBeans mCircleFragmentTwoBeans, Call call, Response response) {
                            if (mCircleFragmentTwoBeans.getResultCode() == 1) {
                                mList2.clear();
                                mList2.addAll(mCircleFragmentTwoBeans.getData());
                                mCircleFragmentContent.setText("我关注的圈子 " + "(" + mList2.size() + ")");
                                mCircleFragmentTwoListViewAdapter = new CircleFragmentTwoListViewAdapter(mList2, getContext());
                                mCircleFragmentMyListView.setAdapter(mCircleFragmentTwoListViewAdapter);

                                if (mList2.size() == 0) {
                                    mCircleFragmentButtonTv.setVisibility(View.GONE);
                                    mCircleFragmentButtonContent.setText("亲，您还没有关注过任何圈子哦~");
                                    mCircleFragmentButtonLl.setVisibility(View.VISIBLE);
                                } else {
                                    mCircleFragmentButtonLl.setVisibility(View.GONE);
                                }
                                mCircleFragmentPullToRefreshScrollView.onRefreshComplete();

                            } else {
                                mCircleFragmentPullToRefreshScrollView.onRefreshComplete();

                                App.ErrorToken(mCircleFragmentTwoBeans.getResultCode(), mCircleFragmentTwoBeans.getMsg());
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            Toast.makeText(mActivity, "网络连接异常，请稍后重试！", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if ("119".equals(id)) {
            mCircleFragmentContent.setText("关注的人");
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("itfaceId", "119");
            hashMap.put("token", SPUtils.get(getContext(), "token", ""));
            hashMap.put("pageNo", page);
            hashMap.put("pageSize", "10");
            JSONObject jsonObject = new JSONObject(hashMap);

            OkGo.post(UrlContent.URL).tag(this)
                    .upJson(jsonObject.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<CircleFragmentTwoBeansTwo>(getContext()) {
                        @Override
                        public void onSuccess(CircleFragmentTwoBeansTwo mCircleFragmentTwoBeansTwo, Call call, Response response) {
                            if (mCircleFragmentTwoBeansTwo.getResultCode() == 1) {
                                if (page == 1) {
                                    mList3.clear();
                                }
                                mList3.addAll(mCircleFragmentTwoBeansTwo.getData());

                                mCircleFragmentTwoListViewTwoAdapter = new CircleFragmentTwoListViewTwoAdapter(mList3, getContext());
                                mCircleFragmentMyListView.setAdapter(mCircleFragmentTwoListViewTwoAdapter);

                                if (mList3.size() == 0) {
                                    mCircleFragmentContent.setText("我关注的人 " + "(0)");
                                    mCircleFragmentButtonTv.setVisibility(View.GONE);
                                    mCircleFragmentButtonContent.setText("亲，您还没有关注过任何人哦~");
                                    mCircleFragmentButtonLl.setVisibility(View.VISIBLE);
                                } else {
                                    mCircleFragmentContent.setText("我关注的人 " + "(" + mList3.get(0).getFollowTotal() + ")");
                                    mCircleFragmentButtonLl.setVisibility(View.GONE);
                                }
                            } else {
                                App.ErrorToken(mCircleFragmentTwoBeansTwo.getResultCode(), mCircleFragmentTwoBeansTwo.getMsg());
                            }
                            mCircleFragmentPullToRefreshScrollView.onRefreshComplete();
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                                Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void initListeners() {
        mCircleFragmentSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), IntegralStrategyActivity.class);
                if (SPUtils.get(getContext(), "ParentChildGameflas", "").equals("true")) {
                    intent.putExtra("flags", IntegralStrategyActivity.flags);
                } else {
                    intent.putExtra("flags", IntegralStrategyActivity.flags);
                }
                startActivity(intent);
            }
        });
        mCircleFragmentThreeMyListViewRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getContext(), CircleDetailNewActivity.class);
                intent.putExtra("id", String.valueOf(mList5.get(position).getId()));
                startActivity(intent);
            }
        });

        mCircleFragmentButtonTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        mCircleFragmentMyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (flag == 1) {
                    Intent intent = new Intent(getContext(), CircleDetailsActivity.class);
                    intent.putExtra("id", mList1.get(position).getId());
                    intent.putExtra("userId", mList1.get(position).getUserId());
                    intent.putExtra("headico", mList1.get(position).getHeadIco());
                    intent.putExtra("name", mList1.get(position).getUserName());
                    intent.putExtra("name2", mList1.get(position).getNickName());
                    intent.putExtra("message", mList1.get(position).getMessage());
                    intent.putExtra("time", mList1.get(position).getCreateTime());
                    intent.putExtra("createTime", mList1.get(position).getCreateTime());
                    intent.putExtra("commentCount", mList1.get(position).getCommentCount());
                    intent.putExtra("title", mList1.get(position).getTitle());
                    intent.putExtra("content", mList1.get(position).getContent());
                    intent.putExtra("img", mList1.get(position).getImg());
                    intent.putExtra("html", mList1.get(position).getLink());
                    if (mList1.get(position).isCollect()) {
                        intent.putExtra("follow", 1);
                    } else {
                        intent.putExtra("follow", 0);
                    }
                    startActivity(intent);
                } else {
                    if (finishId.equals("116")) {
                        Intent intent = new Intent(getContext(), CircleDetailNewActivity.class);
                        intent.putExtra("id", String.valueOf(mList2.get(position).getCircleId()));
                        startActivity(intent);
                    } else if (finishId.equals("119")) {
                        Intent intent = new Intent(getContext(), PostPersonageCenterActivity.class);
                        intent.putExtra("id", String.valueOf(mList3.get(position).getUid()));
                        startActivity(intent);
                    }
                }
            }
        });

//        mCircleFragmentSendTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                boolean needNearEdge = mSonnyJackDragView.getNeedNearEdge();
//                mSonnyJackDragView.setNeedNearEdge(!needNearEdge);
//                if (needNearEdge) {
//                    mButton.setText("移至边沿");
//                } else {
//                    mButton.setText("固定位置");
//                }
//            }
//        });


        mCircleFragmentLinearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    mCircleFragmentOneImg.setBackground(getResources().getDrawable(R.mipmap.circleup));
                    mCircleFragmentTwoImg.setBackground(getResources().getDrawable(R.mipmap.circledown));

                    flag = 1;
                    if (flag == 1) {
                        mList2.clear();
                        mList3.clear();
                        mList4.clear();
                        mList5.clear();
                    } else if (flag == 2) {
                        mList1.clear();
                        mList4.clear();
                        mList5.clear();
                    } else {
                        mList1.clear();
                        mList2.clear();
                        mList3.clear();
                    }

                    initPopupWindow(1);
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });

        mCircleFragmentLinearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    mCircleFragmentOneImg.setBackground(getResources().getDrawable(R.mipmap.circledown));
                    mCircleFragmentTwoImg.setBackground(getResources().getDrawable(R.mipmap.circleup));
                    flag = 2;
                    if (flag == 1) {
                        mList2.clear();
                        mList3.clear();
                        mList4.clear();
                        mList5.clear();
                    } else if (flag == 2) {
                        mList1.clear();
                        mList4.clear();
                        mList5.clear();
                    } else {
                        mList1.clear();
                        mList2.clear();
                        mList3.clear();
                    }

                    initPopupWindow(2);
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }

            }
        });

        mCircleFragmentThreeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    finishId = "108";
                    mCircleFragmentOneImg.setBackground(getResources().getDrawable(R.mipmap.circledown));
                    mCircleFragmentTwoImg.setBackground(getResources().getDrawable(R.mipmap.circledown));
                    mCircleFragmentContent.setText("");
                    flag = 3;

                    mCircleFragmentOneName.setTextColor(getResources().getColor(R.color.black));
                    mCircleFragmentTwoName.setTextColor(getResources().getColor(R.color.black));
                    mCircleFragmentThreeName.setTextColor(getResources().getColor(R.color.circletevc));

                    mCircleFragmentOneImg.setBackground(getResources().getDrawable(R.mipmap.circledown));
                    mCircleFragmentTwoImg.setBackground(getResources().getDrawable(R.mipmap.circledown));

                    mCircleFragmentMyListView.setVisibility(View.GONE);
                    mCircleFragmentThreeLinearLayout.setVisibility(View.VISIBLE);

                    if (flag == 1) {
                        mList2.clear();
                        mList3.clear();
                        mList4.clear();
                        mList5.clear();
                    } else if (flag == 2) {
                        mList1.clear();
                        mList4.clear();
                        mList5.clear();
                    } else {
                        mList1.clear();
                        mList2.clear();
                        mList3.clear();
                    }
                    initData3();
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
        mCircleFragmentThreeMyListViewLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCircleFragmentThreeLiftAdapter = new CircleFragmentThreeLiftAdapter(mList4, getContext(), position);
                mCircleFragmentThreeMyListViewLeft.setAdapter(mCircleFragmentThreeLiftAdapter);
                mCircleFragmentThreeMyListViewLeft.setSelectionFromTop(position, 0);
                mLiftId = mList4.get(position).getId();
                initNetWork(mLiftId);
            }
        });

    }

    private void initData3() {
        mCircleFragmentMyListView.setVisibility(View.GONE);
        mCircleFragmentThreeLinearLayout.setVisibility(View.VISIBLE);
        if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("itfaceId", "108");
            hashMap.put("token", SPUtils.get(getContext(), "token", ""));
            hashMap.put("parentId", 0);
            hashMap.put("related", "1");
            JSONObject jsonObject = new JSONObject(hashMap);

            OkGo.post(UrlContent.URL).tag(this)
                    .upJson(jsonObject.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<CircleFragmentThreeBeans>(getContext()) {
                        @Override
                        public void onSuccess(CircleFragmentThreeBeans mCircleFragmentThreeBeans, Call call, Response response) {
                            if (mCircleFragmentThreeBeans.getResultCode() == 1) {
                                mList4.clear();
                                mList4.addAll(mCircleFragmentThreeBeans.getData());
                                mCircleFragmentThreeLiftAdapter = new CircleFragmentThreeLiftAdapter(mList4, getContext(), 0);
                                mCircleFragmentThreeMyListViewLeft.setAdapter(mCircleFragmentThreeLiftAdapter);
                                mLiftId = mList4.get(0).getId();
                                initNetWork(mLiftId);
                            } else {
                                App.ErrorToken(mCircleFragmentThreeBeans.getResultCode(), mCircleFragmentThreeBeans.getMsg());
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                                Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void initNetWork(final int id) {
        Log.e("xxxxxid2", String.valueOf(id));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "108");
        hashMap.put("token", SPUtils.get(getContext(), "token", ""));
        hashMap.put("parentId", id);
        hashMap.put("related", "1");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<CircleFragmentThreessBeans>(getContext()) {
                    @Override
                    public void onSuccess(CircleFragmentThreessBeans mCircleFragmentThreessBeans, Call call, Response response) {
                        if (mCircleFragmentThreessBeans.getResultCode() == 1) {
                            mList5.clear();
                            mList5.addAll(mCircleFragmentThreessBeans.getData());

                            mCircleFragmentThreeRightAdapter = new CircleFragmentThreeRightAdapter(mList5, getContext(), id);
                            mCircleFragmentThreeMyListViewRight.setAdapter(mCircleFragmentThreeRightAdapter);
                        } else {
                            App.ErrorToken(mCircleFragmentThreessBeans.getResultCode(), mCircleFragmentThreessBeans.getMsg());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                            Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        page = 1;

        //是否显示返回键
        if (event.isShowBank()) {
            mCircleFragmentSave.setVisibility(View.VISIBLE);
        } else {
            mCircleFragmentSave.setVisibility(View.GONE);
        }

        //取消关注圈子
        String s1 = event.getQZid();
        String s2 = event.getItFaceId();

        if (s2 != null) {
            initData("119");
        }

        if (s1 != null) {
            initNetWork(mLiftId);
        }

        //取消关注的动态爱心显示
//        if (event.getItFaceCollect()) {
//            if (dongtaiFlag == 1) {
//                initData("048");
//            } else {
//                initData("121");
//            }
//        }
    }

    private void initPopupWindow(final int size) {
        //显示自定义布局
        View mPopupView = LayoutInflater.from(getContext()).inflate(R.layout.circle_popupwindow, null);
        View parent = LayoutInflater.from(getContext()).inflate(R.layout.home_circle, null);
        TextView textView1 = mPopupView.findViewById(R.id.CircleFragment_PopupWindow_tv1);
        TextView textView2 = mPopupView.findViewById(R.id.CircleFragment_PopupWindow_tv2);

        mPopupView.setFocusable(true);
        mPopupView.setFocusableInTouchMode(true);
        popupWindow = new PopupWindow(mPopupView, DensityUtil.dip2px(getContext(), 120), LinearLayout.LayoutParams.WRAP_CONTENT, true);
        //设置背景色
        //popupWindow.setBackgroundDrawable(new ColorDrawable(0x90000000));
        int[] location = new int[2];

        //按back键将退出更多选择框
        mPopupView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();
                    popupWindow = null;
                    return true;
                }
                return false;
            }
        });
        // 设置允许在外点击消失
        //点击popupwindow外的空白区域，popupwindow消失
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //刷新状态
        popupWindow.update();
        //显示弹出框
        if (size == 1) {
            textView1.setText("全部动态");
            textView2.setText("朋友动态");
            popupWindow.showAtLocation(parent, Gravity.TOP | Gravity.LEFT, location[0] + 40, location[1] + 180);
        } else if (size == 2) {
            textView1.setText("关注的圈子");
            textView2.setText("关注的人");
            popupWindow.showAtLocation(parent, Gravity.TOP | Gravity.LEFT, location[0] + 250, location[1] + 180);
        }
        //one
        mPopupView.findViewById(R.id.CircleFragment_PopupWindow_tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                mCircleFragmentOneImg.setBackground(getResources().getDrawable(R.mipmap.circledown));
                mCircleFragmentTwoImg.setBackground(getResources().getDrawable(R.mipmap.circledown));
                if (size == 1) {
                    dongtaiFlag = 1;
                    mCircleFragmentOneName.setTextColor(getResources().getColor(R.color.circletevc));
                    mCircleFragmentTwoName.setTextColor(getResources().getColor(R.color.black));
                    mCircleFragmentThreeName.setTextColor(getResources().getColor(R.color.black));

                    mCircleFragmentMyListView.setVisibility(View.VISIBLE);
                    mCircleFragmentThreeLinearLayout.setVisibility(View.GONE);
                    mList2.clear();
                    mList3.clear();
                    finishId = "048";
                    initData("048");//全部动态
                } else if (size == 2) {
                    mCircleFragmentOneName.setTextColor(getResources().getColor(R.color.black));
                    mCircleFragmentTwoName.setTextColor(getResources().getColor(R.color.circletevc));
                    mCircleFragmentThreeName.setTextColor(getResources().getColor(R.color.black));

                    mCircleFragmentMyListView.setVisibility(View.VISIBLE);
                    mCircleFragmentThreeLinearLayout.setVisibility(View.GONE);
                    mList1.clear();
                    mList3.clear();
                    finishId = "116";
                    initData("116");//关注的圈子
                }
                popupWindow.dismiss();
            }
        });
        //two
        mPopupView.findViewById(R.id.CircleFragment_PopupWindow_tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                mCircleFragmentOneImg.setBackground(getResources().getDrawable(R.mipmap.circledown));
                mCircleFragmentTwoImg.setBackground(getResources().getDrawable(R.mipmap.circledown));
                if (size == 1) {
                    dongtaiFlag = 2;
                    mCircleFragmentOneName.setTextColor(getResources().getColor(R.color.circletevc));
                    mCircleFragmentTwoName.setTextColor(getResources().getColor(R.color.black));
                    mCircleFragmentThreeName.setTextColor(getResources().getColor(R.color.black));

                    mCircleFragmentMyListView.setVisibility(View.VISIBLE);
                    mCircleFragmentThreeLinearLayout.setVisibility(View.GONE);
                    mList2.clear();
                    mList3.clear();
                    finishId = "121";
                    initData("121");//朋友动态
                } else if (size == 2) {
                    mCircleFragmentOneName.setTextColor(getResources().getColor(R.color.black));
                    mCircleFragmentTwoName.setTextColor(getResources().getColor(R.color.circletevc));
                    mCircleFragmentThreeName.setTextColor(getResources().getColor(R.color.black));

                    mCircleFragmentMyListView.setVisibility(View.VISIBLE);
                    mCircleFragmentThreeLinearLayout.setVisibility(View.GONE);
                    mList1.clear();
                    mList2.clear();
                    finishId = "119";
                    initData("119");//关注的人
                }
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在界面销毁的地方要解绑
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void isCollection(String str) {
        initData("048");
    }
}
