package com.jinwei.tvgame2048.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.jinwei.tvgame2048.MainActivity;
import com.jinwei.tvgame2048.R;
import com.jinwei.tvgame2048.model.Game2048StaticControl;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GameSettingsActivity extends Activity {
    @Bind(R.id.toggle_button_sound) Switch mSwitch;
    @Bind(R.id.button_back) Button mButtonBack;
    @Bind(R.id.button_restart) Button mButtonRestart;
    @Bind(R.id.button_exit) Button mButtonExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);
        ButterKnife.bind(this);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Game2048StaticControl.isGameSoundOn = true;
                }else {
                    Game2048StaticControl.isGameSoundOn = false;
                }
            }
        });
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.game2048.go.back");
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
