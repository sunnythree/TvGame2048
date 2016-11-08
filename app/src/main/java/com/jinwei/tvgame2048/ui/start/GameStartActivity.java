package com.jinwei.tvgame2048.ui.start;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jinwei.tvgame2048.R;
import com.jinwei.tvgame2048.ui.GameSurfaceView;

public class GameStartActivity extends Activity {
    GameStartSurfaceView surfaceView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);
        surfaceView = (GameStartSurfaceView) findViewById(R.id.game_start_surfaceview);
        surfaceView.init();
    }
}
