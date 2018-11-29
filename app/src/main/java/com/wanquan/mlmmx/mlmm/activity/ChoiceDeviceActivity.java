package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.mlmmx.activity.AddDeviceActivity;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;

/**
 * 描述：选择设备
 * 作者：薛昌峰
 * 时间：2018.01.03
 */
public class ChoiceDeviceActivity extends BaseActivity {
    private LinearLayout mChoiceDeviceOne;
    private LinearLayout mChoiceDeviceTwo;
    private LinearLayout mChoiceDeviceThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(ChoiceDeviceActivity.this, R.color.tops);

        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_choice_device;
    }

    @Override
    public void initView() throws Exception {
        mChoiceDeviceOne = (LinearLayout) findViewById(R.id.ChoiceDevice_One);
        mChoiceDeviceTwo = (LinearLayout) findViewById(R.id.ChoiceDevice_Two);
        mChoiceDeviceThree = (LinearLayout) findViewById(R.id.ChoiceDevice_Three);
    }

    private void initData() {
    }

    private void initListeners() {
        mChoiceDeviceOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChoiceDeviceActivity.this, AddDeviceActivity.class).putExtra("flag", "1"));
            }
        });
        mChoiceDeviceTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.put(ChoiceDeviceActivity.this, "off", "0");
                //断开蓝牙连接
                if (BluetoothNewActivity.bluetoothGatt != null) {
                    BluetoothNewActivity.bluetoothGatt.disconnect();
                    //当你要手动断开时调用disconnect方法，然后在onConnectionStateChange的状态回调中调用close方法。
                    //如果在disconnect后直接调用close，会导致不走onConnectionStateChange这里的。
//                                BluetoothNewActivity.bluetoothGatt.close();
                }
                startActivity(new Intent(ChoiceDeviceActivity.this, AirSmallActivity.class).putExtra("flag", "101"));
            }
        });

        mChoiceDeviceThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(ChoiceDeviceActivity.this, QRCodeActivity.class));
                startActivity(new Intent(ChoiceDeviceActivity.this, AddDeviceActivity.class).putExtra("flag", "2"));
            }
        });
    }

    public void ChoiceDeviceActivity_Bank(View view) {
        finish();
    }
}
