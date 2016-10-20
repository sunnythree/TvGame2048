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
    public static interface GameResultListener{
        public void onGameOver();
        public void onGameVictory();
    }
    private Numbers mNumbers;
    GameResultListener mListener;
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
    public void setListener(GameResultListener listener){
        mListener = listener;
    }
    public void setOneRandomNumberInRandomPosition(){
        int scores = Game2048Algorithm.getRandom2Or4();
        int blankCount = mNumbers.getBlankCount();
        int blankTh = 0;
        if(blankCount==0){
            if(checkGameOver()){
                //gameOver
                if(mListener != null){
                    mListener.onGameOver();
                }
            }
            return;
        }else{
            blankTh = Game2048Algorithm.getRandomPosition(blankCount);
        }
        int position = mNumbers.getPositonFromBlankCountTh(blankTh);
        if (position<0){
            Log.d(TAG,"getPositonFromBlankCountTh return error");
            return;
        }
        Number num = mNumbers.getNumber(position/4,position%4);
        num.mScores = scores;
        num.mBeforePosition = num.mCurPosition = position;
        num.isNeedCombine = num.isNeedMove = false;
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
        Number number = getNumber(x,y);
        return number.mScores>0;
    }
    private final int DIRECT_UP = 0;
    private final int DIRECT_DOWN = 1;
    private final int DIRECT_LEFT = 2;
    private final int DIRECT_RIGHT = 3;
    public RectF aniInsertValue(int x,int y,int count,int insertCount,int direct){
        Number number = getNumber(x,y);
        if(number.mCurPosition ==  number.mBeforePosition){
            return null;
        }
        float xDiffPixels = Game2048StaticControl.GameNumberViewPosition[number.mCurPosition/4][number.mCurPosition%4].left
                -Game2048StaticControl.GameNumberViewPosition[number.mBeforePosition/4][number.mBeforePosition%4].left;
        float yDiffPixels = Game2048StaticControl.GameNumberViewPosition[number.mCurPosition/4][number.mCurPosition%4].top
                -Game2048StaticControl.GameNumberViewPosition[number.mBeforePosition/4][number.mBeforePosition%4].top;
        xDiffPixels = Math.abs(xDiffPixels);
        yDiffPixels = Math.abs(yDiffPixels);
        float xStep = xDiffPixels/insertCount;
        float yStep = yDiffPixels/insertCount;
        float xNewPosition = Game2048StaticControl.GameNumberViewPosition[number.mCurPosition/4][number.mCurPosition%4].left;
        float yNewPosition = Game2048StaticControl.GameNumberViewPosition[number.mCurPosition/4][number.mCurPosition%4].top;;
        switch (direct){
            case DIRECT_UP:{
                yNewPosition = Game2048StaticControl.GameNumberViewPosition[number.mBeforePosition/4][number.mBeforePosition%4].top
                        - yStep*count;
                break;
            }
            case DIRECT_DOWN:{
                yNewPosition = Game2048StaticControl.GameNumberViewPosition[number.mBeforePosition/4][number.mBeforePosition%4].top
                                + yStep*count;
                break;
            }
            case DIRECT_LEFT:{
                xNewPosition = Game2048StaticControl.GameNumberViewPosition[number.mBeforePosition/4][number.mBeforePosition%4].left
                        - xStep*count;
                break;
            }
            case DIRECT_RIGHT:{
                xNewPosition = Game2048StaticControl.GameNumberViewPosition[number.mBeforePosition/4][number.mBeforePosition%4].left
                        + xStep*count;
                break;
            }
            default:break;
        }
        return new RectF(xNewPosition,yNewPosition,xNewPosition+Game2048StaticControl.gameNumberViewLength,yNewPosition+Game2048StaticControl.gameNumberViewLength);
    }
    public void updateNumbers(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Number number = getNumber(i,j);
                number.isNeedCombine = number.isNeedMove = false;
            }
        }
    }
    public void swapNumber(int position1,int position2){
        mNumbers.swapNumber(position1,position2);
    }

    public void leftKeyDealAlgorithm(){
        int i, j, k;
        boolean isMoved = false;
        for(i=0;i<4;i++){
            j=k=0;
            isMoved = false;
            while (true) {
                while (j<4 && !isPosionHasNumber(i,j))
                    j++;
                if (j > 3)
                    break;
                if (j > k){
                    isMoved = true;
                    Number number = getNumber(i,j);
                    number.isNeedMove = true;
                    number.isNeedCombine = false;
                    swapNumber(i*4+k,i*4+j);
                }
                if (k > 0 && getNumber(i,k).mScores==getNumber(i,k-1).mScores && !getNumber(i,k-1).isNeedCombine){
                    Number numberk = getNumber(i,k);
                    Number numberkl = getNumber(i,k-1);
                    if(isMoved){
                        numberkl.mBeforePosition = numberk.mBeforePosition;
                    }else {
                        numberkl.mBeforePosition =  i*4+k;
                    }
                    numberkl.mCurPosition = i*4+k-1;
                    numberkl.isNeedMove = true;
                    numberkl.isNeedCombine = true;
                    numberkl.mScores <<=1;
                    numberk.reset();
                    numberk.mCurPosition = numberk.mBeforePosition = i*4+k;
                } else{
                    k++;
                }
                j++;
            }
        }
    }

    public void rightKeyDealAlgorithm(){
        int i, j, k;
        boolean isMoved = false;
        for(i=0;i<4;i++){
            j=k=3;
            isMoved = false;
            while (true) {
                while (j>-1 && !isPosionHasNumber(i,j))
                    j--;
                if (j < 0)
                    break;
                if (j < k){
                    isMoved = true;
                    Number number = getNumber(i,j);
                    number.isNeedMove = true;
                    number.isNeedCombine = false;
                    swapNumber(i*4+k,i*4+j);
                }
                if (k < 3 && getNumber(i,k).mScores==getNumber(i,k+1).mScores && !getNumber(i,k+1).isNeedCombine){
                    Number numberk = getNumber(i,k);
                    Number numberkl = getNumber(i,k+1);
                    if(isMoved){
                        numberkl.mBeforePosition = numberk.mBeforePosition;
                    }else {
                        numberkl.mBeforePosition =  i*4+k;
                    }
                    numberkl.mCurPosition = i*4+k+1;
                    numberkl.isNeedMove = true;
                    numberkl.isNeedCombine = true;
                    numberkl.mScores <<=1;
                    numberk.reset();
                    numberk.mCurPosition = numberk.mBeforePosition = i*4+k;
                } else{
                    k--;
                }
                j--;
            }
        }
    }
    public void upKeyDealAlgorithm(){
        int i, j, k;
        boolean isMoved = false;
        for(i=0;i<4;i++){
            j=k=0;
            isMoved = false;
            while (true) {
                while (j<4 && !isPosionHasNumber(j,i))
                    j++;
                if (j > 3)
                    break;
                if (j > k){
                    isMoved = true;
                    Number number = getNumber(j,i);
                    number.isNeedMove = true;
                    number.isNeedCombine = false;
                    swapNumber(k*4+i,j*4+i);
                }
                if (k > 0 && getNumber(k,i).mScores==getNumber(k-1,i).mScores && !getNumber(k-1,i).isNeedCombine){
                    Number numberk = getNumber(k,i);
                    Number numberkl = getNumber(k-1,i);
                    if(isMoved){
                        numberkl.mBeforePosition = numberk.mBeforePosition;
                    }else {
                        numberkl.mBeforePosition =  k*4+i;
                    }
                    numberkl.mCurPosition = (k-1)*4+i;
                    numberkl.isNeedMove = true;
                    numberkl.isNeedCombine = true;
                    numberkl.mScores <<=1;
                    numberk.reset();
                    numberk.mCurPosition = numberk.mBeforePosition = k*4+i;
                } else{
                    k++;
                }
                j++;
            }
        }
    }
    public void downKeyDealAlgorithm(){
        int i, j, k;
        boolean isMoved = false;
        for(i=0;i<4;i++){
            j=k=3;
            isMoved = false;
            while (true) {
                while (j>-1 && !isPosionHasNumber(j,i))
                    j--;
                if (j < 0)
                    break;
                if (j < k){
                    isMoved = true;
                    Number number = getNumber(j,i);
                    number.isNeedMove = true;
                    number.isNeedCombine = false;
                    swapNumber(k*4+i,j*4+i);
                }
                if (k < 3 && getNumber(k,i).mScores==getNumber(k+1,i).mScores && !getNumber(k+1,i).isNeedCombine){
                    Number numberk = getNumber(k,i);
                    Number numberkl = getNumber(k+1,i);
                    if(isMoved){
                        numberkl.mBeforePosition = numberk.mBeforePosition;
                    }else {
                        numberkl.mBeforePosition =  k*4+i;
                    }
                    numberkl.mCurPosition = (k+1)*4+i;
                    numberkl.isNeedMove = true;
                    numberkl.isNeedCombine = true;
                    numberkl.mScores <<=1;
                    numberk.reset();
                    numberk.mCurPosition = numberk.mBeforePosition = k*4+i;
                } else{
                    k--;
                }
                j--;
            }
        }
    }
    public boolean checkGameOver(){
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if (j != 3 && getNumber(i,j).mScores == getNumber(i,j+1).mScores)
                    return false;
                if (i != 3 && getNumber(i,j).mScores == getNumber(i+1,j).mScores)
                    return false;
            }
        }
        return true;
    }
    public void checkGameWin(int curScore){
        if(curScore==2048){
            if(mListener!=null){
                mListener.onGameVictory();
            }
        }
    }
}
