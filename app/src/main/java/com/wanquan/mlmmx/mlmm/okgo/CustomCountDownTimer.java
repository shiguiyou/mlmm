package com.wanquan.mlmmx.mlmm.okgo;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 自定义倒计时
 */
public class CustomCountDownTimer extends CountDownTimer {
    private TextView textview;

    public CustomCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public CustomCountDownTimer(TextView textview, long millisInFuture, long countDownInterval){
        super(millisInFuture,countDownInterval);
        this.textview=textview;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        textview.setText("重新发送验证码"+millisUntilFinished/1000+"s");
        textview.setClickable(false);
    }

    @Override
    public void onFinish() {
        textview.setClickable(true);
        textview.setText("发送验证码");
        this.cancel();
    }
}
