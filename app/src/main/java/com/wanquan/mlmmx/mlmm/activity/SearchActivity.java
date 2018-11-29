package com.wanquan.mlmmx.mlmm.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.MyAdapters;
import com.wanquan.mlmmx.mlmm.adapter.SearchAdapter;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.view.EditSearchView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.text.Html.fromHtml;
import static com.wanquan.mlmmx.mlmm.R.id.search_view;

/**
 * 描述：搜索
 * 作者：薛昌峰
 * 时间：2017.09.10
 */
public class SearchActivity extends BaseActivity {
    private EditSearchView searchView;
    private MyAdapters adapter;
    private List<Object> listData = new ArrayList<>();
    private String string = "";

    private class ViewHolder {
        private TextView text;
    }

    private GridView mSearchGridView;
    SearchAdapter mSearchAdapter;
    List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(SearchActivity.this, R.color.black);

        this.initValue();
        this.initCommon();
        this.initSearchEvent();

        initListeners();

        mSearchAdapter = new SearchAdapter(this, mList);//创建适配器，获取上下文
        mSearchGridView.setAdapter(mSearchAdapter);//绑定适配器
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() throws Exception {
        mSearchGridView = (GridView) findViewById(R.id.Search_gridView);
    }

    private void initListeners() {
        mSearchGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setCheckId(mList.get(i).toString());
                EventBus.getDefault().post(messageEvent);
                finish();
            }
        });
    }


    private void initSearchEvent() {
        final List<Object> items = new ArrayList<>();
        adapter = new MyAdapters(items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if (convertView == null) {
                    convertView = View.inflate(SearchActivity.this, R.layout.list_item, null);
                    holder = new ViewHolder();
                    holder.text = (TextView) convertView.findViewById(R.id.name);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                String _info = (String) adapter.getItem(position);
                if (!string.equals("") && string != null) {
                    int index = _info.toLowerCase().indexOf(string.toLowerCase());
                    int length = string.length();
                    Spanned temp = fromHtml(_info.substring(0, index)
                            + "<u><font color=#FFD700>"
                            + _info.substring(index, index + length)
                            + "</font></u>"
                            + _info.substring(index + length,
                            _info.length()));
                    holder.text.setText(temp);

                } else {
                    holder.text.setText(_info);
                }
                return convertView;
            }

            @Override
            public Filter getFilter() {
                Filter filter;
                filter = new Filter() {
                    @Override
                    protected FilterResults performFiltering(CharSequence constraint) {
                        FilterResults filterResults = new FilterResults();
                        if (!TextUtils.isEmpty(constraint)) {
                            Pattern pattern = Pattern.compile(constraint.toString().toLowerCase());
                            JSONArray array = new JSONArray();
                            for (int i = 0; i < listData.size(); i++) {
                                String data = (String) listData.get(i);
                                Matcher matcher = pattern.matcher(data.toLowerCase());
                                if (matcher.find()) {
                                    array.put(data);
                                }
                            }
                            JSONObject object = new JSONObject();
                            try {
                                object.put("data", array);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            filterResults.values = object;
                            filterResults.count = array.length();
                        }
                        return filterResults;
                    }

                    @Override
                    protected void publishResults(CharSequence constraint, FilterResults results) {
                        if (results.values != null) {
                            List<Object> list = new ArrayList<>();
                            JSONObject obj = (JSONObject) results.values;
                            if (obj.has("data")) {
                                try {
                                    JSONArray _ja = obj.getJSONArray("data");
                                    for (int i = 0; i < _ja.length(); i++) {
                                        String _info = (String) _ja.get(i);
                                        list.add(_info);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter.setData(list);
                            if (list.size() == 0) {
                                Toast toast = Toast.makeText(SearchActivity.this, "暂无查询结果！", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }
                    }
                };
                return filter;
            }
        };
        searchView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new EditSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String changeTxt) {
                string = changeTxt;
                return false;
            }
        });

        searchView.setOnSearchViewListener(new EditSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                searchView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSearchViewClosed() {
                searchView.setVisibility(View.GONE);
            }
        });

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String info = (String) adapter.getItem(position);
//                Intent intent = new Intent(SearchActivity.this,ReceiverMainActivity.class);
//                intent.putExtra("info",info);
//                setResult(RESULT_OK,intent);
//                finish();
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setCheckId(info);
                EventBus.getDefault().post(messageEvent);
                finish();
            }
        });
    }

    private void initCommon() {
        this.searchView = (EditSearchView) findViewById(R.id.search_view);
        LinearLayout mll = (LinearLayout) findViewById(R.id.mll);
        mll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setMenuItem(view);
            }
        });
    }

    private void initValue() {
        mList.add("北京市");
        mList.add("上海市");
        mList.add("东莞市");
        mList.add("深圳市");
        mList.add("郑州市");
        mList.add("苏州市");
        mList.add("南京市");
        mList.add("杭州市");
        mList.add("武汉市");
        mList.add("西安市");
        mList.add("广州市");
        mList.add("成都市");
        mList.add("重庆市");
        mList.add("台北");
        mList.add("哈尔滨市");
        mList.add("丽江市");
        mList.add("天津市");
        mList.add("厦门市");
        mList.add("青岛市");
        mList.add("沈阳市");

        listData.add("北京市");
        listData.add("天津市");
        listData.add("石家庄市");
        listData.add("唐山市");
        listData.add("秦皇岛市");
        listData.add("邯郸市");
        listData.add("邢台市");
        listData.add("保定市");
        listData.add("张家口");
        listData.add("承德市");
        listData.add("沧州市");
        listData.add("廊坊市");
        listData.add("衡水市");
        listData.add("太原市");
        listData.add("大同市");
        listData.add("阳泉市");
        listData.add("长治市");
        listData.add("晋城市");
        listData.add("朔州市");
        listData.add("忻州市");
        listData.add("吕梁市");
        listData.add("晋中市");
        listData.add("临汾市");
        listData.add("运城市");
        listData.add("呼和浩特市");
        listData.add("包头市");
        listData.add("乌海市");
        listData.add("赤峰市");
        listData.add("呼伦贝尔市");
        listData.add("兴安盟");
        listData.add("通辽市");
        listData.add("乌兰察布盟");
        listData.add("伊克昭盟");
        listData.add("巴彦淖尔盟");
        listData.add("阿拉善盟");
        listData.add("沈阳市");
        listData.add("大连市");
        listData.add("鞍山市");
        listData.add("抚顺市");
        listData.add("本溪市");
        listData.add("丹东市");
        listData.add("锦州市");
        listData.add("营口市");
        listData.add("阜新市");
        listData.add("辽阳市");
        listData.add("盘锦");
        listData.add("铁岭市");
        listData.add("朝阳市");
        listData.add("长春市");
        listData.add("吉林市");
        listData.add("四平");
        listData.add("辽源市");
        listData.add("通化市");
        listData.add("白山市");
        listData.add("松原市");
        listData.add("白城市");
        listData.add("延边朝鲜族自治州");
        listData.add("哈尔滨市");
        listData.add("齐齐哈尔");
        listData.add("鹤岗市");
        listData.add("双鸭山");
        listData.add("鸡西市");
        listData.add("大庆市");
        listData.add("伊春市");
        listData.add("牡丹江市");
        listData.add("佳木斯市");
        listData.add("七台河市");
        listData.add("黑河市");
        listData.add("绥化市");
        listData.add("大兴安岭地区");
        listData.add("上海市");
        listData.add("南京市");
        listData.add("苏州市");
        listData.add("无锡市");
        listData.add("常州市");
        listData.add("镇江市");
        listData.add("南通市");
        listData.add("泰州市");
        listData.add("扬州市");
        listData.add("盐城市");
        listData.add("连云港");
        listData.add("徐州市");
        listData.add("淮安市");
        listData.add("宿迁市");
        listData.add("杭州市");
        listData.add("宁波市");
        listData.add("温州市");
        listData.add("嘉兴市");
        listData.add("湖州市");
        listData.add("绍兴市");
        listData.add("金华市");
        listData.add("衢州市");
        listData.add("舟山市");
        listData.add("台州市");
        listData.add("丽水市");
        listData.add("其他市");
        listData.add("合肥市");
        listData.add("芜湖市");
        listData.add("蚌埠市");
        listData.add("淮南市");
        listData.add("马鞍山");
        listData.add("淮北市");
        listData.add("铜陵市");
        listData.add("安庆市");
        listData.add("黄山市");
        listData.add("滁州市");
        listData.add("阜阳市");
        listData.add("宿州市");
        listData.add("巢湖市");
        listData.add("六安市");
        listData.add("亳州市");
        listData.add("池州市");
        listData.add("宣城市");
        listData.add("福州市");
        listData.add("厦门市");
        listData.add("莆田市");
        listData.add("三明市");
        listData.add("泉州市");
        listData.add("漳州市");
        listData.add("南平市");
        listData.add("龙岩市");
        listData.add("宁德市");
        listData.add("南昌市");
        listData.add("景德镇");
        listData.add("萍乡市");
        listData.add("九江市");
        listData.add("新余市");
        listData.add("鹰潭市");
        listData.add("赣州市");
        listData.add("吉安市");
        listData.add("宜春市");
        listData.add("抚州市");
        listData.add("上饶市");
        listData.add("济南市");
        listData.add("青岛市");
        listData.add("淄博市");
        listData.add("枣庄市");
        listData.add("东营市");
        listData.add("烟台市");
        listData.add("潍坊市");
        listData.add("济宁市");
        listData.add("泰安市");
        listData.add("威海市");
        listData.add("日照市");
        listData.add("莱芜市");
        listData.add("临沂市");
        listData.add("德州市");
        listData.add("聊城市");
        listData.add("滨州市");
        listData.add("菏泽市");
        listData.add("郑州市");
        listData.add("开封市");
        listData.add("洛阳市");
        listData.add("平顶山");
        listData.add("安阳市");
        listData.add("鹤壁市");
        listData.add("新乡市");
        listData.add("焦作市");
        listData.add("濮阳市");
        listData.add("许昌市");
        listData.add("漯河市");
        listData.add("三门峡");
        listData.add("南阳市");
        listData.add("商丘市");
        listData.add("信阳市");
        listData.add("周口市");
        listData.add("驻马店");
        listData.add("焦作市");
        listData.add("武汉市");
        listData.add("黄石市");
        listData.add("十堰市");
        listData.add("荆州市");
        listData.add("宜昌市");
        listData.add("襄樊市");
        listData.add("鄂州市");
        listData.add("荆门市");
        listData.add("孝感市");
        listData.add("黄冈市");
        listData.add("咸宁市");
        listData.add("随州市");
        listData.add("恩施土家族苗族自治州");
        listData.add("仙桃市");
        listData.add("天门市");
        listData.add("潜江市");
        listData.add("神农架林区");
        listData.add("长沙市");
        listData.add("株洲市");
        listData.add("湘潭市");
        listData.add("衡阳市");
        listData.add("邵阳市");
        listData.add("岳阳市");
        listData.add("常德市");
        listData.add("张家界");
        listData.add("益阳市");
        listData.add("郴州市");
        listData.add("永州市");
        listData.add("怀化市");
        listData.add("娄底市");
        listData.add("湘西土家族苗族自治州");
        listData.add("广州市");
        listData.add("深圳市");
        listData.add("东莞市");
        listData.add("中山市");
        listData.add("潮州市");
        listData.add("揭阳市");
        listData.add("云浮市");
        listData.add("珠海市");
        listData.add("汕头市");
        listData.add("韶关市");
        listData.add("佛山市");
        listData.add("江门市");
        listData.add("湛江市");
        listData.add("茂名市");
        listData.add("肇庆市");
        listData.add("惠州市");
        listData.add("梅州市");
        listData.add("汕尾市");
        listData.add("河源市");
        listData.add("阳江市");
        listData.add("清远市");
        listData.add("南宁市");
        listData.add("柳州市");
        listData.add("桂林市");
        listData.add("梧州市");
        listData.add("北海市");
        listData.add("防城港");
        listData.add("钦州市");
        listData.add("贵港市");
        listData.add("玉林市");
        listData.add("百色市");
        listData.add("贺州市");
        listData.add("河池市");
        listData.add("来宾市");
        listData.add("崇左市");
        listData.add("其他市");
        listData.add("海口市");
        listData.add("三亚市");
        listData.add("五指山");
        listData.add("琼海市");
        listData.add("儋州市");
        listData.add("文昌市");
        listData.add("万宁市");
        listData.add("东方市");
        listData.add("澄迈县");
        listData.add("定安县");
        listData.add("屯昌县");
        listData.add("临高县");
        listData.add("白沙黎族自治县");
        listData.add("昌江黎族自治县");
        listData.add("乐东黎族自治县");
        listData.add("陵水黎族自治县");
        listData.add("保亭黎族苗族自治县");
        listData.add("琼中黎族苗族自治县");
        listData.add("重庆市");
        listData.add("成都市");
        listData.add("自贡市");
        listData.add("攀枝花");
        listData.add("泸州市");
        listData.add("德阳市");
        listData.add("绵阳市");
        listData.add("广元市");
        listData.add("遂宁市");
        listData.add("内江市");
        listData.add("乐山市");
        listData.add("南充");
        listData.add("眉山市");
        listData.add("宜宾市");
        listData.add("广安市");
        listData.add("达州市");
        listData.add("雅安市");
        listData.add("巴中市");
        listData.add("资阳市");
        listData.add("阿坝藏族羌族自治州");
        listData.add("甘孜藏族自治州");
        listData.add("凉山彝族自治州");
        listData.add("贵阳市");
        listData.add("六盘水市");
        listData.add("遵义市");
        listData.add("安顺市");
        listData.add("铜仁地区");
        listData.add("毕节地区");
        listData.add("黔西南布依族苗族自治");
        listData.add("黔东南苗族侗族自治州");
        listData.add("黔南布依族苗族自治州");
        listData.add("昆明市");
        listData.add("曲靖市");
        listData.add("玉溪市");
        listData.add("保山市");
        listData.add("昭通市");
        listData.add("丽江市");
        listData.add("普洱市");
        listData.add("临沧市");
        listData.add("德宏傣族景颇族自治州");
        listData.add("怒江傈僳族自治州");
        listData.add("迪庆藏族自治州");
        listData.add("大理白族自治州");
        listData.add("楚雄彝族自治州");
        listData.add("红河哈尼族彝族自治州");
        listData.add("文山壮族苗族自治州");
        listData.add("西双版纳傣族自治州");
        listData.add("拉萨市");
        listData.add("那曲地区");
        listData.add("昌都地区");
        listData.add("林芝地区");
        listData.add("山南地区");
        listData.add("日喀则地区");
        listData.add("西安市");
        listData.add("铜川市");
        listData.add("宝鸡市");
        listData.add("咸阳市");
        listData.add("渭南市");
        listData.add("延安市");
        listData.add("汉中市");
        listData.add("榆林市");
        listData.add("安康市");
        listData.add("商洛市");
        listData.add("甘肃省");
        listData.add("嘉峪关");
        listData.add("金昌市");
        listData.add("白银市");
        listData.add("天水市");
        listData.add("武威市");
        listData.add("酒泉市");
        listData.add("张掖市");
        listData.add("庆阳市");
        listData.add("平凉市");
        listData.add("定西市");
        listData.add("陇南市");
        listData.add("临夏回族自治州");
        listData.add("甘南藏族自治州");
        listData.add("西宁市");
        listData.add("海东地区");
        listData.add("海北藏族自治州");
        listData.add("海南藏族自治州");
        listData.add("黄南藏族自治州");
        listData.add("果洛藏族自治州");
        listData.add("玉树藏族自治州");
        listData.add("海西蒙古族藏族自治州");
        listData.add("银川市");
        listData.add("石嘴山市");
        listData.add("吴忠市");
        listData.add("固原市");
        listData.add("中卫市");
        listData.add("乌鲁木齐市");
        listData.add("克拉玛依市");
        listData.add("吐鲁番地区");
        listData.add("哈密地区");
        listData.add("和田地区");
        listData.add("阿克苏地区");
        listData.add("喀什地区");
        listData.add("克孜勒苏柯尔克孜自治州");
        listData.add("巴音郭楞蒙古自治州");
        listData.add("昌吉回族自治州");
        listData.add("博尔塔拉蒙古自治州");
        listData.add("石河子");
        listData.add("阿拉尔");
        listData.add("图木舒克");
        listData.add("五家渠");
        listData.add("伊犁哈萨克自治州");
        listData.add("台北市");
        listData.add("新北市");
        listData.add("桃园市");
        listData.add("台南市");
        listData.add("高雄市");
        listData.add("澳门");
        listData.add("香港");
    }

    public void search_close(View view) {
        finish();
    }

}
