package com.wanquan.mlmmx.mlmm.voice;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 展示语音feed,支持点击播放
 * v8.7.5 因发布器也使用SoundItemView，但不需要，也不能依赖PPVideoPlayerManager，所以实现一个父类CommonSoundItemView
 * 依赖PPVideoPlayerManager的，再由SoundItemView实现
 * @author Kevin
 * @version V1.0
 * @Date 6/28/16
 * @Description
 */
public class CommonSoundItemView extends RelativeLayout implements AudioPlaybackManager.OnPlayingListener {

    protected static final String TAG = "SoundItemView";
    //默认的阈值
    protected static final long DEFAULT_THRESHOLD = 8;

    protected ImageView ivSoundItemHorn;
    protected AnimationDrawable animationDrawable;
    protected RelativeLayout layoutSoundItem;
    protected TextView tvSoundDuration;

    protected AudioEntity audioInfo;
    protected Context context;
    private int maxLength;

    public CommonSoundItemView(Context context) {
        super(context);
        initView(context);
    }

    public CommonSoundItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CommonSoundItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.sound_item, this);
        rootView.findViewById(R.id.layout_sound_item).setBackgroundDrawable(null);

        ivSoundItemHorn = (ImageView) findViewById(R.id.iv_sound_horn);
        animationDrawable = (AnimationDrawable) ivSoundItemHorn.getDrawable();
        animationDrawable.setOneShot(false);
        layoutSoundItem = (RelativeLayout) findViewById(R.id.layout_sound_item);
        tvSoundDuration = (TextView) findViewById(R.id.tv_sound_duration);
        maxLength = context.getResources().getDimensionPixelSize(R.dimen.pp_sound_item_length);
        layoutSoundItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound();
            }
        });
    }

    public void setSoundData(AudioEntity audioInfo) {
        if (audioInfo == null) {
            Log.e("voide-s81", "audioInfo is null.");
            return;
        }
        this.audioInfo = audioInfo;
        this.tvSoundDuration.setText(StringUtil.formatTime((int) this.audioInfo.getDuration()));

        MessageEvent messageEvent = new MessageEvent();
        messageEvent.setIsVoiceTime(StringUtil.formatTime((int) this.audioInfo.getDuration()));
        EventBus.getDefault().post(messageEvent);

        if (this.audioInfo.getDuration() < DEFAULT_THRESHOLD) {
            //根据语音的时长不同动态设置view的宽度
            //LayoutParams layoutParams = (LayoutParams) layoutSoundItem.getLayoutParams();
            //layoutParams.width = (int) (maxLength * (this.audioInfo.getDuration() * 1f /
            // DEFAULT_THRESHOLD));
            //layoutParams.width = context.getResources().getDimensionPixelSize(R.dimen
            // .pp_sound_item_min_length);
            //layoutSoundItem.setLayoutParams(layoutParams);
        }
        onComplete();
    }

    public void setHornDrawable(AnimationDrawable animationDrawable) {
        ivSoundItemHorn.setImageDrawable(animationDrawable);
        this.animationDrawable = animationDrawable;
    }

    public void setItemBackground(Drawable drawable) {
        layoutSoundItem.setBackgroundDrawable(drawable);
    }

    public String getSoundUrl() {
        return audioInfo.getUrl();
    }

    public AudioEntity getAudioInfo() {
        return audioInfo;
    }

    public long getSoundDuration() {
        return audioInfo.getDuration();
    }

    public boolean hasData() {
        return audioInfo != null && !TextUtils.isEmpty(audioInfo.getUrl());
    }

    public void clearData() {
        this.audioInfo = null;
        AudioPlaybackManager.getInstance().stopAudio();
    }

    protected void playSound() {
        if (audioInfo != null && !TextUtils.isEmpty(audioInfo.getUrl())) {
            Log.e("voide-s82", "start play sound , url:" + audioInfo.getUrl());
            AudioPlaybackManager.getInstance().playAudio(audioInfo.getUrl(), this);
        } else {
            Log.e("voide-s83", "playSound sound url is null");
        }
    }

    @Override
    public void onStart() {
        resetDrawable();
        animationDrawable.start();
        invalidate();
    }

    @Override
    public void onStop() {
        animationDrawable.stop();
        resetDrawable();
        invalidate();
    }

    protected void resetDrawable() {
        ivSoundItemHorn.clearAnimation();
        animationDrawable = (AnimationDrawable) context.getResources().getDrawable(R.drawable.ar_sound_play_animation);
        ivSoundItemHorn.setImageDrawable(animationDrawable);
        animationDrawable.stop();
        animationDrawable.setOneShot(false);
    }

    @Override
    public void onComplete() {
        animationDrawable.stop();
        resetDrawable();
        invalidate();
    }
}
