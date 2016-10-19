package com.jinwei.tvgame2048.model;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

/**
 * Created by Jinwei on 2016/10/19.
 */
public class Game2048StaticControl {
    public static String TAG = "Game2048StaticControl";
    //game
    public static int gameUIHeight = 0;
    public static int gameUIWidth = 0;
    //surface
    public static int gameSurfaceLength;
    //Padding of surfaceView
    public static int gameSurfaceViewPadding = 0;
    //number view(2,4,8...)
    public static int gameNumberViewLength = 0;
    //gap between number views
    public static int gameGapBetweenNumberViews = 0;
    //raduim of NumberView corner
    public static int gameRadiumOfNumberViews = 5;
    //number view position
    public static RectF[][] GameNumberViewPosition = new RectF[4][4];
    public static void initGameNumberViewPosition(){
        int p = gameSurfaceViewPadding;
        int g = gameGapBetweenNumberViews;
        int l = gameNumberViewLength;

        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                Log.d(TAG,"i="+i+";j="+j);
                GameNumberViewPosition[i][j] = new RectF(p+j*g+j*l,p+i*g+i*l,p+j*g+j*l+l,p+i*g+i*l+l);
            }
        }
    }

}
