package com.jinwei.tvgame2048.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.jinwei.tvgame2048.MainActivity;
import com.jinwei.tvgame2048.R;
import com.jinwei.tvgame2048.algorithm.Game2048Algorithm;
import com.jinwei.tvgame2048.model.Game2048StaticControl;

/**
 * Created by Jinwei on 2016/10/18.
 */
public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Game2048Algorithm.GameResultListener{
    private final String TAG = "GameSurfaceView";
    GameSurfaceViewHelper mGSVH ;
    Handler mHandler;
    Context mContext;
    @Override
    public void onGameOver() {
        Log.d(TAG,"onGameOver");
        mGSVH.gameOver();
        buildAskDialog().show();
    }

    @Override
    public void onGameVictory() {
        Log.d(TAG,"onGameVictory");
        mGSVH.gameVictory();
        buildWinDialog().show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_UP:{
                mGSVH.upKeyUpdate();
                return true;
            }
            case KeyEvent.KEYCODE_DPAD_DOWN:{
                mGSVH.downKeyUpdate();
                return true;
            }
            case KeyEvent.KEYCODE_DPAD_LEFT:{
                mGSVH.leftKeyUpdate();
                return true;
            }
            case KeyEvent.KEYCODE_DPAD_RIGHT:{
                mGSVH.rightKeyUpdate();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG,"surfaceCreated");
        Game2048StaticControl.gameSurfaceLength = getWidth();//width==height
        Log.d(TAG,"Game2048StaticControl.gameSurfaceLength:"+Game2048StaticControl.gameSurfaceLength);

        //gameGapBetweenNumberViews:gameSurfaceViewPadding:gameNumberViewLength=1:1:6
        int all=Game2048StaticControl.gamePlayMode*6+ Game2048StaticControl.gamePlayMode-1+2;
        Game2048StaticControl.gameGapBetweenNumberViews = Game2048StaticControl.gameSurfaceLength/all;
        Game2048StaticControl.gameSurfaceViewPadding = Game2048StaticControl.gameSurfaceLength/all;
        Game2048StaticControl.gameNumberViewLength = Game2048StaticControl.gameSurfaceLength/all*6;
        Game2048StaticControl.GameNumberViewPosition = Game2048StaticControl.initGameNumberViewPosition();
        Log.d(TAG,"Game2048StaticControl.gameSurfaceLength:"+Game2048StaticControl.gameSurfaceLength);
        Log.d(TAG,"Game2048StaticControl.gameGapBetweenNumberViews:"+Game2048StaticControl.gameGapBetweenNumberViews);
        Log.d(TAG,"Game2048StaticControl.gameSurfaceViewPadding:"+Game2048StaticControl.gameSurfaceViewPadding);
        Log.d(TAG,"Game2048StaticControl.gameNumberViewLength:"+Game2048StaticControl.gameNumberViewLength);
        mGSVH = new GameSurfaceViewHelper(mContext,holder,mHandler);
        registListener();
        mGSVH.init();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG,"surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG,"surfaceDestroyed");
        mGSVH.exit();
    }
    public void init(Handler handler){
        mHandler = handler;
        getHolder().addCallback(this);
    }
    public void registListener(){
        mGSVH.registListener(GameSurfaceView.this);
    }
    Dialog winDialog;
    private Dialog buildWinDialog(){
        winDialog = new Dialog(mContext,R.style.CustomDialog);
        winDialog.setContentView(R.layout.dialog_game_win);
        TextView textView = (TextView) winDialog.findViewById(R.id.ask_text);
        textView.setText(mContext.getString(R.string.win_game));
        Button button;
        button = (Button) winDialog.findViewById(R.id.button_continue);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Game2048StaticControl.gameHasWin = false;
                if(winDialog!=null){
                    winDialog.dismiss();
                }
            }
        });
        button = (Button) winDialog.findViewById(R.id.button_restart);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.game2048.restart");
                mContext.sendBroadcast(intent);
                if(winDialog!=null){
                    winDialog.dismiss();
                }
            }
        });
        button = (Button) winDialog.findViewById(R.id.button_share);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                shareGame2048();
                if(winDialog!=null){
                    winDialog.dismiss();
                }
            }
        });
        return winDialog;
    }
    private void shareGame2048(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        String text = null;
        if(Game2048StaticControl.gamePlayMode==3){
            text = mContext.getString(R.string.text_mode3);
        }else if(Game2048StaticControl.gamePlayMode==4){
            text = mContext.getString(R.string.text_mode4);
        }else if(Game2048StaticControl.gamePlayMode==5){
            text = mContext.getString(R.string.text_mode5);
        }else if(Game2048StaticControl.gamePlayMode==6){
            text = mContext.getString(R.string.text_mode6);
        }
        sendIntent.putExtra(Intent.EXTRA_TEXT, "I got "+Game2048StaticControl.gameCurrentScores+" scores with " +
                text+"mode in 2048 game,Do you want to have a try?" );
        sendIntent.setType("text/plain");
        mContext.startActivity(Intent.createChooser(sendIntent, "Share to ..."));
    }
    Dialog askDialog;
    private Dialog buildAskDialog(){
        askDialog = new Dialog(mContext,R.style.CustomDialog);
        askDialog.setContentView(R.layout.ask_dialog_layout);
        TextView textView = (TextView) askDialog.findViewById(R.id.ask_text);
        textView.setText(mContext.getString(R.string.lost_game));
        Button button;
        button = (Button) askDialog.findViewById(R.id.dialog_button_yes);
        button.setText(mContext.getString(R.string.game_setting_restart));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.game2048.restart");
                mContext.sendBroadcast(intent);
                if(askDialog != null){
                    askDialog.dismiss();
                }
            }
        });
        button = (Button) askDialog.findViewById(R.id.dialog_button_no);
        button.setText(mContext.getString(R.string.game_setting_go_home));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,GameModeChoiceActivity.class);
                mContext.startActivity(intent);
                ((Activity)mContext).overridePendingTransition(R.anim.anim_left_in,R.anim.anim_right_out);
                if(askDialog != null){
                    askDialog.dismiss();
                }
            }
        });
        return askDialog;
    }

}
