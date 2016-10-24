package com.jinwei.tvgame2048;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.widget.TextView;

import com.jinwei.tvgame2048.model.Game2048StaticControl;
import com.jinwei.tvgame2048.presenter.MainPresenter;
import com.jinwei.tvgame2048.ui.GameSettingsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity {
    @Bind(R.id.game_surface_view) SurfaceView mGameSurfaceView;
    @Bind(R.id.score_text_view) TextView mCurScoresTextView;
    MainPresenter mainPresenter;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Game2048StaticControl.UPDATE_CURRENT_HISTORY_SCORES:{
                    mCurScoresTextView.setText(String.valueOf(Game2048StaticControl.gameCurrentScores));
                    break;
                }
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainPresenter = new MainPresenter(this);
        mainPresenter.initGame(mGameSurfaceView,mHandler);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_MENU){
            goGameSettingActivity();
        }
        return super.onKeyDown(keyCode, event);
    }
    private void goGameSettingActivity(){
        Intent intent = new Intent(MainActivity.this,GameSettingsActivity.class);
        MainActivity.this.startActivity(intent);
        overridePendingTransition(R.anim.anim_right_in,R.anim.anim_left_out);
    }

}
