package com.jinwei.tvgame2048.ui;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.jinwei.tvgame2048.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity implements StartAniSurfaceView.AniOverListener{
    @Bind(R.id.start_ani_surfaceview)
    StartAniSurfaceView surfaceView;
    @Bind(R.id.image_view_progress)
    ImageView imageView;
    @Override
    public void onAniOver() {
        Intent intent = new Intent(this,GameModeChoiceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_right_in,R.anim.anim_left_out);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        surfaceView.setHandler(mHandler);
        surfaceView.init();
        surfaceView.setAniOverListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus){
            Animation animation = AnimationUtils.loadAnimation(this,R.anim.imageview_progress_ani);
            imageView.startAnimation(animation);
        }
        super.onWindowFocusChanged(hasFocus);
    }
}
