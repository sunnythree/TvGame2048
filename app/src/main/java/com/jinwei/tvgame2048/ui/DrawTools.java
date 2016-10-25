package com.jinwei.tvgame2048.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.jinwei.tvgame2048.algorithm.Game2048Algorithm;
import com.jinwei.tvgame2048.model.Game2048StaticControl;

/**
 * Created by Jinwei on 2016/10/22.
 */
public class DrawTools {
    private final String TAG = "DrawTools";
    Game2048Algorithm mGAM;
    public DrawTools(Game2048Algorithm game2048Algorithm){
        mGAM =game2048Algorithm;
    }
    public static void initSurfaceBg(Canvas canvas, Paint paint){
        canvas.drawColor(Color.WHITE);
        paint.setColor(Game2048StaticControl.gameSurfacceViewGbColor);
        canvas.drawRoundRect(0,0,Game2048StaticControl.gameSurfaceLength,Game2048StaticControl.gameSurfaceLength,20,20,paint);
    }
    public void drawSurfaceMap(Canvas canvas, Paint paint){
        paint.setColor(Color.WHITE);
        for(int i=0;i<Game2048StaticControl.gamePlayMode;i++){
            for(int j=0;j<Game2048StaticControl.gamePlayMode;j++){
                paint.setColor(Color.WHITE);
                canvas.drawRoundRect(Game2048StaticControl.GameNumberViewPosition[i][j],
                        Game2048StaticControl.gameRadiumOfNumberViews,
                        Game2048StaticControl.gameRadiumOfNumberViews,paint);
            }
        }
    }
    public void drawSurfaceNumbers(Canvas canvas, Paint paint) {
        for (int i = 0; i < Game2048StaticControl.gamePlayMode; i++) {
            for (int j = 0; j < Game2048StaticControl.gamePlayMode; j++) {
                if (mGAM.isPosionHasNumber(i, j)) {
                    drawNumber(i,j,canvas,paint);
                }
            }
        }
    }
    public void drawNumber(int x,int y,Canvas canvas, Paint paint){
        int scores = mGAM.getNumber(x,y).mScores;
        //1.draw rect
        paint.setColor(Game2048StaticControl.gameNumberColors[mGAM.getBitCount(scores)]);
        canvas.drawRoundRect(Game2048StaticControl.GameNumberViewPosition[x][y],
                Game2048StaticControl.gameRadiumOfNumberViews,
                Game2048StaticControl.gameRadiumOfNumberViews, paint);
        //2.draw text
        //2.1 set color
        if(scores>16){
            paint.setColor(Color.WHITE);
        }else {
            paint.setColor(Color.RED);
        }
        //2.2 set text size
        String text = String.valueOf(scores);
        float textSize = 100f;
        int numberCount = 1;
        while(scores/10>0){
            scores = scores/10;
            numberCount++;
        }
        float lenght = Game2048StaticControl.gameNumberViewLength/3*2;
        textSize = Math.min(lenght/numberCount*2,lenght);
        paint.setTextSize(textSize);
        canvas.drawText(text,
                Game2048StaticControl.GameNumberViewPosition[x][y].left + Game2048StaticControl.gameNumberViewLength / 2 - numberCount*textSize/4-6,
                Game2048StaticControl.GameNumberViewPosition[x][y].top + Game2048StaticControl.gameNumberViewLength / 2 + textSize/3, paint);

    }
    public void drawNumberWithHalfValue(int x,int y,Canvas canvas, Paint paint){
        int scores = mGAM.getNumber(x,y).mScores/2;
        //1.draw rect
        paint.setColor(Game2048StaticControl.gameNumberColors[mGAM.getBitCount(scores)]);
        canvas.drawRoundRect(Game2048StaticControl.GameNumberViewPosition[x][y],
                Game2048StaticControl.gameRadiumOfNumberViews,
                Game2048StaticControl.gameRadiumOfNumberViews, paint);
        //2.draw text
        //2.1set color
        if(scores>16){
            paint.setColor(Color.WHITE);
        }else {
            paint.setColor(Color.RED);
        }
        //2.2set text size
        String text = String.valueOf(scores);
        int numberCount = 1;
        float textSize = 100f;
        while(scores/10>0){
            scores = scores/10;
            numberCount++;
        }
        float lenght = Game2048StaticControl.gameNumberViewLength/3*2;
        textSize = Math.min(lenght/numberCount*2,lenght);
        paint.setTextSize(textSize);
        canvas.drawText(text,
                Game2048StaticControl.GameNumberViewPosition[x][y].left + Game2048StaticControl.gameNumberViewLength / 2 - numberCount*textSize/4-6,
                Game2048StaticControl.GameNumberViewPosition[x][y].top + Game2048StaticControl.gameNumberViewLength / 2 + textSize/3, paint);

    }
    public void drawNumberByRectF(int x,int y,Canvas canvas, Paint paint,RectF rectF) {
        int scores = mGAM.getNumber(x,y).mScores;
        // Log.d(TAG,rectF.toString());
        //1.draw rect
        if(mGAM.getNumber(x, y).isNeedCombine){
            scores = scores/2;
            paint.setColor(Game2048StaticControl.gameNumberColors[mGAM.getBitCount(scores)]);
        }else{
            paint.setColor(Game2048StaticControl.gameNumberColors[mGAM.getBitCount(scores)]);
        }
        canvas.drawRoundRect(rectF,
                Game2048StaticControl.gameRadiumOfNumberViews,
                Game2048StaticControl.gameRadiumOfNumberViews, paint);
        //2.draw text
        //2.1 set color
        if(scores>16){
            paint.setColor(Color.WHITE);
        }else {
            paint.setColor(Color.RED);
        }
        //2.2 set text size
        String text = String.valueOf(scores);
        int numberCount = 1;
        float textSize = 100f;
        while(scores/10>0){
            scores = scores/10;
            numberCount++;
        }
        float lenght = (rectF.right-rectF.left)/3*2;
        textSize = Math.min(lenght/numberCount*2,lenght);
        paint.setTextSize(textSize);
        canvas.drawText(text,
                (rectF.left + rectF.right)/2-numberCount*textSize/4,
                (rectF.top + rectF.bottom)/2+textSize/3,paint);

    }
    public void drawSurfaceMapAndNumbersWhoIsNeedCombine(Canvas canvas,Paint paint){
        for(int i=0;i<Game2048StaticControl.gamePlayMode;i++){
            for(int j=0;j<Game2048StaticControl.gamePlayMode;j++){
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
    public void drawGameOver(Canvas canvas, Paint paint){
        paint.setColor(Color.argb(128,256,256,0));
        canvas.drawRoundRect(0,0,Game2048StaticControl.gameSurfaceLength,Game2048StaticControl.gameSurfaceLength,20,20,paint);
        paint.setTextSize(Game2048StaticControl.gameWinOrLostTextSize);
        paint.setColor(Color.WHITE);
        String text = "YOU LOST";
        float width = paint.measureText(text);
        canvas.drawText(text,Game2048StaticControl.gameSurfaceLength/2- width/2,
                Game2048StaticControl.gameSurfaceLength/2+Game2048StaticControl.gameWinOrLostTextSize/3,paint);
    }
    public void drawGameVictory(Canvas canvas, Paint paint){
        paint.setColor(Color.argb(128,256,256,0));
        canvas.drawRoundRect(0,0,Game2048StaticControl.gameSurfaceLength,Game2048StaticControl.gameSurfaceLength,20,20,paint);
        paint.setTextSize(Game2048StaticControl.gameWinOrLostTextSize);
        paint.setColor(Color.WHITE);
        String text = "YOU WIN";
        float width = paint.measureText(text);
        canvas.drawText(text,Game2048StaticControl.gameSurfaceLength/2-width/2,
                Game2048StaticControl.gameSurfaceLength/2+Game2048StaticControl.gameWinOrLostTextSize/3,paint);
    }
}
