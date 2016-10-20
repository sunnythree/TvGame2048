package com.jinwei.tvgame2048.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jinwei.tvgame2048.algorithm.Game2048Algorithm;
import com.jinwei.tvgame2048.model.Game2048StaticControl;

/**
 * Created by Jinwei on 2016/10/18.
 */
public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Game2048Algorithm.GameResultListener{
    private final String TAG = "GameSurfaceView";
    GameSurfaceViewHelper mGSVH ;
    Canvas mCanvas;
    SurfaceHolder mHolder;
    Paint mPaint;

    @Override
    public void onGameOver() {

    }

    @Override
    public void onGameVictory() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_UP:{
                mGSVH.upKeyUpdate(mHolder,mPaint);
                return true;
            }
            case KeyEvent.KEYCODE_DPAD_DOWN:{
                mGSVH.downKeyUpdate(mHolder,mPaint);
                return true;
            }
            case KeyEvent.KEYCODE_DPAD_LEFT:{
                mGSVH.leftKeyUpdate(mHolder,mPaint);
                return true;
            }
            case KeyEvent.KEYCODE_DPAD_RIGHT:{
                mGSVH.rightKeyUpdate(mHolder,mPaint);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG,"surfaceCreated");
        Game2048StaticControl.gameSurfaceLength = getWidth();//width==height
        Log.d(TAG,"Game2048StaticControl.gameSurfaceLength:"+Game2048StaticControl.gameSurfaceLength);
        //gameGapBetweenNumberViews:gameSurfaceViewPadding:gameNumberViewLength=1:2:6
        Game2048StaticControl.gameGapBetweenNumberViews = Game2048StaticControl.gameSurfaceLength/31;
        Game2048StaticControl.gameSurfaceViewPadding = Game2048StaticControl.gameSurfaceLength/31*2;
        Game2048StaticControl.gameNumberViewLength = Game2048StaticControl.gameSurfaceLength/31*6;
        Game2048StaticControl.initGameNumberViewPosition();
        mGSVH = new GameSurfaceViewHelper();
        mGSVH.init();
        doDraw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG,"surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG,"surfaceDestroyed");
    }
    private void doDraw(){
        mHolder = getHolder();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mCanvas = mHolder.lockCanvas();
        mGSVH.doDraw(mCanvas,mPaint);
        mHolder.unlockCanvasAndPost(mCanvas);
    }
    public void init(){
        getHolder().addCallback(this);
    }
    public void registListener(){
        mGSVH.registListener(this);
    }
}
