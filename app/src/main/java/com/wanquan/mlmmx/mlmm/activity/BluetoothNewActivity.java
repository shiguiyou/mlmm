package com.wanquan.mlmmx.mlmm.activity;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.BluetoothNewBeans;
import com.wanquan.mlmmx.mlmm.beans.HomeEquipmentBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.ListDataSave;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MyDialog_Views;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：蓝牙搜索界面
 * 作者：薛昌峰
 * 时间：2018.01.11
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BluetoothNewActivity extends BaseActivity {
    private android.bluetooth.BluetoothAdapter bluetooth;
    private RelativeLayout mScanAction;
    private CheckBox checkSwitchButton;
    private StringBuffer buffer = new StringBuffer();
    private String name = "", mac = "";
    private ImageView mBluetooth;
    private BlueToothReceiver mBlueToothReceiver;

    private TextView tv_status;
    private TextView txt_search;
    private List<BluetoothDevice> devicelist = new ArrayList<>();

    public static BluetoothGattCharacteristic controlCharacteristicl, notifyCharacteristic, batteryCharacteristic;
    private Map<String, Integer> map;

    private String str1 = "0";//包头
    private String str2 = "0";//设备id号
    public static BluetoothAdapter bluetoothAdapter;
    public static BluetoothDevice bluetoothDevice;
    public static BluetoothGatt bluetoothGatt;
    Handler handler = new Handler();

    int int1;
    int int2;
    int int3;
    int int4;
    int int5;

    private boolean run = true;
    Handler handlers = new Handler();
    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (run) {
                handlers.postDelayed(this, 5000);
                int1 = 0;
                int2 = 0;
                int3 = 0;
                int4 = 0;
                int5 = 0;
                //  十六进制转化为十进制
                if (!String.valueOf(Integer.parseInt(str1, 16)).equals("")) {
                    int1 = Integer.parseInt(str1, 16);
                    String ss = "0C";
                    String sss = "01";
                    int2 = Integer.parseInt(ss, 16);
                    int3 = Integer.parseInt(sss, 16);
                }
                if (!String.valueOf(Integer.parseInt(str2, 16)).equals("")) {
                    int4 = Integer.parseInt(str2, 16);
                    String ssss = "0D0A";
                    int5 = Integer.parseInt(ssss, 16);
                }
                num = int1 + int2 + int3 + int4 + int5;
                Log.e("xcfNUM9999999", num + "");

//                十进制转化为十六进制
                if (num != 0) {
                    number = Integer.toHexString(num);
                    Log.e("xcfNUMBER9999999", number + "");
                }

                if (controlCharacteristicl != null && bluetoothGatt != null) {
                    controlCharacteristicl.setValue(new byte[]{
                            (byte) 0xEB, (byte) 0x90,
                            0x00, 0x0B,
                            0x01, 0x00,
                            0x00, 0x00,
                            0x01, 0x00,
                            0x0D, 0x0D, 0x0A});
                    bluetoothGatt.writeCharacteristic(controlCharacteristicl);
                    Log.e("xcf发送信息成功", "发送信息成功");
                }
            }
        }
    };
    private String number = "0";
    private int num = 0;
    Boolean recharge_flag = true;
    private Dialog dialog;
    private ListView listView;
    private String flag;
    public static String single;

    @Override
    protected void onResume() {
        super.onResume();
        task.run();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        flag = getIntent().getStringExtra("flag");
//        Toast.makeText(this, flag, Toast.LENGTH_SHORT).show();

        SPUtils.put(BluetoothNewActivity.this, "istwo", "true");
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //注册BroadcastRecever
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);

        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_bluetooth_new;
    }

    @Override
    public void initView() throws Exception {
        listView = (ListView) findViewById(R.id.listview_device);
        mBluetooth = (ImageView) findViewById(R.id.bluetooth);
        bluetooth = android.bluetooth.BluetoothAdapter.getDefaultAdapter();
        tv_status = (TextView) findViewById(R.id.tv_status);
        txt_search = (TextView) findViewById(R.id.txt_search);
        mScanAction = (RelativeLayout) findViewById(R.id.scanAction);
    }

    //发现设备广播接收器
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //发现服务发现远端设备时
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //从Intent对象中获取BluetoothDevice对象信息
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //当发现的新设备不存在于设备配对列表中时，将设备的信息添加到adapter适配器中
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    if (!devicelist.contains(device)) {
//                  adapter.add(device.getName() + device.getAddress());
                        Log.e("xcfble", device.getName() + device.getAddress());
                        listView.setAdapter(adapter);
                        devicelist.add(device);
                    }
                }
            }
            //搜索完成
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(true);//关闭进度条
                txt_search.setText("搜索完成！");
            }
        }
    };

    private void initListeners() {
        if (bluetoothAdapter.isEnabled()) {
            setProgressBarIndeterminateVisibility(true);
            txt_search.setText("正在搜索...");
            if (bluetoothAdapter.isDiscovering()) { //如果适配器正在搜索，则取消搜索
                bluetoothAdapter.cancelDiscovery();
            }
            bluetoothAdapter.startDiscovery();//开始搜索
        } else {
            bluetoothAdapter.enable();
        }

        mScanAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                devicelist.clear();
                if (bluetoothAdapter.isEnabled()) {
                    setProgressBarIndeterminateVisibility(true);
                    txt_search.setText("正在搜索...");
                    if (bluetoothAdapter.isDiscovering()) { //如果适配器正在搜索，则取消搜索
                        bluetoothAdapter.cancelDiscovery();
                    }
                    bluetoothAdapter.startDiscovery();//开始搜索
                } else {
                    bluetoothAdapter.enable();
                }
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                name = devicelist.get(position).getName();
                mac = devicelist.get(position).getAddress();

                SPUtils.put(BluetoothNewActivity.this, "name", name);
                SPUtils.put(BluetoothNewActivity.this, "mac", mac);

                bluetoothAdapter.stopLeScan(mScanCallback);//停止扫描
                bluetoothDevice = devicelist.get(position);
                if (null != bluetoothGatt) {
                    bluetoothGatt.close();
                }
                if ("100".equals(flag)) {
                    bluetoothGatt = bluetoothDevice.connectGatt(BluetoothNewActivity.this, false, gattCallback);
                    tv_status.setText("连接中...");
                } else if ("101".equals(flag)) {
                    initBlueName(name, mac);
                }
            }
        });
    }

    //启动扫描功能
    private LeScanCallback mScanCallback = new LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice bluetoothDevice, int rssi, byte[] values) {
            Log.e("xcfvvvvvvvvv", " name:" + bluetoothDevice.getName() + "  mac:" + bluetoothDevice.getAddress());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!devicelist.contains(bluetoothDevice)) {
                        devicelist.add(bluetoothDevice);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
            if ("100".equals(flag)) {
                bluetoothGatt = bluetoothDevice.connectGatt(BluetoothNewActivity.this, false, gattCallback);
                tv_status.setText("连接中...");
            } else if ("101".equals(flag)) {
//                initBlueName(name, mac);
            }
        }
    };

    BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            //byte[] single = characteristic.getValue();
            single = bytes2HexString(characteristic.getValue());
            Log.e("xcf", " 收到蓝牙返回值---start：" + single);
            String[] ss = single.split(" ");
            if (buffer.length() == 0) {
                int i = 0;
                while (i < ss.length) {
                    if (ss[i].equalsIgnoreCase("EB") && ss[i + 1].equalsIgnoreCase("90")) {
                        break;
                    }
                    i++;
                }
                int len = ss.length - i;
                String[] header = new String[len];
                System.arraycopy(ss, i, header, 0, len);
                //buffer.append(header);
                for (String str : header) {
                    buffer.append(str);
                }
            } else {
                int i = 0;
                boolean flag = false;
                while (i < ss.length) {
                    if ((ss[i].equalsIgnoreCase("0D")) && (ss[i + 1].equalsIgnoreCase("0A"))) {
                        flag = true;
                        break;
                    }
                    i++;
                }
                int len = i + 2;
                String[] tail = new String[len];
                System.arraycopy(ss, 0, tail, 0, len);
                //buffer.append(tail);
                for (String str : tail) {
                    buffer.append(str);
                }
                if (flag) {
                    String str = buffer.toString();
                    String strs = str.substring(0, 62);
                    Log.e("xcf", "整包 ：" + strs);
                    //数据包解析
                    byte[] bts = hexStringNoSpace2Bytes(strs);
                    //包头
                    str1 = buffer.substring(0, 4);
                    str2 = buffer.substring(16, 18);//设备id号

                    if (bts[4] == 0x04) {
                        int length = ((bts[2] << 8) & 0xff) + (bts[3] & 0xff);
                        int l = length - 2 - 1 - 4 - 2 - 2;
                        byte[] data = new byte[l];
                        System.arraycopy(bts, 9, data, 0, l);
                        int num = data.length / 3;
                        map = new HashMap<>();
                        for (int j = 0; j < num; j++) {
                            int start = j * 3;
                            byte[] tmp = new byte[3];
                            System.arraycopy(data, start, tmp, 0, 3);
                            int value = 0;
                            switch (tmp[0]) {
                                case 0x01:
                                    value = ((tmp[1] & 0xff) << 8) + (tmp[2] & 0xff);
                                    map.put("pm25", value);
                                    SPUtils.put(BluetoothNewActivity.this, "pm25", value);
                                    break;
                                case 0x02:
                                    value = ((tmp[1] & 0xff) << 8) + (tmp[2] & 0xff);
                                    map.put("pm10", value);
                                    SPUtils.put(BluetoothNewActivity.this, "pm10", value);
                                    break;
                                case 0x03:
                                    value = ((tmp[1] & 0xff) << 8) + (tmp[2] & 0xff);
                                    map.put("wendu", value - 1000);
                                    SPUtils.put(BluetoothNewActivity.this, "wendu", value - 1000);
                                    break;
                                case 0x04:
                                    value = ((tmp[1] & 0xff) << 8) + (tmp[2] & 0xff);
                                    map.put("shidu", value);
                                    SPUtils.put(BluetoothNewActivity.this, "shidu", value);
                                    break;
                                case 0x0A:
                                    value = ((tmp[1] & 0xff) << 8) + (tmp[2] & 0xff);
                                    map.put("dianliang", value);
                                    String values = String.valueOf(value);
                                    SPUtils.put(BluetoothNewActivity.this, "dianliang", values);
                                    break;
                                case 0x0E:
                                    value = ((tmp[1] & 0xff) << 8) + (tmp[2] & 0xff);
                                    map.put("pm03", value);
                                    SPUtils.put(BluetoothNewActivity.this, "pm03", String.valueOf(value));
                                    break;
                                case 0x0F:
                                    value = ((tmp[1] & 0xff) << 8) + (tmp[2] & 0xff);
                                    map.put("chongdian", value);
                                    SPUtils.put(BluetoothNewActivity.this, "chongdian", value);
                                    break;
                            }
                        }
                        Set<String> keys = map.keySet();
                        for (String key : keys) {
                            Log.e("xcf", "数据值 ：" + key + ":" + map.get(key));

                            String pm25 = String.valueOf(map.get("pm25"));
                            String wendu = String.valueOf(map.get("wendu"));
                            String shidu = String.valueOf(map.get("shidu"));
                            String dianliang = String.valueOf(map.get("dianliang"));
                            String pm03 = String.valueOf(map.get("pm03"));
                            String chongdian = String.valueOf(map.get("chongdian"));

                            SPUtils.put(BluetoothNewActivity.this, "pm25", pm25);
                            SPUtils.put(BluetoothNewActivity.this, "wendu", wendu);
                            SPUtils.put(BluetoothNewActivity.this, "shidu", shidu);
                            SPUtils.put(BluetoothNewActivity.this, "dianliang", dianliang);
                            SPUtils.put(BluetoothNewActivity.this, "pm03", pm03);
                            SPUtils.put(BluetoothNewActivity.this, "chongdian", chongdian);
                        }
                        Log.e("xcf", "数据包 ：" + bytes2HexString(data));
                    }
                    buffer.setLength(0);
                }
                if (len < ss.length) {
                    while (len < ss.length) {
                        if (ss[len].equalsIgnoreCase("EB") && ss[len + 1].equalsIgnoreCase("90")) {
                            break;
                        }
                        len++;
                    }
                    int length = ss.length - len;
                    String[] header = new String[len];
                    System.arraycopy(ss, len, header, 0, length);
                    for (String str : header) {
                        buffer.append(str);
                    }
                }
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            // TODO Auto-generated method stub
            super.onCharacteristicRead(gatt, characteristic, status);
//            if (status == BluetoothGatt.GATT_SUCCESS) {
//                if (characteristic.getUuid().toString().equals(batteryCharacteristic.getUuid().toString())) {
//                    Log.e("", "获取到电量：" + characteristic.getValue()[0]);
//                }
//            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            // TODO Auto-generated method stub
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        //连接状态改变方法
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, final int newState) {
            // TODO Auto-generated method stub
            super.onConnectionStateChange(gatt, status, newState);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (newState) {
                        case BluetoothGatt.STATE_CONNECTED:
                            if (!SPUtils.get(BluetoothNewActivity.this, "name", "").equals("")/*&&!SPUtils.get(BluetoothNewActivity.this, "mac", "").equals("")*/) {

                                SPUtils.put(BluetoothNewActivity.this, "off", "1");
                                SPUtils.put(BluetoothNewActivity.this, "bluename", name);
                                //连接后立刻发一次心跳
                                int1 = 0;
                                int2 = 0;
                                int3 = 0;
                                int4 = 0;
                                int5 = 0;
                                //  十六进制转化为十进制
                                if (!String.valueOf(Integer.parseInt(str1, 16)).equals("")) {
                                    int1 = Integer.parseInt(str1, 16);
                                    String ss = "0C";
                                    String sss = "01";
                                    int2 = Integer.parseInt(ss, 16);
                                    int3 = Integer.parseInt(sss, 16);
                                }
                                if (!String.valueOf(Integer.parseInt(str2, 16)).equals("")) {
                                    int4 = Integer.parseInt(str2, 16);
                                    String ssss = "0D0A";
                                    int5 = Integer.parseInt(ssss, 16);
                                }
                                num = int1 + int2 + int3 + int4 + int5;
                                Log.e("xcfNUM9999999", num + "");

                                //十进制转化为十六进制
                                if (num != 0) {
                                    number = Integer.toHexString(num);
                                    Log.e("xcfNUMBER9999999", number + "");
                                }

                                if (controlCharacteristicl != null && bluetoothGatt != null) {
                                    controlCharacteristicl.setValue(new byte[]{
                                            (byte) 0xEB, (byte) 0x90,
                                            0x00, 0x0B,
                                            0x01, 0x00,
                                            0x00, 0x00,
                                            Byte.parseByte(str2), 0x00,
                                            0x00, 0x0D,
                                            0x0D, 0x0A});
                                    bluetoothGatt.writeCharacteristic(controlCharacteristicl);
                                    Log.e("xcf发送信息成功", "发送信息成功");
                                }

                                bluetoothGatt.discoverServices();// 寻找服务
                                bluetoothAdapter.stopLeScan(mScanCallback);

                                finish();
                            }
                            break;
                        case BluetoothGatt.STATE_CONNECTING:
                            tv_status.setText("连接中...");
                            break;
                        case BluetoothGatt.STATE_DISCONNECTED:
                            tv_status.setText("已断开");
                            SPUtils.put(BluetoothNewActivity.this, "off", "0");
                            if (bluetoothGatt != null) {
                                bluetoothGatt.close();
                            }
                            bluetoothAdapter.startLeScan(mScanCallback);
                            break;
                        case BluetoothGatt.STATE_DISCONNECTING:
                            tv_status.setText("正在断开...");
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            // TODO Auto-generated method stub
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            // TODO Auto-generated method stub
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            // TODO Auto-generated method stub
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            // TODO Auto-generated method stub
            super.onReliableWriteCompleted(gatt, status);
        }

        //寻找到服务
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            // TODO Auto-generated method stub
            super.onServicesDiscovered(gatt, status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                List<BluetoothGattService> services = bluetoothGatt.getServices();
                for (BluetoothGattService bluetoothGattService : services) {
                    Log.e("xcf", " server:" + bluetoothGattService.getUuid().toString());
                    List<BluetoothGattCharacteristic> characteristics = bluetoothGattService.getCharacteristics();
                    for (BluetoothGattCharacteristic bluetoothGattCharacteristic : characteristics) {
                        Log.e("xcf", " charac:" + bluetoothGattCharacteristic.getUuid().toString());
//                        if (bluetoothGattCharacteristic.getUuid().toString().equals("00002a08-0000-1000-8000-00805f9b34fb")) {
                        controlCharacteristicl = bluetoothGattCharacteristic;
                        notifyCharacteristic = bluetoothGattCharacteristic;

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                enableNotification(true, notifyCharacteristic);
                                for (BluetoothGattDescriptor dp : notifyCharacteristic.getDescriptors()) {
                                    dp.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                                    bluetoothGatt.writeDescriptor(dp);
                                }
                            }
                        }, 500);

                        batteryCharacteristic = bluetoothGattCharacteristic;
                        bluetoothGatt.readCharacteristic(batteryCharacteristic);
//                        if (bluetoothGattCharacteristic.getUuid().toString().equals("0000ffe1-0000-1000-8000-00805f9b34fb")) {
//                        } else if (bluetoothGattCharacteristic.getUuid().toString().equals("0000ffe1-0000-1000-8000-00805f9b34fb")) {
//                        } else if (bluetoothGattCharacteristic.getUuid().toString().equals("0000ffe1-0000-1000-8000-00805f9b34fb")) {
//                        }
                    }
                }
            }
        }
    };

    private void initBlueName(String name, String mac) {
        if ("true".equals(String.valueOf(SPUtils.get(BluetoothNewActivity.this, "istwo", "")))) {
            Log.e("Dsadsadsadsa", "到了");
            HashMap<String, Object> hashMap2 = new HashMap<>();
            hashMap2.put("itfaceId", "110");
            hashMap2.put("token", SPUtils.get(BluetoothNewActivity.this, "token", ""));
            hashMap2.put("mac", mac);
            hashMap2.put("name", name);
            JSONObject jsonObject2 = new JSONObject(hashMap2);

            OkGo.post(UrlContent.URL).tag(BluetoothNewActivity.this)
                    .upJson(jsonObject2.toString())
//                .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<BluetoothNewBeans>(BluetoothNewActivity.this) {
                        @Override
                        public void onSuccess(BluetoothNewBeans mBluetoothNewBeans, Call call, Response response) {
                            Log.e("asfafasf", String.valueOf(mBluetoothNewBeans.getResultCode()));
                            if (mBluetoothNewBeans.getResultCode() == 1) {
                                SPUtils.put(BluetoothNewActivity.this, "istwo", "false");
                                Log.e("xcf", "上传名称成功");
                                tv_status.setText("连接中...");
                                bluetoothGatt = bluetoothDevice.connectGatt(BluetoothNewActivity.this, false, gattCallback);
                                if (mBluetoothNewBeans.getData().getExtra() != 0) {
                                    Toast.makeText(BluetoothNewActivity.this, "设备绑定成功积分+" + mBluetoothNewBeans.getData().getExtra(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
//                            App.ErrorToken(mBluetoothNewBeans.getResultCode(), mBluetoothNewBeans.getMsg());
                                Toast.makeText(BluetoothNewActivity.this, mBluetoothNewBeans.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return devicelist.size();
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_scan, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_device = (TextView) convertView.findViewById(R.id.tv_device);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
//            if (devicelist.get(position).getName() != null) {
            viewHolder.tv_device.setText(devicelist.get(position).getName() + "  " + devicelist.get(position).getAddress());
//            }
            return convertView;
        }

        class ViewHolder {
            TextView tv_device;
        }
    };

    private boolean enableNotification(boolean enable, BluetoothGattCharacteristic characteristic) {
        Log.e("xcf_bluetoothGatt", bluetoothGatt + "----" + characteristic);
        if (bluetoothGatt == null || characteristic == null)
            return false;
        bluetoothGatt.setCharacteristicNotification(characteristic, enable);

        Log.e("xcf_characteristic", characteristic.getDescriptors().toString());
        BluetoothGattDescriptor clientConfig = characteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
//        BluetoothGattDescriptor clientConfig = characteristic.getDescriptor(UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb"));
        if (clientConfig != null) {
            Log.e("xcf_clientConfig", "11111111111111111111111" + clientConfig.toString());
        } else {
            Log.e("xcf_clientConfig", "null");
        }


        if (clientConfig == null)
            return false;
        if (enable) {
            clientConfig.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        } else {
            clientConfig.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
        }
        return bluetoothGatt.writeDescriptor(clientConfig);
    }


    private final byte[] hex = "0123456789ABCDEF".getBytes();

    // 从字节数组到十六进制字符串转�?
    private String Bytes2HexString(byte[] b) {
        byte[] buff = new byte[2 * b.length];
        for (int i = 0; i < b.length; i++) {
            buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
            buff[2 * i + 1] = hex[b[i] & 0x0f];
        }
        return new String(buff);
    }

    /**
     * 字节转十六进制字符串，很个字节对应的十六进制以空格分隔
     *
     * @param b
     * @return
     */
    private String bytes2HexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase() + " ";
        }
        ret = ret.substring(0, ret.length() - 1);
        return ret;
    }

    private byte[] hexStringNoSpace2Bytes(String hexStr) {
        String regex = "(.{2})";
        hexStr = hexStr.replaceAll(regex, "$1 ");
        String[] strArr = hexStr.split(" ");
        byte[] bytes = new byte[strArr.length];
        byte b = 0;
        for (int i = 0; i < strArr.length; i++) {
            b = oneHexString2Byte(strArr[i]);
            bytes[i] = b;
        }
        return bytes;
    }

    private byte oneHexString2Byte(String src) {
        byte ret = 0;
        byte[] tmp = null;
        try {
            tmp = src.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 1; ++i) {
            ret = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    private byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
        byte ret = (byte) (_b0 | _b1);
        return ret;
    }

    public void onClicks_bank(View view) {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        bluetoothGatt.close();

//        unregisterReceiver(mBlueToothReceiver);
    }

    private class BlueToothReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.xcf.receiverout")) {
                String logoutFlag = intent.getStringExtra("receiverout");

                Log.e("TAG", "获取的值-->" + logoutFlag);

                if (logoutFlag.equals("1")) {
                    finish();
                }
            }
        }
    }
}
