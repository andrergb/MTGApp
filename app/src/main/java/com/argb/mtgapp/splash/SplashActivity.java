package com.argb.mtgapp.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.argb.mtgapp.BaseActivity;
import com.argb.mtgapp.MainActivity;
import com.argb.mtgapp.R;
import com.argb.mtgapp.signin.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.Nullable;

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        hideToolbar();

        final Intent activityIntent;

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            activityIntent = new Intent(this, SignInActivity.class);
        } else {
            activityIntent = new Intent(this, MainActivity.class);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(activityIntent);
                finish();
            }
        }, 2000);

    }
}
