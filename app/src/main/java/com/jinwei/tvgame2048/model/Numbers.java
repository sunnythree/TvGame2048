package com.jinwei.tvgame2048.model;

/**
 * Created by Jinwei on 2016/10/19.
 */
public class Numbers {
    Number [][] mNumbers = new Number[4][4];
    public Numbers(){
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                mNumbers[i][j] = new Number(0,0);
            }
        }
    }
    public Number getNumber(int x,int y){
        return mNumbers[x][y];
    }
    public int getBlankCount(){
        int count = 0;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(mNumbers[i][j].mScores==0){
                    count++;
                }
            }
        }
        return count;
    }
    public int getPositonFromBlankCountTh(int blankTh){
        int count = 0;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(mNumbers[i][j].mScores==0){
                    if(count==blankTh){
                        return i*4+j;
                    }else {
                        count++;
                    }
                }
            }
        }
        return -1;
    }
    public void swapNumber(int position1,int position2){
        mNumbers[position1/4][position1%4].mCurPosition = position2;
        mNumbers[position1/4][position1%4].mBeforePosition = position1;
        mNumbers[position2/4][position2%4].mCurPosition = position1;
        mNumbers[position2/4][position2%4].mBeforePosition = position2;
        Number tem = mNumbers[position1/4][position1%4];
        mNumbers[position1/4][position1%4] = mNumbers[position2/4][position2%4];
        mNumbers[position2/4][position2%4] = tem;
    }
}
