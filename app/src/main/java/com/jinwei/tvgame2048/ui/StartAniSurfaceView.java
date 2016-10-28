package com.jinwei.tvgame2048.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jinwei.tvgame2048.R;
import com.jinwei.tvgame2048.model.FlyNumber;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Jinwei on 2016/10/27.
 */
public class StartAniSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private final int textSize = 60;
    private final int bgColor = Color.BLACK;
    private final int aniCount = 300;
    private final int forbidArea = 200;
    private final int gameNameSize = 300;
    private final int cornerRadius = 20;
    private Handler mHandler;
    public interface AniOverListener{
        public void onAniOver();
    }
    public void setHandler(Handler handler){
        mHandler = handler;
    }
    AniOverListener mListener;
    public StartAniSurfaceView(Context context) {
        super(context);
    }

    public StartAniSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void init(){
        getHolder().addCallback(this);
    }
    public void setAniOverListener(AniOverListener listener){
        mListener = listener;
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        doDraw(holder,paint);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    public void getBesselFlayNumber(FlyNumber number,float gatT){
        number.t += gatT;
        double x = (1-number.t)*(1-number.t)*number.start.x +2*(1-number.t)*number.t*number.control.x + number.t*number.t*number.end.x;
        double y = (1-number.t)*(1-number.t)*number.start.y +2*(1-number.t)*number.t*number.control.y + number.t*number.t*number.end.y;
        number.cur.x = (int)x;
        number.cur.y = (int)y;
    }

    public void drawOneFlyNumber(Canvas canvas, Paint paint, FlyNumber number){
        paint.setTextSize(number.textSize);
        paint.setColor(number.clolor);
        canvas.drawText(String.valueOf(number.number),number.cur.x,number.cur.y,paint);
    }
    public void doDraw(final SurfaceHolder holder,final Paint paint){
        new Thread(new Runnable() {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.game_name);
            @Override
            public void run() {
                ArrayList<FlyNumber> arrayList=new ArrayList<>();
                drawFlayNumbersBessel(holder,paint,arrayList,aniCount);
                drawGameName(holder,paint);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mListener!=null){
                            mListener.onAniOver();
                        }
                    }
                },2000);

            }
        }).start();
    }
    int numbers[] = {2,0,4,8};
    public FlyNumber generateRandomNumber(){
        FlyNumber number = new FlyNumber();
        Random random = new Random();
        number.setStart(getWidth()/2,getHeight());
        number.setControl(getWidth()/2,0);
        int endX = random.nextInt(getWidth());
        int endY = random.nextInt(getHeight());
        if(endY>getHeight()/2){
            if (endX>forbidArea&&endX<getWidth()/2){
                endX -= forbidArea;
            }else if(endX>getWidth()/2 && endX<getWidth()-forbidArea){
                endX+=forbidArea;
            }
        }
        number.setEnd(endX,endY);
        number.t = 0;
        number.number = numbers[random.nextInt(4)];
        number.clolor = Color.rgb(random.nextInt(256),random.nextInt(256),random.nextInt(256));
        number.textSize = random.nextInt(textSize);
        return number;
    }

    final int MAX = 100;
    public void drawFlayNumbersBessel(SurfaceHolder holder,Paint paint,ArrayList<FlyNumber> arrayList,final int count){
        int countl = 0;
        final float gapT = 1.0f/MAX;
        for(int i=0;i<100;i++){
            arrayList.add(generateRandomNumber());
        }
        Canvas canvas;
        FlyNumber number;
        while (countl++<(count)){
            canvas = holder.lockCanvas();
            if(countl<count-MAX){
                arrayList.add(generateRandomNumber());
                arrayList.add(generateRandomNumber());
            }
            if(canvas != null){
                canvas.drawColor(Color.WHITE);
                paint.setColor(bgColor);
                canvas.drawRoundRect(0,0,getWidth(),getHeight(),cornerRadius,cornerRadius,paint);
                if(arrayList.size()>0){
                    Iterator<FlyNumber> iterator = arrayList.iterator();
                    while (iterator.hasNext()){
                        number = iterator.next();
                        if(number.t>=1.0f){
                            iterator.remove();
                        }
                        getBesselFlayNumber(number,gapT);
                        drawOneFlyNumber(canvas,paint,number);
                        int dif = count-countl;
                        if(dif<50 && dif>0){
                            drawWelComeState(canvas,paint,255-(count-countl)*3,300-(count-countl)*6);
                        }
                    }
                }
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }
    private void drawWelComeState(Canvas canvas,Paint paint,int alpha,int textSieze){
        paint.setAlpha(alpha);
        paint.setTextSize(textSieze);
        paint.setColor(Color.WHITE);
        String string = "2048";
        float width = paint.measureText(string);
        canvas.drawText(string,getWidth()/2-width/2,getHeight()/2+textSieze/3,paint);
    }
    private void drawGameName(SurfaceHolder holder,Paint paint){
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        paint.setColor(bgColor);
        canvas.drawRoundRect(0,0,getWidth(),getHeight(),cornerRadius,cornerRadius,paint);
        drawWelComeState(canvas,paint,255,gameNameSize);
        holder.unlockCanvasAndPost(canvas);
    }
}
