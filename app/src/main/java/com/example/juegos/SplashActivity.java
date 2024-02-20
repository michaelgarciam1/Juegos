package com.example.juegos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Animation splashAnim = AnimationUtils.loadAnimation(this, R.anim.splash_animation);
        ImageView imageView = (ImageView) findViewById(R.id.game);
        imageView.startAnimation(splashAnim);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        splashAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //Iniciar la siguiente actividad
                startActivity(new Intent(SplashActivity.this, GameCenter.class));
                //finalizar esta actividad para que el usuario no pueda volver a ella
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }


}