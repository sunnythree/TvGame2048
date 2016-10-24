package com.jinwei.tvgame2048.model;

import android.graphics.Color;
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
    //game mode
    public static int gamePlayMode = 4;
    public static boolean isGoBackEnabled = false;
    //sound
    public static boolean isGameSoundOn = true;
    //number view position
    public static RectF[][] GameNumberViewPosition  = null;
    public static RectF[][] initGameNumberViewPosition(){
        RectF [][]rectF = new RectF[Game2048StaticControl.gamePlayMode][Game2048StaticControl.gamePlayMode];
        int p = gameSurfaceViewPadding;
        int g = gameGapBetweenNumberViews;
        int l = gameNumberViewLength;
        for(int i=0;i<Game2048StaticControl.gamePlayMode;i++){
            for(int j=0;j<Game2048StaticControl.gamePlayMode;j++){
                rectF[i][j] = new RectF(p+j*g+j*l,p+i*g+i*l,p+j*g+j*l+l,p+i*g+i*l+l);
            }
        }
        return rectF;
    }
    public static int []gameNumberColors={
            Color.rgb(238, 228, 218),        // 2
            Color.rgb(237, 224, 200),		// 4
            Color.rgb(242, 177, 121),		// 8
            Color.rgb(245, 149, 99),			//16
            Color.rgb(246, 124, 95),			//32
            Color.rgb(246, 94, 59),			//64
            Color.rgb(237, 207, 114),		//128
            Color.rgb(237, 204, 97),			//256
            Color.rgb(246, 206, 70),			//512
            Color.rgb(237, 197, 63),			//1024
            Color.rgb(234, 185, 19)			//2048
    };
    public static int gameSurfacceViewGbColor = Color.rgb(187,173,160);

    //animation
    public static final int DIRECT_UP = 0;
    public static final int DIRECT_DOWN = 1;
    public static final int DIRECT_LEFT = 2;
    public static final int DIRECT_RIGHT = 3;
    public static final int GENERATE_NUMBER = 4;
    //animation step
    public static final int ANIMATION_MOVE_STEP = 10;
    public static final int ANIMATION_GENERATE_STEP = 10;

    //game scores and history highest scores
    public static int gameCurrentScores = 0;
    public static int gameHistoryHighestScores = 0;

    //update scores and history highest scores
    public static final int UPDATE_CURRENT_HISTORY_SCORES = 0;

}
