package com.jinwei.tvgame2048.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.jinwei.tvgame2048.algorithm.Game2048Algorithm;
import com.jinwei.tvgame2048.model.Game2048StaticControl;
import com.jinwei.tvgame2048.model.Number;
import com.jinwei.tvgame2048.model.Numbers;

/**
 * Created by Jinwei on 2016/10/19.
 */
public class GameSurfaceViewHelper {
    private final String TAG = "GameSurfaceViewHelper";
    Game2048Algorithm mGAM;
    public GameSurfaceViewHelper(){
        mGAM = new Game2048Algorithm();
        mGAM.initTowNumbers();
    }
    public void doDraw(Canvas canvas, Paint paint ){
        initSurfaceBg(canvas,paint);
        drawSurfaceMap(canvas,paint);
    }
    private void initSurfaceBg(Canvas canvas, Paint paint){
        canvas.drawColor(Color.WHITE);
        paint.setColor(Game2048StaticControl.gameSurfacceViewGbColor);
        canvas.drawRoundRect(0,0,Game2048StaticControl.gameSurfaceLength,Game2048StaticControl.gameSurfaceLength,20,20,paint);
    }
    private void drawSurfaceMap(Canvas canvas, Paint paint){
        paint.setColor(Color.WHITE);
        Numbers numbers = mGAM.getmNumbers();
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(numbers.isPosionHasNumber(i,j)){
                    Log.d(TAG,"i:"+i+"j:"+j+"has num");
                    paint.setColor(Game2048StaticControl.gameNumberColors[mGAM.getBitCount(numbers.getScrores(i,j))]);
                    canvas.drawRoundRect(Game2048StaticControl.GameNumberViewPosition[i][j],
                            Game2048StaticControl.gameRadiumOfNumberViews,
                            Game2048StaticControl.gameRadiumOfNumberViews,paint);
                    paint.setTextSize(100);
                    paint.setColor(Color.RED);
                    canvas.drawText(String.valueOf(mGAM.getmNumbers().getScrores(i,j)),
                            mGAM.getNumberRectF(i,j).left+Game2048StaticControl.gameNumberViewLength/2-25,
                            mGAM.getNumberRectF(i,j).top+Game2048StaticControl.gameNumberViewLength/2+30,paint);
                }else {
                    paint.setColor(Color.WHITE);
                    canvas.drawRoundRect(Game2048StaticControl.GameNumberViewPosition[i][j],
                            Game2048StaticControl.gameRadiumOfNumberViews,
                            Game2048StaticControl.gameRadiumOfNumberViews,paint);
                }

            }
        }
    }

    public void upKeyUpdate(){

    }
    public void downKeyUpdate(){

    }
    public void leftKeyUpdate(){
        Log.d(TAG,"leftKeyUpdate");
        int i, j, k;
        for(i=0;i<4;i++){
            j=k=0;
            while (true) {
                while (j<4 && mGAM.isPosionHasNumber(i,j))
                    j++;
                if (j > 3)
                    break;
                Log.d(TAG,"j: "+j);
                Log.d(TAG,"k: "+k);
                if (j != k){
                    Log.d(TAG,"mGAM.getNumber(i,j)getScroes:"+mGAM.getNumber(i,j).getScroes());
                    Log.d(TAG,"mGAM.getNumber(i,k)getScroes:"+mGAM.getNumber(i,k).getScroes());
                    mGAM.swap(mGAM.getNumber(i,j),mGAM.getNumber(i,k));
                    Log.d(TAG,"mGAM.getNumber(i,j)getScroes:"+mGAM.getNumber(i,j).getScroes());
                    Log.d(TAG,"mGAM.getNumber(i,k)getScroes:"+mGAM.getNumber(i,k).getScroes());
                }
                if (k > 0 && mGAM.isNumberScroesEquel(mGAM.getNumber(i,k),mGAM.getNumber(i,k-1))){
                    int scores = mGAM.getNumber(i,k-1).getScroes();
                    scores <<= 1;
                    mGAM.getNumber(i,k-1).setScores(scores);
                    mGAM.getNumber(i,k).reset();
                } else{
                    k++;
                }
                j++;
            }
          }
    }
    public void rightKeyUpdate(){

    }
}
