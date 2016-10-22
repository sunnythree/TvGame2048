package com.jinwei.tvgame2048.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jinwei.tvgame2048.algorithm.Game2048Algorithm;
import com.jinwei.tvgame2048.model.Game2048StaticControl;
import com.jinwei.tvgame2048.model.Number;
import com.jinwei.tvgame2048.model.Numbers;

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
    public GameSurfaceViewHelper(SurfaceHolder holder){
        mHolder = holder;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mGAM = new Game2048Algorithm();
        mDrawTools = new DrawTools(mGAM);
        mHandlerThread = new HandlerThread("Game2049Animation");
        mHandlerThread.start();
        mAniHander = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case Game2048StaticControl.GENERATE_NUMBER:{
                        int position  = mGAM.setOneRandomNumberInRandomPosition();
                        //generateRandomNumberAnimation(position);
                        doDrawGameSurface();
                        break;
                    }
                    case Game2048StaticControl.DIRECT_UP:{
                        if (mGAM.upKeyDealAlgorithm()){
                            startAnimation(mHolder,mPaint,Game2048StaticControl.DIRECT_UP);
                            mGAM.updateNumbers();
                            doDrawGameSurface();
                            sendEmptyMessage(Game2048StaticControl.GENERATE_NUMBER);
                        }
                        break;
                    }
                    case Game2048StaticControl.DIRECT_DOWN:{
                        if (mGAM.downKeyDealAlgorithm()){
                            startAnimation(mHolder,mPaint,Game2048StaticControl.DIRECT_DOWN);
                            mGAM.updateNumbers();
                            doDrawGameSurface();
                            sendEmptyMessage(Game2048StaticControl.GENERATE_NUMBER);
                        }
                        break;
                    }
                    case Game2048StaticControl.DIRECT_LEFT:{
                        if (mGAM.leftKeyDealAlgorithm()){
                            startAnimation(mHolder,mPaint,Game2048StaticControl.DIRECT_LEFT);
                            mGAM.updateNumbers();
                            doDrawGameSurface();
                            sendEmptyMessage(Game2048StaticControl.GENERATE_NUMBER);
                        }
                        break;
                    }
                    case Game2048StaticControl.DIRECT_RIGHT:{
                        if (mGAM.rightKeyDealAlgorithm()){
                            startAnimation(mHolder,mPaint,Game2048StaticControl.DIRECT_RIGHT);
                            mGAM.updateNumbers();
                            doDrawGameSurface();
                            sendEmptyMessage(Game2048StaticControl.GENERATE_NUMBER);
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
        while (count++<10) {
            Canvas canvas = holder.lockCanvas();
            mDrawTools.initSurfaceBg(canvas, paint);
            mDrawTools.drawSurfaceMap(canvas, paint);
            mDrawTools.drawSurfaceMapAndNumbersWhoIsNeedCombine(canvas,paint);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    RectF rectF = mGAM.aniInsertValue(i, j, count, 10,direct);
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
        int count = 0;
        RectF numberRectF = Game2048StaticControl.GameNumberViewPosition[position/4][position%4];
        RectF rectF = new RectF();
        float difPixel = (numberRectF.right - numberRectF.left-100)/2;
        float step = difPixel/Game2048StaticControl.ANIMATION_STEP;
        while (count++<Game2048StaticControl.ANIMATION_STEP) {
            DrawTools.initSurfaceBg(canvas,mPaint);
            mDrawTools.drawSurfaceMap(canvas,mPaint);
            mDrawTools.drawSurfaceNumbers(canvas,mPaint);
            rectF.set(numberRectF.left+difPixel-count*step,numberRectF.right-difPixel+count*step,
                    numberRectF.top+difPixel-count*step,numberRectF.bottom-difPixel+count*step);
            mDrawTools.drawNumberByRectF(position/4,position%4,canvas,mPaint,rectF);
        }
        mHolder.unlockCanvasAndPost(canvas);
    }

    public void upKeyUpdate(){
        Log.d(TAG,"up key");
        mAniHander.sendEmptyMessage(Game2048StaticControl.DIRECT_UP);
    }
    public void downKeyUpdate(){
        Log.d(TAG,"down key");
        mAniHander.sendEmptyMessage(Game2048StaticControl.DIRECT_DOWN);
    }
    public void leftKeyUpdate(){
        Log.d(TAG,"left key");
        mAniHander.sendEmptyMessage(Game2048StaticControl.DIRECT_LEFT);
    }
    public void rightKeyUpdate(){
        Log.d(TAG,"right key");
        mAniHander.sendEmptyMessage(Game2048StaticControl.DIRECT_RIGHT);
    }
}
