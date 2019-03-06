package com.argb.mtgapp.signin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.argb.mtgapp.BaseActivity;
import com.argb.mtgapp.MainActivity;
import com.argb.mtgapp.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

import androidx.annotation.NonNull;

public class SignInActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = SignInActivity.class.getName();
    private static final int GOOGLE_SIGN_IN = 8001;

    private FirebaseAuth mFirebaseAuth;

    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mFacebookCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        hideToolbar();

        mFirebaseAuth = FirebaseAuth.getInstance();

        // Initialize Google Login button
        findViewById(R.id.google_sign_in_button).setOnClickListener(this);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_id_token))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        // Initialize Facebook Login button
        findViewById(R.id.facebook_sign_in_button).setOnClickListener(this);
        mFacebookCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                // Facebook Sign In was successful, authenticate with Firebase
                firebaseAuthWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.w(TAG, "facebook:onError", error);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_sign_in_button:
                signInWithGoogle();
                break;
            case R.id.facebook_sign_in_button:
                signInWithFacebook();
                break;
        }
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    private void signInWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
            mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        showProgressDialog();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, onSignInWitchCredentialCompleteListener);
    }

    private void firebaseAuthWithFacebook(AccessToken token) {
        showProgressDialog();

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, onSignInWitchCredentialCompleteListener);
    }

    private OnCompleteListener<AuthResult> onSignInWitchCredentialCompleteListener = new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                Log.d(TAG, "signInWithCredential:success");
                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                updateUI(user);
            } else {
                Exception exception = task.getException();
                Log.w(TAG, "signInWithCredential:failure", exception);
                if (exception instanceof FirebaseAuthUserCollisionException) {
                    Snackbar.make(findViewById(android.R.id.content),"FirebaseAuthUserCollisionException", Snackbar.LENGTH_SHORT).show();
                }
            }

            hideProgressDialog();
        }
    };

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
