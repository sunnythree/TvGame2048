package com.jinwei.tvgame2048.ui.start;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jinwei.tvgame2048.R;
import com.jinwei.tvgame2048.ui.GameModeChoiceActivity;
import com.jinwei.tvgame2048.ui.GameSettingsActivity;

/**
 * Created by Jinwei on 2016/11/8.
 */
public class GameStartSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private static int playCount = 0;
    private final String TAG = "MySurfaceView";
    MediaPlayer mediaPlayer;
    Context mContext = null;
    public GameStartSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mediaPlayer = MediaPlayer.create(context, R.raw.game_loading);
        Log.d(TAG,"MySurfaceView");
    }
    public void init(){
        getHolder().addCallback(this);
        playCount = 0;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        play();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mediaPlayer.release();
    }
    private void play() {
        Log.d(TAG,"play");
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //设置需要播放的视频
            //把视频画面输出到SurfaceView
            mediaPlayer.setDisplay(getHolder());
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    playCount++;
                    if(playCount==3){
                        playCount=0;
                        Intent intent = new Intent(mContext,GameModeChoiceActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mContext.startActivity(intent);
                        ((Activity)mContext).overridePendingTransition(R.anim.anim_left_in,R.anim.anim_right_out);
                    }else {
                        mediaPlayer.start();
                    }
                }
            });
            //播放
            mediaPlayer.start();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
