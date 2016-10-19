package com.jinwei.tvgame2048.algorithm;

import android.graphics.RectF;
import android.util.Log;

import com.jinwei.tvgame2048.model.Game2048StaticControl;
import com.jinwei.tvgame2048.model.Number;
import com.jinwei.tvgame2048.model.Numbers;

import java.util.Random;

/**
 * Created by Jinwei on 2016/10/19.
 */
public class Game2048Algorithm {
    private Numbers mNumbers;
    private final String TAG = "Game2048Algorithm";
    public Game2048Algorithm(){
        mNumbers = new Numbers();
    }
    public static int getRandom2Or4(){
        Random random =new Random();
        int number  = random.nextInt(4);
        if(number<3)
            return 2;
        else
            return 4;
    }
    public static int getRandomPosition(int blankcount){
        Random random =new Random();
        int number  = random.nextInt(blankcount);
        return number;
    }
    public void setOneRandomNumberInRandomPosition(){
        int number = Game2048Algorithm.getRandom2Or4();
        int blankCount = mNumbers.getBlankCount();
        int blankTh = 0;
        if(blankCount==0){
            //gameover
            return;
        }else{
            blankTh = Game2048Algorithm.getRandomPosition(blankCount);
        }
        int position = mNumbers.getPositonFromBlankCountTh(blankTh);
        if (position<0){
            Log.d(TAG,"getPositonFromBlankCountTh return error");
            return;
        }
        mNumbers.setScroes(position/4,position%4,number);
        mNumbers.setPosition(position/4,position%4,
                Game2048StaticControl.GameNumberViewPosition[position/4][position%4].left,
                Game2048StaticControl.GameNumberViewPosition[position/4][position%4].top);
    }
    public void initTowNumbers(){
        setOneRandomNumberInRandomPosition();
        setOneRandomNumberInRandomPosition();
    }
    public Numbers getmNumbers(){
        return mNumbers;
    }
    public Number getNumber(int x, int y){
        return mNumbers.getNumber(x,y);
    }
    public RectF getNumberRectF(int x, int y){
        float left = mNumbers.getLeft(x,y);
        float top = mNumbers.getTop(x,y);
        float right = left + Game2048StaticControl.gameNumberViewLength;
        float bottom = top + Game2048StaticControl.gameNumberViewLength;
        return new RectF(left,top,right,bottom);
    }
    public int getBitCount(int n)
    {
        // 循\BB\B7\BB\F1取\CA\FD\D7侄\FE\BD\F8\D6\C6位\CA\FD
        int c = 0;
        while ((n >>= 1)>0)
            c++;
        // \B7\B5\BB\D8位\CA\FD-1
        return c - 1;
    }
    public boolean isPosionHasNumber(int x,int y){
        return mNumbers.isPosionHasNumber(x,y);
    }
    public void swap(Number number1,Number number2){
        int scroes=number1.getScroes();
        number1.setScores(number2.getScroes());
        number2.setScores(scroes);
    }
    public boolean isNumberScroesEquel(Number number1,Number number2){
        return number1.getScroes()==number1.getScroes();
    }
}
