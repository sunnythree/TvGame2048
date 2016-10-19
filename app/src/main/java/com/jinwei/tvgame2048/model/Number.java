package com.jinwei.tvgame2048.model;

import android.graphics.Color;

import com.jinwei.tvgame2048.algorithm.Game2048Algorithm;

/**
 * Created by Jinwei on 2016/10/19.
 */
public class Number {
    int mScores;
    int mPosition;
    float px;
    float py;
    public Number(int position,int scores){
        mScores = scores;
        mPosition = position;
        px = Game2048StaticControl.GameNumberViewPosition[mPosition/4][mPosition%4].left;
        py = Game2048StaticControl.GameNumberViewPosition[mPosition/4][mPosition%4].right;
    }
    public int getScroes(){
        return mScores;
    }
    public void setScores(int scores){
        mScores = scores;
    }
    public void reset(){
        mScores = 0;
        mPosition = 0;
        px = 0;
        py = 0;
    }
}
