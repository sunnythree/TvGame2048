package com.jinwei.tvgame2048.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

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
    @Bind(R.id.game_settings_share) LinearLayout shareLayout;
    @Bind(R.id.image_view_after_back) ImageView imageViewAfterBack;
    MineViewGroupOnClickListener clickListener = new MineViewGroupOnClickListener();
    MineViewGroupOnFocusChangerListener focusChangerListener = new MineViewGroupOnFocusChangerListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);
        ButterKnife.bind(this);
        if(!Game2048StaticControl.isGoBackEnabled || Game2048StaticControl.gameHasFail || Game2048StaticControl.gameHasWin){
            goBackLayout.setVisibility(View.GONE);
            imageViewAfterBack.setVisibility(View.GONE);
        }
        soundLayout.setOnClickListener(clickListener);
        goBackLayout.setOnClickListener(clickListener);
        goHomeLayout.setOnClickListener(clickListener);
        restartLayout.setOnClickListener(clickListener);
        shareLayout.setOnClickListener(clickListener);

        soundLayout.setOnFocusChangeListener(focusChangerListener);
        goBackLayout.setOnFocusChangeListener(focusChangerListener);
        goHomeLayout.setOnFocusChangeListener(focusChangerListener);
        restartLayout.setOnFocusChangeListener(focusChangerListener);
        shareLayout.setOnFocusChangeListener(focusChangerListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_right_in,R.anim.anim_right_out);
    }
    class MineViewGroupOnFocusChangerListener implements View.OnFocusChangeListener{
        ImageView imageView = null;
        TextView textView = null;
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            int id = v.getId();
            switch (id){
                case R.id.game_settings_sound:{
                    imageView = (ImageView) v.findViewById(R.id.imageview_sound);
                    textView = (TextView) v.findViewById(R.id.textview_sound);
                    if(hasFocus){
                        if(Game2048StaticControl.isGameSoundOn){
                            imageView.setImageResource(R.mipmap.game_sound_on_forcus);
                        }else {
                            imageView.setImageResource(R.mipmap.game_sound_off_focus);
                        }
                        textView.setTextColor(Color.WHITE);
                    }else{
                        if(Game2048StaticControl.isGameSoundOn){
                            imageView.setImageResource(R.mipmap.game_sound_on_unforcus);
                        }else {
                            imageView.setImageResource(R.mipmap.game_sound_off_unforcus);
                        }
                        textView.setTextColor(Color.parseColor("#76454545"));
                    }
                    break;
                }
                case R.id.game_settings_go_back:{
                    imageView = (ImageView) v.findViewById(R.id.imageview_back);
                    textView = (TextView) v.findViewById(R.id.textview_back);
                    if (hasFocus){
                        imageView.setImageResource(R.mipmap.game_back_forcus);
                        textView.setTextColor(Color.WHITE);
                    }else {
                        imageView.setImageResource(R.mipmap.game_back_unforcus);
                        textView.setTextColor(Color.parseColor("#76454545"));
                    }
                    break;
                }
                case R.id.game_settings_go_home:{
                    imageView = (ImageView) v.findViewById(R.id.imageview_home);
                    textView = (TextView) v.findViewById(R.id.textview_home);
                    if (hasFocus){
                        imageView.setImageResource(R.mipmap.game_home_focus);
                        textView.setTextColor(Color.WHITE);
                    }else {
                        imageView.setImageResource(R.mipmap.game_home_unfocus);
                        textView.setTextColor(Color.parseColor("#76454545"));
                    }
                    break;
                }
                case R.id.game_settings_restart:{
                    imageView = (ImageView) v.findViewById(R.id.imageview_restart);
                    textView = (TextView) v.findViewById(R.id.textview_restart);
                    if (hasFocus){
                        imageView.setImageResource(R.mipmap.game_restart_forcus);
                        textView.setTextColor(Color.WHITE);
                    }else {
                        imageView.setImageResource(R.mipmap.game_restart_unforcus);
                        textView.setTextColor(Color.parseColor("#76454545"));
                    }
                    break;
                }
                case R.id.game_settings_share:{
                    imageView = (ImageView) v.findViewById(R.id.imageview_share);
                    textView = (TextView) v.findViewById(R.id.textview_share);
                    if (hasFocus){
                        imageView.setImageResource(R.mipmap.game_share_forcus);
                        textView.setTextColor(Color.WHITE);
                    }else {
                        imageView.setImageResource(R.mipmap.game_share_unforcus);
                        textView.setTextColor(Color.parseColor("#76454545"));
                    }
                    break;
                }
            }
        }
    }
    class MineViewGroupOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id){
                case R.id.game_settings_sound:{
                    ImageView  imageView = (ImageView) v.findViewById(R.id.imageview_sound);
                    if(Game2048StaticControl.isGameSoundOn){
                        Game2048StaticControl.isGameSoundOn = false;
                        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(GameSettingsActivity.this);
                        SharedPreferences.Editor  editor  =  preference.edit();
                        editor.putBoolean("isSoundOn",false);
                        editor.commit();
                        imageView.setImageResource(R.mipmap.game_sound_off_focus);
                    }else {
                        Game2048StaticControl.isGameSoundOn = true;
                        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(GameSettingsActivity.this);
                        SharedPreferences.Editor  editor  =  preference.edit();
                        editor.putBoolean("isSoundOn",true);
                        editor.commit();
                        imageView.setImageResource(R.mipmap.game_sound_on_forcus);
                    }
                    break;
                }
                case R.id.game_settings_go_back:{
                    if(Game2048StaticControl.isGoBackEnabled){
                        Intent intent = new Intent();
                        intent.setAction("com.game2048.go.back");
                        GameSettingsActivity.this.sendBroadcast(intent);
                    }
                    break;
                }
                case R.id.game_settings_go_home:{
                    Intent intent = new Intent(GameSettingsActivity.this,GameModeChoiceActivity.class);
                    GameSettingsActivity.this.startActivity(intent);
                    overridePendingTransition(R.anim.anim_left_in,R.anim.anim_right_out);
                    break;
                }
                case R.id.game_settings_restart:{
                    Intent intent = new Intent();
                    intent.setAction("com.game2048.restart");
                    GameSettingsActivity.this.sendBroadcast(intent);
                    break;
                }
                case R.id.game_settings_share:{
                    shareGame2048();
                    break;
                }
            }
        }
    }

    private void shareGame2048(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "I am playing 2048 game,do you want to play it?");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share to ..."));
    }
}
