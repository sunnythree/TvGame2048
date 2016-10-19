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

import com.jinwei.tvgame2048.model.Game2048StaticControl;

/**
 * Created by Jinwei on 2016/10/18.
 */
public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    private final String TAG = "GameSurfaceView";
    MainThread mainThread;
    Canvas mCanvas;
    int lendthPixel = 100;
    int left = 0;
    int top = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG,"onKeyDown");
        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_UP:{
                top+=lendthPixel;
                if(top>this.getHeight())
                    top=this.getHeight();
                return true;
            }
            case KeyEvent.KEYCODE_DPAD_DOWN:{
                top-=lendthPixel;
                if(top<10)
                    top=10;
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
        Log.d(TAG,"GameSurfaceView");
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
        startDrawThread();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG,"surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG,"surfaceDestroyed");
        mainThread.isRunning = false;
        try {
            mainThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void startDrawThread(){
        if(mainThread==null)
            mainThread = new MainThread(getHolder());
        mainThread.isRunning = true;
        mainThread.start();
    }
    public void init(){
        getHolder().addCallback(this);
    }
    class MainThread extends Thread{
        SurfaceHolder mHolder;
        boolean isRunning;
        Paint mPaint;

        public MainThread(SurfaceHolder holder) {
            super();
            mHolder = holder;
            mPaint = new Paint();
            mPaint.setColor(Color.YELLOW);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setAntiAlias(true);
        }

        @Override
        public void run() {
            super.run();
            while(isRunning){
                try {
                    synchronized (mHolder) {
                        doDraw();
                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.d(TAG,"draw end");
        }
        private void doDraw(){
            Log.d(TAG,"do Draw");
            mCanvas = mHolder.lockCanvas();
            initSurfaceBg();
            initNumberViews();
            mHolder.unlockCanvasAndPost(mCanvas);
        }
        private void initSurfaceBg(){
            if (mCanvas==null){
                Log.d(TAG,"mCanvas==null");
            }
            mCanvas.drawColor(Color.WHITE);
            mPaint.setColor(Color.YELLOW);
            mCanvas.drawRoundRect(0,0,GameSurfaceView.this.getWidth(),GameSurfaceView.this.getHeight(),20,20,mPaint);
        }
        private void initNumberViews(){
            mPaint.setColor(Color.WHITE);
            for(int i=0;i<4;i++){
                for(int j=0;j<4;j++){
                    Log.d(TAG,"x="+Game2048StaticControl.GameNumberViewPosition[i][j].left+"y="+
                            Game2048StaticControl.GameNumberViewPosition[i][j].right);
                    mCanvas.drawRoundRect(Game2048StaticControl.GameNumberViewPosition[i][j],
                            Game2048StaticControl.gameRadiumOfNumberViews,
                            Game2048StaticControl.gameRadiumOfNumberViews,mPaint);
                }
            }
        }
    }
}
