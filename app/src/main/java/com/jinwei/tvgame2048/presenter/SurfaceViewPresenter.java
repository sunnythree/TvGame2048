package com.jinwei.tvgame2048.presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceView;

import com.jinwei.tvgame2048.MainActivity;
import com.jinwei.tvgame2048.model.Game2048StaticControl;
import com.jinwei.tvgame2048.ui.GameSurfaceView;

/**
 * Created by Jinwei on 2016/10/19.
 */
public class SurfaceViewPresenter {
    private final String TAG = "SurfaceViewPresenter";
    GameSurfaceView mSurfaceView;
    public SurfaceViewPresenter(Context context){

    }
    public void initSurfaceView(SurfaceView surfaceView, Handler handler){
        mSurfaceView = (GameSurfaceView) surfaceView;
        mSurfaceView.requestFocus();
        mSurfaceView.init(handler);
    }
    public void forcusSurfaceView(){
        mSurfaceView.requestFocus();
    }
}
