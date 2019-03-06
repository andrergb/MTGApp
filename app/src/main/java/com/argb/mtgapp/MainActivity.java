package com.argb.mtgapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.argb.mtgapp.gameSetup.GameSetupActivity;
import com.argb.mtgapp.signIn.SignInActivity;
import com.argb.mtgapp.userInfo.UserInfoActivity;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.user_info).setOnClickListener(this);
        findViewById(R.id.game_setup).setOnClickListener(this);

        // Configure sign-in to request the user's ID, email address, and basic profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_id_token))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.user_info:
                startActivity(new Intent(MainActivity.this, UserInfoActivity.class));
                break;
            case R.id.game_setup:
                startActivity(new Intent(MainActivity.this, GameSetupActivity.class));
                break;
        }
    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });

        // Facebook sign out
        LoginManager.getInstance().logOut();
    }

    private void updateUI(FirebaseUser user) {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }
}
