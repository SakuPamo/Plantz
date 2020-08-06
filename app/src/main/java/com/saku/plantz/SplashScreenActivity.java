package com.saku.plantz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    Thread splashThread;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        StartSplashScreen();
    }

    private void StartSplashScreen() {

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.alpha);
        animation.reset();
        ImageView img = findViewById(R.id.plantz_icon);
        MaterialTextView text = findViewById(R.id.icon_text);
        img.clearAnimation();
        text.clearAnimation();
        img.startAnimation(animation);
        text.startAnimation(animation);

        splashThread = new Thread() {
            @Override
            public void run() {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = mAuth.getCurrentUser();

                try {
                    int waited = 0;
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }

                    if (firebaseUser != null) {
                        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    SplashScreenActivity.this.finish();
                }
            }
        };
        splashThread.start();
    }
}