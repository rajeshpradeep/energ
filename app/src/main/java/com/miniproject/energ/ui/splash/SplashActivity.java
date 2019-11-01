package com.miniproject.energ.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.miniproject.energ.R;
import com.miniproject.energ.ui.base.BaseActivity;
import com.miniproject.energ.ui.login.LoginActivity;
import com.miniproject.energ.utils.Constant;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removeStatusBar();
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                SplashActivity.this.finish();
            }
        }, Constant.SPLASH_TIME_OUT); // SPLASH_TIME_OUT 2 seconds
    }
}