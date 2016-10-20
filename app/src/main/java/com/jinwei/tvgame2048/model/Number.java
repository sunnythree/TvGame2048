package com.jinwei.tvgame2048.model;

import android.graphics.Color;

import com.jinwei.tvgame2048.algorithm.Game2048Algorithm;

/**
 * Created by Jinwei on 2016/10/19.
 */
public class Number {
    public int mScores;
    public int mCurPosition;
    public int mBeforePosition;
    public boolean isNeedMove;
    public boolean isNeedCombine;
    public Number(int position,int scores){
        mScores = scores;
        mCurPosition = mBeforePosition = position;
        isNeedMove = false;
        isNeedCombine = false;
    }
    public void reset(){
        mScores = 0;
        isNeedMove = false;
        isNeedCombine = false;
    }
}
