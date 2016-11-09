package com.jinwei.tvgame2048.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.jinwei.tvgame2048.MainActivity;
import com.jinwei.tvgame2048.model.Game2048StaticControl;

/**
 * Created by Jinwei on 2016/10/19.
 */
public class MainPresenter {
    Context mContext;
    SurfaceViewPresenter mSurfaceViewPresenter;
    public MainPresenter(Context context){
        mContext = context;
        mSurfaceViewPresenter = new SurfaceViewPresenter(context);
    }
    public void initGame(SurfaceView surfaceView,Handler handler){
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Point point=new Point();
        wm.getDefaultDisplay().getSize(point);
        Game2048StaticControl.gameUIWidth = point.x;
        Game2048StaticControl.gameUIHeight = point.y;
        Game2048StaticControl.gameCurrentScores = 0;
        Game2048StaticControl.gameHasWin = false;
        Game2048StaticControl.gameHasFail = false;
        Game2048StaticControl.isShouldCheckGameWin = true;
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(mContext);
        Game2048StaticControl.isGameSoundOn = preference.getBoolean("isSoundOn",true);
        Game2048StaticControl.gameHistoryHighestScores = preference.getInt("bestScores",0);
        mSurfaceViewPresenter.initSurfaceView(surfaceView,handler);
    }
    public void forcusGame(){
        mSurfaceViewPresenter.forcusSurfaceView();
    }

}
