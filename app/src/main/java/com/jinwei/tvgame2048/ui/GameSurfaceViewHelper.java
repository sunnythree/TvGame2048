package com.jinwei.tvgame2048.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.SoundPool;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.SurfaceHolder;

import com.jinwei.tvgame2048.R;
import com.jinwei.tvgame2048.algorithm.Game2048Algorithm;
import com.jinwei.tvgame2048.model.Game2048StaticControl;
import com.jinwei.tvgame2048.model.Number;
import com.jinwei.tvgame2048.model.NumbersItemOfQueue;
import com.jinwei.tvgame2048.model.NumbersQueue;

import java.util.HashMap;

/**
 * Created by Jinwei on 2016/10/19.
 */
public class GameSurfaceViewHelper {
    private final String TAG = "GameSurfaceViewHelper";
    Game2048Algorithm mGAM;
    HandlerThread mHandlerThread;
    Handler mAniHander;
    SurfaceHolder mHolder;
    DrawTools mDrawTools;
    Paint mPaint;
    Handler mHandler;
    SoundPool mSoundPool;
    Context mContext;
    NumbersQueue mNumberQueue;
    HashMap<Integer,Integer> mSoundMap = new HashMap<>();
    private class ReceiveBroadCast extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            NumbersItemOfQueue numbersItemOfQueue = mNumberQueue.pullItem();
            if(numbersItemOfQueue == null){
                Log.d(TAG,"cannot back any more");
                return;
            }
            mGAM.resetCurrentNumbers(numbersItemOfQueue.mNumbers);
            doDrawGameSurface();
        }
    }
    ReceiveBroadCast mReceiver= new ReceiveBroadCast();
    public GameSurfaceViewHelper(Context context,SurfaceHolder holder, Handler handler){
        mHolder = holder;
        mContext = context;
        mHandler = handler;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mGAM = new Game2048Algorithm(mHandler);
        mDrawTools = new DrawTools(mGAM);
        mSoundPool = new SoundPool.Builder().build();
        mNumberQueue = new NumbersQueue(6);
        mSoundMap.put(1,mSoundPool.load(context, R.raw.move,1));
        mSoundMap.put(2,mSoundPool.load(context, R.raw.merge,1));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.game2048.go.back");
        mContext.registerReceiver(mReceiver,intentFilter);
        mHandlerThread = new HandlerThread("Game2048Animation");
        mHandlerThread.start();
        mAniHander = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case Game2048StaticControl.GENERATE_NUMBER:{
                        int position  = mGAM.setOneRandomNumberInRandomPosition();
                        generateRandomNumberAnimation(position);
                        doDrawGameSurface();
                        break;
                    }
                    case Game2048StaticControl.DIRECT_UP:{
                        mNumberQueue.pushItem(mGAM.getmNumbers());
                        int ret = mGAM.upKeyDealAlgorithm();
                        if(ret>0){
                            startAnimation(mHolder,mPaint,Game2048StaticControl.DIRECT_UP);
                            mGAM.updateNumbers();
                            doDrawGameSurface();
                            sendEmptyMessage(Game2048StaticControl.GENERATE_NUMBER);
                            playSoundEffect(ret);
                        }
                        break;
                    }
                    case Game2048StaticControl.DIRECT_DOWN:{
                        mNumberQueue.pushItem(mGAM.getmNumbers());
                        int ret = mGAM.downKeyDealAlgorithm();
                        if (ret>0){
                            startAnimation(mHolder,mPaint,Game2048StaticControl.DIRECT_DOWN);
                            mGAM.updateNumbers();
                            doDrawGameSurface();
                            sendEmptyMessage(Game2048StaticControl.GENERATE_NUMBER);
                            playSoundEffect(ret);
                        }
                        break;
                    }
                    case Game2048StaticControl.DIRECT_LEFT:{
                        mNumberQueue.pushItem(mGAM.getmNumbers());
                        int ret =  mGAM.leftKeyDealAlgorithm();
                        if (ret>0){
                            startAnimation(mHolder,mPaint,Game2048StaticControl.DIRECT_LEFT);
                            mGAM.updateNumbers();
                            doDrawGameSurface();
                            sendEmptyMessage(Game2048StaticControl.GENERATE_NUMBER);
                            playSoundEffect(ret);
                        }
                        break;
                    }
                    case Game2048StaticControl.DIRECT_RIGHT:{
                        mNumberQueue.pushItem(mGAM.getmNumbers());
                        int ret = mGAM.rightKeyDealAlgorithm();
                        if (ret>0){
                            startAnimation(mHolder,mPaint,Game2048StaticControl.DIRECT_RIGHT);
                            mGAM.updateNumbers();
                            doDrawGameSurface();
                            sendEmptyMessage(Game2048StaticControl.GENERATE_NUMBER);
                            playSoundEffect(ret);
                        }
                        break;
                    }
                }
                super.handleMessage(msg);
            }
        };
    }
    public void init(){
        mGAM.initTowNumbers();
        doDrawGameSurface();
    }
    public void exit(){
        mHandlerThread.quitSafely();
        mSoundPool.release();
    }
    public void registListener(GameSurfaceView surfaceView){
        mGAM.setListener(surfaceView);
    }
    public void doDrawGameSurface(){
        Canvas canvas = mHolder.lockCanvas();
        DrawTools.initSurfaceBg(canvas,mPaint);
        mDrawTools.drawSurfaceMap(canvas,mPaint);
        mDrawTools.drawSurfaceNumbers(canvas,mPaint);
        mHolder.unlockCanvasAndPost(canvas);
    }

    public void startAnimation(SurfaceHolder holder,Paint paint,int direct){
        int count = 0;
        while (count++<Game2048StaticControl.ANIMATION_MOVE_STEP) {
            Canvas canvas = holder.lockCanvas();
            mDrawTools.initSurfaceBg(canvas, paint);
            mDrawTools.drawSurfaceMap(canvas, paint);
            mDrawTools.drawSurfaceMapAndNumbersWhoIsNeedCombine(canvas,paint);
            for (int i = 0; i < Game2048StaticControl.gamePlayMode; i++) {
                for (int j = 0; j < Game2048StaticControl.gamePlayMode; j++) {
                    RectF rectF = mGAM.aniInsertValue(i, j, count, Game2048StaticControl.ANIMATION_MOVE_STEP,direct);
                    if(rectF != null && mGAM.isPosionHasNumber(i,j) && mGAM.getNumber(i,j).isNeedMove){
                        mDrawTools.drawNumberByRectF(i,j,canvas,paint,rectF);
                    }
                }
            }
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void generateRandomNumberAnimation(int position){
        Canvas canvas = mHolder.lockCanvas();
        RectF numberRectF = Game2048StaticControl.GameNumberViewPosition[position/Game2048StaticControl.gamePlayMode]
                [position%Game2048StaticControl.gamePlayMode];
        RectF rectF = new RectF();
        float difPixel = (Game2048StaticControl.gameNumberViewLength-10)/2;
        float step = difPixel/Game2048StaticControl.ANIMATION_GENERATE_STEP;
        int count = 0;
        while (count++<Game2048StaticControl.ANIMATION_GENERATE_STEP) {
            DrawTools.initSurfaceBg(canvas,mPaint);
            mDrawTools.drawSurfaceMap(canvas,mPaint);
            mDrawTools.drawSurfaceNumbers(canvas,mPaint);
            rectF.set(numberRectF.left+difPixel-count*step,numberRectF.top+difPixel-count*step,
                    numberRectF.right-difPixel+count*step, numberRectF.bottom-difPixel+count*step);
            mDrawTools.drawNumberByRectF(position/Game2048StaticControl.gamePlayMode,
                    position%Game2048StaticControl.gamePlayMode,canvas,mPaint,rectF);
        }
        mHolder.unlockCanvasAndPost(canvas);
    }

    public void upKeyUpdate(){
        //Log.d(TAG,"up key");
        mAniHander.sendEmptyMessage(Game2048StaticControl.DIRECT_UP);
    }
    public void downKeyUpdate(){
        //Log.d(TAG,"down key");
        mAniHander.sendEmptyMessage(Game2048StaticControl.DIRECT_DOWN);
    }
    public void leftKeyUpdate(){
        //Log.d(TAG,"left key");
        mAniHander.sendEmptyMessage(Game2048StaticControl.DIRECT_LEFT);
    }
    public void rightKeyUpdate(){
        //Log.d(TAG,"right key");
        mAniHander.sendEmptyMessage(Game2048StaticControl.DIRECT_RIGHT);
    }
    private void playSoundEffect(int ret){
        if(Game2048StaticControl.isGameSoundOn){
            mSoundPool.play(mSoundMap.get(ret),1,1,0,0,1);
        }
    }
}
