package com.jinwei.tvgame2048.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jinwei.tvgame2048.MainActivity;
import com.jinwei.tvgame2048.R;
import com.jinwei.tvgame2048.model.Game2048StaticControl;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GameGoBackModeActivity extends Activity {
    @Bind(R.id.allow_goback) Button buttonCanGoBack;
    @Bind(R.id.forbid_goback) Button buttonCanNotGoBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.go_back_choice);
        ButterKnife.bind(this);
        buttonCanGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //game mode
                Game2048StaticControl.isGoBackEnabled = true;
                goMainActivity();
            }
        });
        buttonCanNotGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //game mode
                Game2048StaticControl.isGoBackEnabled = false;
                goMainActivity();
            }
        });
    }
    private void goMainActivity(){
        Intent intent = new Intent(GameGoBackModeActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        GameGoBackModeActivity.this.startActivity(intent);
        overridePendingTransition(R.anim.anim_right_in,R.anim.anim_left_out);
    }
}
