package com.jinwei.tvgame2048.model;

import android.graphics.Point;

/**
 * Created by Jinwei on 2016/10/27.
 */
public class FlyNumber {
    public Point start;
    public Point control;
    public Point end;
    public Point cur;
    public float t;
    public int number;
    public int clolor;
    public int textSize;
    public FlyNumber(){
        start = new Point();
        control = new Point();
        end = new Point();
        cur = new Point();
        t=0f;
    }
    public void setStart(int x,int y){
        start.x=x;
        start.y=y;
    }
    public void setControl(int x,int y){
        control.x=x;
        control.y=y;
    }
    public void setEnd(int x,int y){
        end.x=x;
        end.y=y;
    }
}
