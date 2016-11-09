package com.jinwei.tvgame2048.algorithm;

import android.graphics.RectF;
import android.os.Handler;
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
    Handler mHandler;
    private final String TAG = "Game2048Algorithm";
    public Game2048Algorithm(Handler handler){
        mNumbers = new Numbers();
        mHandler = handler;
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
    public int  setOneRandomNumberInRandomPosition(){
        int scores = Game2048Algorithm.getRandom2Or4();
        int blankCount = getBlankCount();
        Log.d(TAG,"blankCount:"+blankCount);
        int blankTh = 0;
        if(blankCount<=0){
            return -1;
        }else{
            blankTh = Game2048Algorithm.getRandomPosition(blankCount);
        }
        int position = mNumbers.getPositonFromBlankCountTh(blankTh);
        if (position<0){
            Log.d(TAG,"getPositonFromBlankCountTh return error");
            return -1;
        }
        Number num = mNumbers.getNumber(position/Game2048StaticControl.gamePlayMode,position%Game2048StaticControl.gamePlayMode);
        num.mScores = scores;
        num.mBeforePosition = num.mCurPosition = position;
        num.isNeedCombine = num.isNeedMove = false;
        return position;
    }
    public void initTowNumbers(){
        setOneRandomNumberInRandomPosition();
        setOneRandomNumberInRandomPosition();
    }
    public Number[][] getmNumbers(){
        return mNumbers.getNumbers();
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
    public void aniInsertValue(int x,int y,int count,int insertCount,int direct,RectF rectF){
        Number number = getNumber(x,y);
        if(number.mCurPosition ==  number.mBeforePosition){
            return;
        }
        float xDiffPixels = Game2048StaticControl.GameNumberViewPosition[number.mCurPosition/Game2048StaticControl.gamePlayMode]
                [number.mCurPosition%Game2048StaticControl.gamePlayMode].left
                -Game2048StaticControl.GameNumberViewPosition[number.mBeforePosition/Game2048StaticControl.gamePlayMode]
                [number.mBeforePosition%Game2048StaticControl.gamePlayMode].left;
        float yDiffPixels = Game2048StaticControl.GameNumberViewPosition[number.mCurPosition/Game2048StaticControl.gamePlayMode]
                [number.mCurPosition%Game2048StaticControl.gamePlayMode].top
                -Game2048StaticControl.GameNumberViewPosition[number.mBeforePosition/Game2048StaticControl.gamePlayMode]
                [number.mBeforePosition%Game2048StaticControl.gamePlayMode].top;
        xDiffPixels = Math.abs(xDiffPixels);
        yDiffPixels = Math.abs(yDiffPixels);
        float xStep = xDiffPixels/insertCount;
        float yStep = yDiffPixels/insertCount;
        float xNewPosition = Game2048StaticControl.GameNumberViewPosition[number.mCurPosition/Game2048StaticControl.gamePlayMode]
                [number.mCurPosition%Game2048StaticControl.gamePlayMode].left;
        float yNewPosition = Game2048StaticControl.GameNumberViewPosition[number.mCurPosition/Game2048StaticControl.gamePlayMode]
                [number.mCurPosition%Game2048StaticControl.gamePlayMode].top;;
        switch (direct){
            case DIRECT_UP:{
                yNewPosition = Game2048StaticControl.GameNumberViewPosition[number.mBeforePosition/Game2048StaticControl.gamePlayMode]
                        [number.mBeforePosition%Game2048StaticControl.gamePlayMode].top
                        - yStep*count;
                break;
            }
            case DIRECT_DOWN:{
                yNewPosition = Game2048StaticControl.GameNumberViewPosition[number.mBeforePosition/Game2048StaticControl.gamePlayMode]
                        [number.mBeforePosition%Game2048StaticControl.gamePlayMode].top
                                + yStep*count;
                break;
            }
            case DIRECT_LEFT:{
                xNewPosition = Game2048StaticControl.GameNumberViewPosition[number.mBeforePosition/Game2048StaticControl.gamePlayMode]
                        [number.mBeforePosition%Game2048StaticControl.gamePlayMode].left
                        - xStep*count;
                break;
            }
            case DIRECT_RIGHT:{
                xNewPosition = Game2048StaticControl.GameNumberViewPosition[number.mBeforePosition/Game2048StaticControl.gamePlayMode]
                        [number.mBeforePosition%Game2048StaticControl.gamePlayMode].left
                        + xStep*count;
                break;
            }
            default:break;
        }
        rectF.set(xNewPosition,yNewPosition,xNewPosition+Game2048StaticControl.gameNumberViewLength
                ,yNewPosition+Game2048StaticControl.gameNumberViewLength);
    }
    public void updateNumbers(){
        for (int i = 0; i < Game2048StaticControl.gamePlayMode; i++) {
            for (int j = 0; j < Game2048StaticControl.gamePlayMode; j++) {
                Number number = getNumber(i,j);
                number.isNeedCombine = number.isNeedMove = false;
            }
        }
    }
    public void resetCurrentNumbers(Number [][] numbers){
        for (int i = 0; i < Game2048StaticControl.gamePlayMode; i++) {
            for (int j = 0; j < Game2048StaticControl.gamePlayMode; j++) {
                Number number = getNumber(i,j);
                number.mScores = numbers[i][j].mScores;
                number.mCurPosition = numbers[i][j].mCurPosition;
                number.mBeforePosition = numbers[i][j].mBeforePosition;
                number.isNeedCombine = numbers[i][j].isNeedCombine;
                number.isNeedMove = numbers[i][j].isNeedMove;
            }
        }
    }
    public void swapNumber(int position1,int position2){
        mNumbers.swapNumber(position1,position2);
    }

    //return 0:do nothing
    //return 1:move
    //return 2:combine
    public int leftKeyDealAlgorithm(){
        int i, j, k;
        boolean isMoved = false;
        boolean isFinalMove = false;
        boolean isFinalCombie = false;
        for(i=0;i<Game2048StaticControl.gamePlayMode;i++){
            j=k=0;
            isMoved = false;
            while (true) {
                while (j<Game2048StaticControl.gamePlayMode && !isPosionHasNumber(i,j))
                    j++;
                if (j > Game2048StaticControl.gamePlayMode-1)
                    break;
                if (j > k){
                    isMoved = true;
                    isFinalMove = true;
                    Number number = getNumber(i,j);
                    number.isNeedMove = true;
                    number.isNeedCombine = false;
                    swapNumber(i*Game2048StaticControl.gamePlayMode+k,i*Game2048StaticControl.gamePlayMode+j);
                }
                if (k > 0 && getNumber(i,k).mScores==getNumber(i,k-1).mScores && !getNumber(i,k-1).isNeedCombine){
                    isFinalCombie = true;
                    Number numberk = getNumber(i,k);
                    Number numberkl = getNumber(i,k-1);
                    if(isMoved){
                        numberkl.mBeforePosition = numberk.mBeforePosition;
                    }else {
                        numberkl.mBeforePosition =  i*Game2048StaticControl.gamePlayMode+k;
                    }
                    numberkl.mCurPosition = i*Game2048StaticControl.gamePlayMode+k-1;
                    numberkl.isNeedMove = true;
                    numberkl.isNeedCombine = true;
                    numberkl.mScores <<=1;
                    updateCurScoresAndHistoryScores(numberkl.mScores);
                    numberk.reset();
                    numberk.mCurPosition = numberk.mBeforePosition = i*Game2048StaticControl.gamePlayMode+k;
                } else{
                    k++;
                }
                j++;
            }
        }
        return isFinalCombie?2:(isFinalMove?1:0);
    }

    public int rightKeyDealAlgorithm(){
        int i, j, k;
        boolean isMoved = false;
        boolean isFinalMove = false;
        boolean isFinalCombie = false;
        for(i=0;i<Game2048StaticControl.gamePlayMode;i++){
            j=k=Game2048StaticControl.gamePlayMode-1;
            isMoved = false;
            while (true) {
                while (j>-1 && !isPosionHasNumber(i,j))
                    j--;
                if (j < 0)
                    break;
                if (j < k){
                    isMoved = true;
                    isFinalMove = true;
                    Number number = getNumber(i,j);
                    number.isNeedMove = true;
                    number.isNeedCombine = false;
                    swapNumber(i*Game2048StaticControl.gamePlayMode+k,i*Game2048StaticControl.gamePlayMode+j);
                }
                if (k < Game2048StaticControl.gamePlayMode-1 && getNumber(i,k).mScores==getNumber(i,k+1).mScores && !getNumber(i,k+1).isNeedCombine){
                    isFinalCombie = true;
                    Number numberk = getNumber(i,k);
                    Number numberkl = getNumber(i,k+1);
                    if(isMoved){
                        numberkl.mBeforePosition = numberk.mBeforePosition;
                    }else {
                        numberkl.mBeforePosition =  i*Game2048StaticControl.gamePlayMode+k;
                    }
                    numberkl.mCurPosition = i*Game2048StaticControl.gamePlayMode+k+1;
                    numberkl.isNeedMove = true;
                    numberkl.isNeedCombine = true;
                    numberkl.mScores <<=1;
                    updateCurScoresAndHistoryScores(numberkl.mScores);
                    numberk.reset();
                    numberk.mCurPosition = numberk.mBeforePosition = i*Game2048StaticControl.gamePlayMode+k;
                } else{
                    k--;
                }
                j--;
            }
        }
        return isFinalCombie?2:(isFinalMove?1:0);
    }
    public int upKeyDealAlgorithm(){
        int i, j, k;
        boolean isMoved = false;
        boolean isFinalMove = false;
        boolean isFinalCombie = false;
        for(i=0;i<Game2048StaticControl.gamePlayMode;i++){
            j=k=0;
            isMoved = false;
            while (true) {
                while (j<Game2048StaticControl.gamePlayMode && !isPosionHasNumber(j,i))
                    j++;
                if (j > Game2048StaticControl.gamePlayMode-1)
                    break;
                if (j > k){
                    isMoved = true;
                    isFinalMove = true;
                    Number number = getNumber(j,i);
                    number.isNeedMove = true;
                    number.isNeedCombine = false;
                    swapNumber(k*Game2048StaticControl.gamePlayMode+i,j*Game2048StaticControl.gamePlayMode+i);
                }
                if (k > 0 && getNumber(k,i).mScores==getNumber(k-1,i).mScores && !getNumber(k-1,i).isNeedCombine){
                    isFinalCombie = true;
                    Number numberk = getNumber(k,i);
                    Number numberkl = getNumber(k-1,i);
                    if(isMoved){
                        numberkl.mBeforePosition = numberk.mBeforePosition;
                    }else {
                        numberkl.mBeforePosition =  k*Game2048StaticControl.gamePlayMode+i;
                    }
                    numberkl.mCurPosition = (k-1)*Game2048StaticControl.gamePlayMode+i;
                    numberkl.isNeedMove = true;
                    numberkl.isNeedCombine = true;
                    numberkl.mScores <<=1;
                    updateCurScoresAndHistoryScores(numberkl.mScores);
                    numberk.reset();
                    numberk.mCurPosition = numberk.mBeforePosition = k*Game2048StaticControl.gamePlayMode+i;
                } else{
                    k++;
                }
                j++;
            }
        }
        return isFinalCombie?2:(isFinalMove?1:0);
    }
    public int downKeyDealAlgorithm(){
        int i, j, k;
        boolean isMoved = false;
        boolean isFinalMove = false;
        boolean isFinalCombie = false;
        for(i=0;i<Game2048StaticControl.gamePlayMode;i++){
            j=k=Game2048StaticControl.gamePlayMode-1;
            isMoved = false;
            while (true) {
                while (j>-1 && !isPosionHasNumber(j,i))
                    j--;
                if (j < 0)
                    break;
                if (j < k){
                    isMoved = true;
                    isFinalMove = true;
                    Number number = getNumber(j,i);
                    number.isNeedMove = true;
                    number.isNeedCombine = false;
                    swapNumber(k*Game2048StaticControl.gamePlayMode+i,j*Game2048StaticControl.gamePlayMode+i);
                }
                if (k < Game2048StaticControl.gamePlayMode-1 && getNumber(k,i).mScores==getNumber(k+1,i).mScores && !getNumber(k+1,i).isNeedCombine){
                    isFinalCombie = true;
                    Number numberk = getNumber(k,i);
                    Number numberkl = getNumber(k+1,i);
                    if(isMoved){
                        numberkl.mBeforePosition = numberk.mBeforePosition;
                    }else {
                        numberkl.mBeforePosition =  k*Game2048StaticControl.gamePlayMode+i;
                    }
                    numberkl.mCurPosition = (k+1)*Game2048StaticControl.gamePlayMode+i;
                    numberkl.isNeedMove = true;
                    numberkl.isNeedCombine = true;
                    numberkl.mScores <<=1;
                    updateCurScoresAndHistoryScores(numberkl.mScores);
                    numberk.reset();
                    numberk.mCurPosition = numberk.mBeforePosition = k*Game2048StaticControl.gamePlayMode+i;
                } else{
                    k--;
                }
                j--;
            }
        }
        return isFinalCombie?2:(isFinalMove?1:0);
    }
    public boolean checkGameOver(){
        Log.d(TAG,"checkGameOver");
        for (int i = 0; i < Game2048StaticControl.gamePlayMode; i++)
        {
            for (int j = 0; j < Game2048StaticControl.gamePlayMode; j++)
            {
                if (j != Game2048StaticControl.gamePlayMode-1 && getNumber(i,j).mScores == getNumber(i,j+1).mScores)
                    return false;
                if (i != Game2048StaticControl.gamePlayMode-1 && getNumber(i,j).mScores == getNumber(i+1,j).mScores)
                    return false;
            }
        }
        if (mListener!=null){
            mListener.onGameOver();
        }
        return true;
    }
    public void checkGameWin(){
        if(!Game2048StaticControl.isShouldCheckGameWin)return;
        Number number;
        int bestScores=0;
        for (int i = 0; i < Game2048StaticControl.gamePlayMode; i++) {
            for (int j = 0; j < Game2048StaticControl.gamePlayMode; j++) {
                number = getNumber(i,j);
                if(number.mScores>bestScores){
                    bestScores = number.mScores;
                }
            }
        }
        Game2048StaticControl.gameCurrentBestNumberScores = bestScores;
        int winScores = (int)Math.pow(2,Game2048StaticControl.gamePlayMode*2+3);
        if(bestScores==winScores){
            if (mListener!=null){
                mListener.onGameVictory();
            }
        }
    }
    private void updateCurScoresAndHistoryScores(int stepScores){
        Game2048StaticControl.gameCurrentScores += stepScores;
        //Log.d(TAG,"stepScores"+stepScores);
        //Log.d(TAG,"Game2048StaticControl.gameCurrentScores"+Game2048StaticControl.gameCurrentScores);
        if(Game2048StaticControl.gameHistoryHighestScores<Game2048StaticControl.gameCurrentScores){
            Game2048StaticControl.gameHistoryHighestScores=Game2048StaticControl.gameCurrentScores;
        }
        Log.d(TAG,"Game2048StaticControl.gameHistoryHighestScores"+Game2048StaticControl.gameHistoryHighestScores);
        mHandler.sendEmptyMessage(Game2048StaticControl.UPDATE_CURRENT_HISTORY_SCORES);
    }
    public void restartGame(){
        Number number;
        Game2048StaticControl.gameCurrentScores = 0;
        for (int i = 0; i < Game2048StaticControl.gamePlayMode; i++) {
            for (int j = 0; j < Game2048StaticControl.gamePlayMode; j++) {
                number = getNumber(i,j);
                number.reset();
                number.mBeforePosition = 0;
                number.mCurPosition = 0;
            }
        }
        initTowNumbers();
    }
    public int getBlankCount(){
        return mNumbers.getBlankCount();
    }
}
