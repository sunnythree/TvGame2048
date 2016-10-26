package com.jinwei.tvgame2048.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_mode_choice_header,null);
        buttonMode3 = ButterKnife.findById(view,R.id.button_mode3);
        buttonMode4 = ButterKnife.findById(view,R.id.button_mode4);
        buttonMode5 = ButterKnife.findById(view,R.id.button_mode5);
        buttonMode6 = ButterKnife.findById(view,R.id.button_mode6);
        buttonMode3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //game mode
                Game2048StaticControl.gamePlayMode = 3;
                buildAskDialog().show();
            }
        });
        buttonMode4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //game mode
                Game2048StaticControl.gamePlayMode = 4;
                buildAskDialog().show();
            }
        });
        buttonMode5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //game mode
                Game2048StaticControl.gamePlayMode = 5;
                buildAskDialog().show();
            }
        });
        buttonMode6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //game mode
                Game2048StaticControl.gamePlayMode = 6;
                buildAskDialog().show();
            }
        });
        buttonMode3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    GameModeChoiceBodyFragment fragment = (GameModeChoiceBodyFragment)getFragmentManager().findFragmentById(R.id.fragment_body);
                    fragment.setImageViewSrc(0);
                }
            }
        });
        //set onForcusChangerListener
        buttonMode4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    GameModeChoiceBodyFragment fragment = (GameModeChoiceBodyFragment) getFragmentManager().findFragmentById(R.id.fragment_body);
                    fragment.setImageViewSrc(1);
                }
            }
        });
        buttonMode5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    GameModeChoiceBodyFragment fragment = (GameModeChoiceBodyFragment) getFragmentManager().findFragmentById(R.id.fragment_body);
                    fragment.setImageViewSrc(2);
                }
            }
        });
        buttonMode6.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    GameModeChoiceBodyFragment fragment = (GameModeChoiceBodyFragment) getFragmentManager().findFragmentById(R.id.fragment_body);
                    fragment.setImageViewSrc(3);
                }
            }
        });
        return view;
    }
    private void goGameMainActivity(){
        Intent intent = new Intent(getActivity(),MainActivity.class);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_right_in,R.anim.anim_left_out);
    }
    Dialog askDialog;
    private Dialog buildAskDialog(){
        Activity activity = getActivity();
        askDialog = new Dialog(activity,R.style.CustomDialog);
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
}
