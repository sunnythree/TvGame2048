package com.jinwei.tvgame2048.model;

/**
 * Created by Jinwei on 2016/10/19.
 */
public class Numbers {
    Number [][] mNumbers = new Number[Game2048StaticControl.gamePlayMode][Game2048StaticControl.gamePlayMode];
    public Numbers(){
        for(int i=0;i<Game2048StaticControl.gamePlayMode;i++){
            for(int j=0;j<Game2048StaticControl.gamePlayMode;j++){
                mNumbers[i][j] = new Number(0,0);
            }
        }
    }
    public Number getNumber(int x,int y){
        return mNumbers[x][y];
    }
    public Number [][] getNumbers (){
        return mNumbers;
    }
    public int getBlankCount(){
        int count = 0;
        for(int i=0;i<Game2048StaticControl.gamePlayMode;i++){
            for(int j=0;j<Game2048StaticControl.gamePlayMode;j++){
                if(mNumbers[i][j].mScores==0){
                    count++;
                }
            }
        }
        return count;
    }
    public int getPositonFromBlankCountTh(int blankTh){
        int count = 0;
        for(int i=0;i<Game2048StaticControl.gamePlayMode;i++){
            for(int j=0;j<Game2048StaticControl.gamePlayMode;j++){
                if(mNumbers[i][j].mScores==0){
                    if(count==blankTh){
                        return i*Game2048StaticControl.gamePlayMode+j;
                    }else {
                        count++;
                    }
                }
            }
        }
        return -1;
    }
    public void swapNumber(int position1,int position2){
        mNumbers[position1/Game2048StaticControl.gamePlayMode][position1%Game2048StaticControl.gamePlayMode].mCurPosition = position2;
        mNumbers[position1/Game2048StaticControl.gamePlayMode][position1%Game2048StaticControl.gamePlayMode].mBeforePosition = position1;
        mNumbers[position2/Game2048StaticControl.gamePlayMode][position2%Game2048StaticControl.gamePlayMode].mCurPosition = position1;
        mNumbers[position2/Game2048StaticControl.gamePlayMode][position2%Game2048StaticControl.gamePlayMode].mBeforePosition = position2;
        Number tem = mNumbers[position1/Game2048StaticControl.gamePlayMode][position1%Game2048StaticControl.gamePlayMode];
        mNumbers[position1/Game2048StaticControl.gamePlayMode][position1%Game2048StaticControl.gamePlayMode] = mNumbers[position2/Game2048StaticControl.gamePlayMode][position2%Game2048StaticControl.gamePlayMode];
        mNumbers[position2/Game2048StaticControl.gamePlayMode][position2%Game2048StaticControl.gamePlayMode] = tem;
    }
}
