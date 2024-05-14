package com.example.blogapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashViewModel extends AndroidViewModel {

    private final FirebaseAuth auth;
    private final GoogleSignInClient googleSignInClient;

    public SplashViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
        googleSignInClient = GoogleSignInClientInstance.getInstance(application.getApplicationContext());
    }

    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    public GoogleSignInClient getGoogleSignInClient() {
        return googleSignInClient;
    }

    public void signInWithGoogle(Activity activity, Intent signInIntent) {
        activity.startActivityForResult(signInIntent, 100);
    }

    public Task<AuthResult> signInWithGoogle(AuthCredential authCredential) {
        return auth.signInWithCredential(authCredential);
    }

    public void signOut(Runnable onComplete) {
        auth.signOut();
        googleSignInClient.signOut().addOnCompleteListener(task -> onComplete.run());
    }
}
