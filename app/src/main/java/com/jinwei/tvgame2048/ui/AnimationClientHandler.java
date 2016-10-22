package com.jinwei.tvgame2048.ui;

import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.SurfaceHolder;

import com.jinwei.tvgame2048.model.Game2048StaticControl;

/**
 * Created by Jinwei on 2016/10/22.
 */
public class AnimationClientHandler extends Handler {
    SurfaceHolder mHolder;
    Paint mPaint;
    public AnimationClientHandler(Looper looper, SurfaceHolder holder, Paint paint) {
        super(looper);
        mHolder = holder;
        mPaint = paint;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case Game2048StaticControl.GENERATE_NUMBER:{
                break;
            }
            case Game2048StaticControl.DIRECT_UP:{
                break;
            }
            case Game2048StaticControl.DIRECT_DOWN:{
                break;
            }
            case Game2048StaticControl.DIRECT_LEFT:{
                break;
            }
            case Game2048StaticControl.DIRECT_RIGHT:{
                break;
            }
        }
        super.handleMessage(msg);
    }

}
