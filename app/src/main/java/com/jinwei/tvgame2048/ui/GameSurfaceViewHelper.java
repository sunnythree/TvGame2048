package com.jinwei.tvgame2048.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;

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
    }
    public void init(){
        mGAM.initTowNumbers();
        //mGAM.getNumber(0,0).mScores = 2;
        //mGAM.getNumber(0,3).mScores = 2;
    }
    public void doDraw(Canvas canvas, Paint paint ){
        initSurfaceBg(canvas,paint);
        drawSurfaceMap(canvas,paint);
        drawSurfaceNumbers(canvas,paint);
    }
    private void initSurfaceBg(Canvas canvas, Paint paint){
        canvas.drawColor(Color.WHITE);
        paint.setColor(Game2048StaticControl.gameSurfacceViewGbColor);
        canvas.drawRoundRect(0,0,Game2048StaticControl.gameSurfaceLength,Game2048StaticControl.gameSurfaceLength,20,20,paint);
    }
    private void drawSurfaceMap(Canvas canvas, Paint paint){
        paint.setColor(Color.WHITE);
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                    paint.setColor(Color.WHITE);
                    canvas.drawRoundRect(Game2048StaticControl.GameNumberViewPosition[i][j],
                            Game2048StaticControl.gameRadiumOfNumberViews,
                            Game2048StaticControl.gameRadiumOfNumberViews,paint);
            }
        }
    }
    private void drawSurfaceNumbers(Canvas canvas, Paint paint) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (mGAM.isPosionHasNumber(i, j)) {
                    drawNumber(i,j,canvas,paint);
                 }
            }
        }
    }
    private void drawNumber(int x,int y,Canvas canvas, Paint paint){
        paint.setColor(Game2048StaticControl.gameNumberColors[mGAM.getBitCount(mGAM.getNumber(x, y).mScores)]);
        canvas.drawRoundRect(Game2048StaticControl.GameNumberViewPosition[x][y],
                Game2048StaticControl.gameRadiumOfNumberViews,
                Game2048StaticControl.gameRadiumOfNumberViews, paint);
        int textSize = 100;
        int numberCount = 1;
        if(mGAM.getNumber(x, y).mScores>999){
            textSize = 40;
            numberCount = 4;
        }else if(mGAM.getNumber(x, y).mScores>99){
            textSize = 60;
            numberCount = 3;
        } if(mGAM.getNumber(x, y).mScores>9){
            textSize = 80;
            numberCount = 2;
        }else {
            textSize = 100;
            numberCount = 1;
        }
        paint.setTextSize(textSize);
        paint.setColor(Color.RED);
        canvas.drawText(String.valueOf(mGAM.getNumber(x, y).mScores),
                Game2048StaticControl.GameNumberViewPosition[x][y].left + Game2048StaticControl.gameNumberViewLength / 2 - numberCount*textSize/4,
                Game2048StaticControl.GameNumberViewPosition[x][y].top + Game2048StaticControl.gameNumberViewLength / 2 + textSize/3, paint);

    }
    private void drawNumberWithHalfValue(int x,int y,Canvas canvas, Paint paint){
        paint.setColor(Game2048StaticControl.gameNumberColors[mGAM.getBitCount(mGAM.getNumber(x, y).mScores/2)]);
        canvas.drawRoundRect(Game2048StaticControl.GameNumberViewPosition[x][y],
                Game2048StaticControl.gameRadiumOfNumberViews,
                Game2048StaticControl.gameRadiumOfNumberViews, paint);
        int textSize = 100;
        int numberCount = 1;
        if(mGAM.getNumber(x, y).mScores>999){
            textSize = 40;
            numberCount = 4;
        }else if(mGAM.getNumber(x, y).mScores>99){
            textSize = 60;
            numberCount = 3;
        } if(mGAM.getNumber(x, y).mScores>9){
            textSize = 80;
            numberCount = 2;
        }else {
            textSize = 100;
            numberCount = 1;
        }
        paint.setTextSize(textSize);
        paint.setColor(Color.RED);
        canvas.drawText(String.valueOf(mGAM.getNumber(x, y).mScores/2),
                Game2048StaticControl.GameNumberViewPosition[x][y].left + Game2048StaticControl.gameNumberViewLength / 2 - numberCount*textSize/4,
                Game2048StaticControl.GameNumberViewPosition[x][y].top + Game2048StaticControl.gameNumberViewLength / 2 + textSize/3, paint);

    }
    private void drawNumberByRectF(int x,int y,Canvas canvas, Paint paint,RectF rectF) {
        if(mGAM.getNumber(x, y).isNeedCombine){
            paint.setColor(Game2048StaticControl.gameNumberColors[mGAM.getBitCount(mGAM.getNumber(x, y).mScores/2)]);
        }else{
            paint.setColor(Game2048StaticControl.gameNumberColors[mGAM.getBitCount(mGAM.getNumber(x, y).mScores)]);
        }
        canvas.drawRoundRect(rectF,
                Game2048StaticControl.gameRadiumOfNumberViews,
                Game2048StaticControl.gameRadiumOfNumberViews, paint);
        paint.setColor(Color.RED);
        String text  = null;
        if(mGAM.getNumber(x, y).isNeedCombine){
            text = String.valueOf(mGAM.getNumber(x, y).mScores/2);
        }else{
            text = String.valueOf(mGAM.getNumber(x, y).mScores);
        }
        int textSize = 100;
        int numberCount = 1;
        if(mGAM.getNumber(x, y).mScores>999){
            textSize = 40;
            numberCount = 4;
        }else if(mGAM.getNumber(x, y).mScores>99){
            textSize = 60;
            numberCount = 3;
        } if(mGAM.getNumber(x, y).mScores>9){
            textSize = 80;
            numberCount = 2;
        }else {
            textSize = 100;
            numberCount = 1;
        }
        paint.setTextSize(textSize);
        canvas.drawText(text,
                (rectF.left + rectF.right)/2-numberCount*textSize/4,
                (rectF.top + rectF.bottom)/2+textSize/3,paint);

    }
    public void upKeyUpdate(Canvas canvas, Paint paint){
       mGAM.swapNumber(0,3);
        Log.d(TAG,"upKeyUpdate");
        initSurfaceBg(canvas,paint);
        drawSurfaceMap(canvas,paint);
        drawSurfaceNumbers(canvas,paint);

    }
    public void downKeyUpdate(Canvas canvas, Paint paint){
        mGAM.swapNumber(3,0);
        initSurfaceBg(canvas,paint);
        drawSurfaceMap(canvas,paint);
        drawSurfaceNumbers(canvas,paint);
    }
    public void leftKeyUpdate(SurfaceHolder holder, Paint paint){
        Log.d(TAG,"leftKeyUpdate");
        mGAM.leftKeyDealAlgorithm();
        startAnimation(holder,paint,DIRECT_LEFT);
        mGAM.updateNumbers();
        mGAM.setOneRandomNumberInRandomPosition();
        Canvas canvas = holder.lockCanvas();
        doDraw(canvas,paint);
        holder.unlockCanvasAndPost(canvas);
    }
    public void rightKeyUpdate(){


    }
    private final int DIRECT_UP = 0;
    private final int DIRECT_DOWN = 1;
    private final int DIRECT_LEFT = 2;
    private final int DIRECT_RIGHT = 3;
    public void startAnimation(SurfaceHolder holder,Paint paint,int direct){
        int count = 0;
        while (count++<10) {
            Canvas canvas = holder.lockCanvas();
            initSurfaceBg(canvas, paint);
            drawSurfaceMap(canvas, paint);
            drawSurfaceMapAndNumbersWhoIsNeedCombine(canvas,paint);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    RectF rectF = mGAM.aniInsertValue(i, j, count, 10,DIRECT_LEFT);
                    if(rectF != null && mGAM.isPosionHasNumber(i,j) && mGAM.getNumber(i,j).isNeedMove){
                        drawNumberByRectF(i,j,canvas,paint,rectF);
                    }
                }
            }
            holder.unlockCanvasAndPost(canvas);
        }
    }
    private void drawSurfaceMapAndNumbersWhoIsNeedCombine(Canvas canvas,Paint paint){
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(mGAM.isPosionHasNumber(i,j)){
                    if(mGAM.getNumber(i,j).isNeedCombine){
                        drawNumberWithHalfValue(i,j,canvas,paint);
                    }else if(!mGAM.getNumber(i,j).isNeedMove){
                        drawNumber(i,j,canvas,paint);
                    }
                }else {
                    paint.setColor(Color.WHITE);
                    canvas.drawRoundRect(Game2048StaticControl.GameNumberViewPosition[i][j],
                            Game2048StaticControl.gameRadiumOfNumberViews,
                            Game2048StaticControl.gameRadiumOfNumberViews,paint);
                }
            }
        }
    }
}
