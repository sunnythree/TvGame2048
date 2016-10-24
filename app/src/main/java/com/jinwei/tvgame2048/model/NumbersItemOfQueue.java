package com.jinwei.tvgame2048.model;

/**
 * Created by Jinwei on 2016/10/24.
 */
public class NumbersItemOfQueue {
    public Number [][] mNumbers = new Number[Game2048StaticControl.gamePlayMode][Game2048StaticControl.gamePlayMode];
    public NumbersItemOfQueue(){
        for(int i=0;i<Game2048StaticControl.gamePlayMode;i++){
            for(int j=0;j<Game2048StaticControl.gamePlayMode;j++){
                mNumbers[i][j] = new Number(0,0);
            }
        }
    }
    public NumbersItemOfQueue(NumbersItemOfQueue numbersItemOfQueue){
        for(int i=0;i<Game2048StaticControl.gamePlayMode;i++){
            for(int j=0;j<Game2048StaticControl.gamePlayMode;j++){
                mNumbers[i][j] = new Number(0,0);
                mNumbers[i][j].mScores = numbersItemOfQueue.mNumbers[i][j].mScores;
                mNumbers[i][j].mCurPosition = numbersItemOfQueue.mNumbers[i][j].mCurPosition;
                mNumbers[i][j].mBeforePosition = numbersItemOfQueue.mNumbers[i][j].mBeforePosition;
                mNumbers[i][j].isNeedCombine = numbersItemOfQueue.mNumbers[i][j].isNeedCombine;
                mNumbers[i][j].isNeedMove = numbersItemOfQueue.mNumbers[i][j].isNeedMove;
            }
        }
    }
    public boolean hasData = false;
}
