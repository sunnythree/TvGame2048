package com.jinwei.tvgame2048.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jinwei.tvgame2048.MainActivity;
import com.jinwei.tvgame2048.R;
import com.jinwei.tvgame2048.model.Game2048StaticControl;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jinwei on 2016/10/24.
 */
public class GameModeChoiceActivity extends Activity {
    @Bind(R.id.button_mode3) Button buttonMode3;
    @Bind(R.id.button_mode4) Button buttonMode4;
    @Bind(R.id.button_mode5) Button buttonMode5;
    @Bind(R.id.button_mode6) Button buttonMode6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_mode_choice);
        ButterKnife.bind(this);
        buttonMode3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //game mode
                Game2048StaticControl.gamePlayMode = 3;
                goGameBackModeActivity();
            }
        });
        buttonMode4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //game mode
                Game2048StaticControl.gamePlayMode = 4;
                goGameBackModeActivity();
            }
        });
        buttonMode5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //game mode
                Game2048StaticControl.gamePlayMode = 5;
                goGameBackModeActivity();
            }
        });
        buttonMode6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //game mode
                Game2048StaticControl.gamePlayMode = 6;
                goGameBackModeActivity();
            }
        });
    }
    private void goGameBackModeActivity(){
        Intent intent = new Intent(GameModeChoiceActivity.this,GameGoBackModeActivity.class);
        GameModeChoiceActivity.this.startActivity(intent);
        overridePendingTransition(R.anim.anim_right_in,R.anim.anim_left_out);
    }
    Dialog exitDialog;
    @Override
    public void onBackPressed() {
        exitDialog = new Dialog(GameModeChoiceActivity.this,R.style.CustomDialog);
        exitDialog.setContentView(R.layout.dialog_game_exit);
        Button button;
        button = (Button) exitDialog.findViewById(R.id.button_restart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.game2048.restart");
                GameModeChoiceActivity.this.sendBroadcast(intent);
                if(exitDialog != null){
                    exitDialog.dismiss();
                }
            }
        });
        button = (Button) exitDialog.findViewById(R.id.button_exit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exitDialog != null){
                    exitDialog.dismiss();
                }
                finish();
            }
        });
        exitDialog.show();
    }
}
