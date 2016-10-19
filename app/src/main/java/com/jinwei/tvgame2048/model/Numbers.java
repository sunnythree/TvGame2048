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
    public void setScroes(int x,int y,int scores){
        mNumbers[x][y].mScores = scores;
    }
    public int getScrores(int x,int y){
        return mNumbers[x][y].mScores;
    }
    public boolean isPosionHasNumber(int x,int y){
        return mNumbers[x][y].mScores>0;
    }
    public Number[][] getmNumbers(){
        return mNumbers;
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
    public float getLeft(int x,int y){
        return mNumbers[x][y].px;
    }
    public float getTop(int x,int y){
        return mNumbers[x][y].py;
    }
    public void setPosition(int x,int y,float px,float py){
        mNumbers[x][y].px = px;
        mNumbers[x][y].py = py;
    }
}
