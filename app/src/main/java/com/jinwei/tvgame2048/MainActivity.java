package com.jinwei.tvgame2048;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jinwei.tvgame2048.model.Game2048StaticControl;
import com.jinwei.tvgame2048.presenter.MainPresenter;
import com.jinwei.tvgame2048.ui.GameModeChoiceActivity;
import com.jinwei.tvgame2048.ui.GameSettingsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity {
    @Bind(R.id.game_surface_view) SurfaceView mGameSurfaceView;
    @Bind(R.id.score_text_view) TextView mCurScoresTextView;
    @Bind(R.id.history_text_view) TextView mHighestScoresTextView;
    MainPresenter mainPresenter;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Game2048StaticControl.UPDATE_CURRENT_HISTORY_SCORES:{
                    mCurScoresTextView.setText(String.valueOf(Game2048StaticControl.gameCurrentScores));
                    mHighestScoresTextView.setText(String.valueOf(Game2048StaticControl.gameHistoryHighestScores));
                    break;
                }
                case Game2048StaticControl.EXIT_CURRENT_GAME:{
                    finish();
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
        Game2048StaticControl.gameHistoryHighestScores = PreferenceManager.getDefaultSharedPreferences(this).getInt("bestScores",0);
        mHighestScoresTextView.setText(String.valueOf(Game2048StaticControl.gameHistoryHighestScores));
    }

    @Override
    protected void onResume() {
        mainPresenter.forcusGame();
        super.onResume();
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
    @Override
    public void onBackPressed() {
        final Dialog askDialog;
        askDialog = new Dialog(this, R.style.CustomDialog);
        askDialog.setContentView(R.layout.ask_dialog_layout);
        Button button;
        TextView textView = (TextView) askDialog.findViewById(R.id.ask_text);
        textView.setText(getString(R.string.go_home));
        button = (Button) askDialog.findViewById(R.id.dialog_button_yes);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GameModeChoiceActivity.class);
                MainActivity.this.startActivity(intent);
                overridePendingTransition(R.anim.anim_right_in,R.anim.anim_left_out);
                if (askDialog != null) {
                    askDialog.dismiss();
                }
            }
        });
        button = (Button) askDialog.findViewById(R.id.dialog_button_no);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (askDialog != null) {
                    askDialog.dismiss();
                }
            }
        });
        askDialog.show();
    }
}
