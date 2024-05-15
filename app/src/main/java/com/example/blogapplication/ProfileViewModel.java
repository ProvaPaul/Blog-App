package com.example.blogapplication;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<GoogleSignInAccount> accountLiveData;

    public ProfileViewModel() {
        accountLiveData = new MutableLiveData<>();
    }

    public LiveData<GoogleSignInAccount> getAccount() {
        return accountLiveData;
    }

    public void loadAccount(Context context) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        accountLiveData.setValue(account);
    }

    public void signOut(Context context, GoogleSignInClient signInClient, Runnable onComplete) {
        FirebaseAuth.getInstance().signOut(); // logout from firebase
        signInClient.signOut().addOnCompleteListener(task -> onComplete.run());
    }
}
