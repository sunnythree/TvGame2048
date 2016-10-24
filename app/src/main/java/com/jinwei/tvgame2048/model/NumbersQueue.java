package com.jinwei.tvgame2048.model;

import android.util.Log;

/**
 * Created by Jinwei on 2016/10/24.
 */
public class NumbersQueue {
    NumbersItemOfQueue [] mNumberQueue;
    private final String TAG = "NumbersQueue";
    int mSize;
    public NumbersQueue(int size){
        mSize = size;
        mNumberQueue = new NumbersItemOfQueue[mSize];
        for(int i=0;i<size;i++){
            mNumberQueue[i] = new NumbersItemOfQueue();
        }
    }
    public void  pushItem(Number [][] numbers){
        mNumberQueue[0].hasData = false;
        for(int i=1;i<mSize;i++){
            swapTowNumbersItemOfQueue(i,i-1);
        }
        initNumbersItemOfQueue(mNumberQueue[mSize-1],numbers);
    }
    public NumbersItemOfQueue pullItem(){
        if (!mNumberQueue[mSize-1].hasData){
            return null;
        }
        NumbersItemOfQueue tem = new NumbersItemOfQueue(mNumberQueue[mSize-1]);
        mNumberQueue[mSize-1].hasData = false;
        for(int i=mSize-1;i>0;i--){
            swapTowNumbersItemOfQueue(i,i-1);
        }
        return tem;
    }
    private void swapTowNumbersItemOfQueue(int i,int j){
        NumbersItemOfQueue tem = mNumberQueue[i];
        mNumberQueue[i] = mNumberQueue[j];
        mNumberQueue[j] = tem;
    }
    private void initNumbersItemOfQueue(NumbersItemOfQueue numbersItemOfQueue,Number [][] numbers){
        numbersItemOfQueue.hasData = true;
        for(int i=0;i<Game2048StaticControl.gamePlayMode;i++){
            for(int j=0;j<Game2048StaticControl.gamePlayMode;j++){
                numbersItemOfQueue.mNumbers[i][j].mScores =  numbers[i][j].mScores;
                numbersItemOfQueue.mNumbers[i][j].mCurPosition =  numbers[i][j].mCurPosition;
                numbersItemOfQueue.mNumbers[i][j].mBeforePosition =  numbers[i][j].mBeforePosition;
                numbersItemOfQueue.mNumbers[i][j].isNeedCombine =  numbers[i][j].isNeedCombine;
                numbersItemOfQueue.mNumbers[i][j].isNeedMove =  numbers[i][j].isNeedMove;
            }
        }
    }
}
