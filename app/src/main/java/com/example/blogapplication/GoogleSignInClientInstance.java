package com.example.blogapplication;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class GoogleSignInClientInstance {

    private static GoogleSignInClient instance;

    private GoogleSignInClientInstance() {}

    public static synchronized GoogleSignInClient getInstance(Context context) {
        if (instance == null) {
            GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            instance = GoogleSignIn.getClient(context, signInOptions);
        }
        return instance;
    }
}
