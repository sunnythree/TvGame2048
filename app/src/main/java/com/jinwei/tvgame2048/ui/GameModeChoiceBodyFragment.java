package com.jinwei.tvgame2048.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jinwei.tvgame2048.R;

import butterknife.ButterKnife;

/**
 * Created by Jinwei on 2016/10/26.
 */
public class GameModeChoiceBodyFragment extends Fragment{
    ImageView mImageView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_mode_choice_body,null);
        mImageView = ButterKnife.findById(view,R.id.fragment_body_imageview);
        return view;
    }
    public void setImageViewSrc(int mode){
        switch (mode){
            case 0:{
                mImageView.setImageResource(R.mipmap.mode3);
                break;
            }
            case 1:{
                mImageView.setImageResource(R.mipmap.mode4);
                break;
            }
            case 2:{
                mImageView.setImageResource(R.mipmap.mode5);
                break;
            }
            case 3:{
                mImageView.setImageResource(R.mipmap.mode6);
                break;
            }
        }
    }
}
