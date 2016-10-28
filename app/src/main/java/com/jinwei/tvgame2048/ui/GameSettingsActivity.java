package com.jinwei.tvgame2048.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.jinwei.tvgame2048.MainActivity;
import com.jinwei.tvgame2048.R;
import com.jinwei.tvgame2048.model.Game2048StaticControl;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GameSettingsActivity extends Activity {
    @Bind(R.id.game_settings_sound) LinearLayout soundLayout;
    @Bind(R.id.game_settings_go_back) LinearLayout goBackLayout;
    @Bind(R.id.game_settings_go_home) LinearLayout goHomeLayout;
    @Bind(R.id.game_settings_restart) LinearLayout restartLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);
        ButterKnife.bind(this);

        soundLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Game2048StaticControl.isGameSoundOn){
                    Game2048StaticControl.isGameSoundOn = false;
                    SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(GameSettingsActivity.this);
                    SharedPreferences.Editor  editor  =  preference.edit();
                    editor.putBoolean("isSoundOn",false);
                    editor.commit();
                }else {
                    Game2048StaticControl.isGameSoundOn = true;
                    SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(GameSettingsActivity.this);
                    SharedPreferences.Editor  editor  =  preference.edit();
                    editor.putBoolean("isSoundOn",true);
                    editor.commit();
                }
            }
        });
        if(!Game2048StaticControl.isGoBackEnabled || Game2048StaticControl.gameHasFail || Game2048StaticControl.gameHasWin){
            goBackLayout.setEnabled(false);
        }
        goBackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Game2048StaticControl.isGoBackEnabled){
                    Intent intent = new Intent();
                    intent.setAction("com.game2048.go.back");
                    GameSettingsActivity.this.sendBroadcast(intent);
                }
            }
        });
        goHomeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameSettingsActivity.this,GameModeChoiceActivity.class);
                GameSettingsActivity.this.startActivity(intent);
                overridePendingTransition(R.anim.anim_left_in,R.anim.anim_right_out);
            }
        });
        restartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.game2048.restart");
                GameSettingsActivity.this.sendBroadcast(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_right_in,R.anim.anim_right_out);
    }
}
