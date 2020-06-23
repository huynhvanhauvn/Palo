package com.sbro.palo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.sbro.palo.Activities.LoginActivity.LoginActivity;
import com.sbro.palo.Activities.MainActivity.MainActivity;
import com.sbro.palo.R;

public class WelcomeActivity extends AppCompatActivity {

    public static final String SHARED_DATA = "MyData";
    public static final String USER_NAME = "username";
    public static final String PASSWORD = "password";
    public static final String USER_ID = "userid";
    public static final String IS_LOGGED = "isLogged";
    private LottieAnimationView imgLogo;
    private static int SPLASH_TIME_OUT = 3000;
    private Animation anim_FadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        getSupportActionBar().hide();

        imgLogo = (LottieAnimationView) findViewById(R.id.imageviewLogoWelcome);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE);
                boolean isLogged = sharedPref.getBoolean(IS_LOGGED,false);
                if(isLogged) {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (!isLogged) {
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_TIME_OUT);

        Animation();
        imgLogo.startAnimation(anim_FadeIn);
    }

    private void Animation(){
        imgLogo.setVisibility(View.VISIBLE);
        anim_FadeIn = AnimationUtils.loadAnimation(this,R.anim.anim_fadein);
        anim_FadeIn.setDuration(1500);
    }
}
