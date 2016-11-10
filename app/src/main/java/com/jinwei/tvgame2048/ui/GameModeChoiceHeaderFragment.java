package com.jinwei.tvgame2048.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.jinwei.tvgame2048.MainActivity;
import com.jinwei.tvgame2048.R;
import com.jinwei.tvgame2048.model.Game2048StaticControl;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jinwei on 2016/10/26.
 */
public class GameModeChoiceHeaderFragment extends Fragment {
    Button buttonMode3;
    Button buttonMode4;
    Button buttonMode5;
    Button buttonMode6;
    ButtonForcusChangeListener buttonForcusChangeListener = new ButtonForcusChangeListener();
    ButtonClickListener buttonClickListener = new ButtonClickListener();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_mode_choice_header,null);
        buttonMode3 = ButterKnife.findById(view,R.id.button_mode3);
        buttonMode4 = ButterKnife.findById(view,R.id.button_mode4);
        buttonMode5 = ButterKnife.findById(view,R.id.button_mode5);
        buttonMode6 = ButterKnife.findById(view,R.id.button_mode6);
        buttonMode3.setOnClickListener(buttonClickListener);
        buttonMode4.setOnClickListener(buttonClickListener);
        buttonMode5.setOnClickListener(buttonClickListener);
        buttonMode6.setOnClickListener(buttonClickListener);
        //set onForcusChangerListener
        buttonMode3.setOnFocusChangeListener(buttonForcusChangeListener);
        buttonMode4.setOnFocusChangeListener(buttonForcusChangeListener);
        buttonMode5.setOnFocusChangeListener(buttonForcusChangeListener);
        buttonMode6.setOnFocusChangeListener(buttonForcusChangeListener);
        return view;
    }
    private void goGameMainActivity(){
        Intent intent = new Intent(getActivity(),MainActivity.class);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_right_in,R.anim.anim_left_out);
    }

    private Dialog buildAskDialog(){
        Activity activity = getActivity();
        final Dialog askDialog = new Dialog(activity,R.style.CustomDialog);
        askDialog.setContentView(R.layout.ask_dialog_layout);
        Button button;
        button = (Button) askDialog.findViewById(R.id.dialog_button_yes);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game2048StaticControl.isGoBackEnabled = true;
                goGameMainActivity();
                if(askDialog != null){
                    askDialog.dismiss();
                }
            }
        });
        button = (Button) askDialog.findViewById(R.id.dialog_button_no);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game2048StaticControl.isGoBackEnabled = false;
                goGameMainActivity();
                if(askDialog != null){
                    askDialog.dismiss();
                }
            }
        });
        return askDialog;
    }

    class ButtonForcusChangeListener implements View.OnFocusChangeListener{
        GameModeChoiceBodyFragment fragment;
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            int id = v.getId();
            Log.d("hello","onFocusChange");
            fragment = (GameModeChoiceBodyFragment) GameModeChoiceHeaderFragment.this.getActivity().getFragmentManager().findFragmentById(R.id.fragment_body);
            switch (id) {
                case R.id.button_mode3: {
                    if(hasFocus){
                        fragment.setImageViewSrc(0);
                        ((Button)v).setTextColor(Color.WHITE);
                    }else{
                        ((Button)v).setTextColor(Color.parseColor("#76454545"));
                    }
                    break;
                }
                case R.id.button_mode4: {
                    if(hasFocus){
                        fragment.setImageViewSrc(1);
                        ((Button)v).setTextColor(Color.WHITE);
                    }else{
                        ((Button)v).setTextColor(Color.parseColor("#76454545"));
                    }
                    break;
                }
                case R.id.button_mode5: {
                    if(hasFocus){
                        fragment.setImageViewSrc(2);
                        ((Button)v).setTextColor(Color.WHITE);
                    }else{
                        ((Button)v).setTextColor(Color.parseColor("#76454545"));
                    }
                    break;
                }
                case R.id.button_mode6: {
                    if(hasFocus){
                        fragment.setImageViewSrc(3);
                        ((Button)v).setTextColor(Color.WHITE);
                    }else{
                        ((Button)v).setTextColor(Color.parseColor("#76454545"));
                    }
                    break;
                }
            }
        }
    }
    class ButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.button_mode3: {
                    Game2048StaticControl.gamePlayMode = 3;
                    buildAskDialog().show();
                    break;
                }
                case R.id.button_mode4: {
                    Game2048StaticControl.gamePlayMode = 4;
                    buildAskDialog().show();
                    break;
                }
                case R.id.button_mode5: {
                    Game2048StaticControl.gamePlayMode = 5;
                    buildAskDialog().show();
                    break;
                }
                case R.id.button_mode6: {
                    Game2048StaticControl.gamePlayMode = 6;
                    buildAskDialog().show();
                    break;
                }
            }
        }
    }
}
