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
import android.widget.Toast;

import com.google.gson.Gson;
import com.jinwei.tvgame2048.R;
import com.jinwei.tvgame2048.algorithm.Game2048Algorithm;
import com.jinwei.tvgame2048.model.Game2048StaticControl;
import com.jinwei.tvgame2048.model.NumbersItemOfQueue;
import com.jinwei.tvgame2048.model.NumbersQueue;

import org.json.JSONArray;

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
            String action = intent.getAction();
            if(action.equals("com.game2048.go.back")){
                NumbersItemOfQueue numbersItemOfQueue = mNumberQueue.pullItem();
                if(numbersItemOfQueue == null){
                    Log.d(TAG,"cannot back any more");
                    Toast.makeText(mContext,"Can not back any more!!!",Toast.LENGTH_SHORT).show();
                    return;
                }
                mGAM.resetCurrentNumbers(numbersItemOfQueue.mNumbers);
                doDrawGameSurface();
            }else if(action.equals("com.game2048.restart")){
                mGAM.restartGame();
                doDrawGameSurface();
                Game2048StaticControl.gameCurrentScores = 0;
                Game2048StaticControl.isShouldCheckGameWin = true;
                mHandler.sendEmptyMessage(Game2048StaticControl.UPDATE_CURRENT_HISTORY_SCORES);

            }else if(action.equals("com.game2048.exit")){
                mHandler.sendEmptyMessage(Game2048StaticControl.EXIT_CURRENT_GAME);
            }

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
        intentFilter.addAction("com.game2048.restart");
        intentFilter.addAction("com.game2048.exit");
        mContext.registerReceiver(mReceiver,intentFilter);
        mHandlerThread = new HandlerThread("Game2048Animation");
        mHandlerThread.start();
        mAniHander = new Handler(mHandlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case Game2048StaticControl.GENERATE_NUMBER:{
                        int position  = mGAM.setOneRandomNumberInRandomPosition();
                        Log.d(TAG,"position is : "+position);
                        if(position>-1){
                            generateRandomNumberAnimation(position);
                            doDrawGameSurface();
                            if(mGAM.getBlankCount()==0){
                                mGAM.checkGameOver();
                                break;
                            }
                        }
                        mGAM.checkGameWin();
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
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(mContext);
        String savedGame = preference.getString("mode"+Game2048StaticControl.gamePlayMode+Game2048StaticControl.isGoBackEnabled,null);
        if (savedGame !=null){
            Gson gson = new Gson();
            int game[][] = gson.fromJson(savedGame,int[][].class);
            for(int i=0;i<Game2048StaticControl.gamePlayMode;i++){
                for(int j=0;j<Game2048StaticControl.gamePlayMode;j++){
                    mGAM.getNumber(i,j).mScores = game[i][j];
                }
            }
            Game2048StaticControl.isShouldCheckGameWin =  preference.getBoolean("isShouldCheckGameWin"+Game2048StaticControl.gamePlayMode+Game2048StaticControl.isGoBackEnabled,false);
            Game2048StaticControl.gameCurrentScores = preference.getInt("currentScores"+Game2048StaticControl.gamePlayMode+Game2048StaticControl.isGoBackEnabled,0);
        }else {
            mGAM.initTowNumbers();
            Game2048StaticControl.gameCurrentScores = 0;
            Game2048StaticControl.isShouldCheckGameWin = true;
        }
        Game2048StaticControl.gameHistoryHighestScores = preference.getInt("bestScores"+Game2048StaticControl.gamePlayMode+Game2048StaticControl.isGoBackEnabled,0);
        mHandler.sendEmptyMessage(Game2048StaticControl.UPDATE_CURRENT_HISTORY_SCORES);
        doDrawGameSurface();
    }
    public void exit(){
        Log.d(TAG,"exit");
        mHandlerThread.quitSafely();
        mSoundPool.release();
        mContext.unregisterReceiver(mReceiver);
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor =  preference.edit();
        editor.putInt("bestScores"+Game2048StaticControl.gamePlayMode+Game2048StaticControl.isGoBackEnabled,Game2048StaticControl.gameHistoryHighestScores);
        editor.putInt("currentScores"+Game2048StaticControl.gamePlayMode+Game2048StaticControl.isGoBackEnabled,Game2048StaticControl.gameCurrentScores);
        editor.putBoolean("isShouldCheckGameWin"+Game2048StaticControl.gamePlayMode+Game2048StaticControl.isGoBackEnabled,Game2048StaticControl.isShouldCheckGameWin);
        editor.commit();
        int game[][] = new int[Game2048StaticControl.gamePlayMode][Game2048StaticControl.gamePlayMode];
        for(int i=0;i<Game2048StaticControl.gamePlayMode;i++){
            for(int j=0;j<Game2048StaticControl.gamePlayMode;j++){
                game[i][j]=mGAM.getNumber(i,j).mScores;
            }
        }
        Gson gson = new Gson();
        String savedGame = gson.toJson(game);
        editor.putString("mode"+Game2048StaticControl.gamePlayMode+Game2048StaticControl.isGoBackEnabled,savedGame);
        editor.commit();
    }
    public void registListener(GameSurfaceView surfaceView){
        mGAM.setListener(surfaceView);
    }
    public void doDrawGameSurface(){
        Canvas canvas = mHolder.lockCanvas();
        if(canvas == null){
            Log.d(TAG,"doDrawGameSurface lockCanvas error");
            return;
        }
        DrawTools.initSurfaceBg(canvas,mPaint);
        mDrawTools.drawSurfaceMap(canvas,mPaint);
        mDrawTools.drawSurfaceNumbers(canvas,mPaint);
        mHolder.unlockCanvasAndPost(canvas);
    }

    public void startAnimation(SurfaceHolder holder,Paint paint,int direct){
        int count = 0;
        RectF rectF = new RectF();
        while (count++<Game2048StaticControl.ANIMATION_MOVE_STEP) {
            Canvas canvas = holder.lockCanvas();
            mDrawTools.initSurfaceBg(canvas, paint);
            mDrawTools.drawSurfaceMap(canvas, paint);
            mDrawTools.drawSurfaceMapAndNumbersWhoIsNeedCombine(canvas,paint);
            for (int i = 0; i < Game2048StaticControl.gamePlayMode; i++) {
                for (int j = 0; j < Game2048StaticControl.gamePlayMode; j++) {
                    mGAM.aniInsertValue(i, j, count, Game2048StaticControl.ANIMATION_MOVE_STEP,direct,rectF);
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
        if(canvas == null){
            Log.d(TAG,"generateRandomNumberAnimation lockCanvas error");
            return;
        }
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
    public void gameOver(){
        Log.d(TAG,"gameOver");
        Game2048StaticControl.gameHasFail = true;
        doDrawGameSurface();
//        Canvas canvas = mHolder.lockCanvas();
//        if(canvas == null){
//            Log.d(TAG,"gameOver lockCanvas error");
//            return;
//        }
//        mDrawTools.drawGameOver(canvas,mPaint);
//        mHolder.unlockCanvasAndPost(canvas);
    }
    public void gameVictory(){
        Game2048StaticControl.gameHasWin = true;
        doDrawGameSurface();
//        Canvas canvas = mHolder.lockCanvas();
//        if(canvas == null){
//            Log.d(TAG,"gameVictory lockCanvas error");
//            return;
//        }
//        mDrawTools.drawGameVictory(canvas,mPaint);
//        mHolder.unlockCanvasAndPost(canvas);
    }
}
