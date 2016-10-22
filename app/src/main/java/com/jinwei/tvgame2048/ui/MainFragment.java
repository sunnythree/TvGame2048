package com.jinwei.tvgame2048.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinwei.tvgame2048.R;
import com.jinwei.tvgame2048.model.Game2048StaticControl;
import com.jinwei.tvgame2048.presenter.MainPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jinwei on 2016/10/22.
 */
public class MainFragment extends Fragment {
    SurfaceView mGameSurfaceView;
    TextView mCurScoresTextView;
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,container,false);
        mGameSurfaceView = ButterKnife.findById(view,R.id.game_surface_view);
        mCurScoresTextView = ButterKnife.findById(view,R.id.score_text_view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        mainPresenter = new MainPresenter(getActivity());
        mainPresenter.initGame(mGameSurfaceView,mHandler);
        super.onActivityCreated(savedInstanceState);
    }
}
