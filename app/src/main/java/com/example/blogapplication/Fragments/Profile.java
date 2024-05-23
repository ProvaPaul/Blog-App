package com.example.blogapplication.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.example.blogapplication.ProfileViewModel;
import com.example.blogapplication.R;
import com.example.blogapplication.SplashActivity;
import com.example.blogapplication.databinding.FragmentProfileBinding;
import com.example.blogapplication.GoogleSignInClientInstance;

public class Profile extends Fragment {

    private FragmentProfileBinding binding;

    private GoogleSignInAccount account;
    private GoogleSignInClient signInClient;


    public Profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initVar();
        super.onViewCreated(view, savedInstanceState);
    }

    private void initVar() {
        account = GoogleSignIn.getLastSignedInAccount(getContext());
        if (account != null) {
            binding.uName.setText(account.getDisplayName());
            binding.uEmail.setText(account.getEmail());
            Glide.with(getContext()).load(account.getPhotoUrl()).into(binding.profileDp);
        }


        logoutUser();
    }

    private void logoutUser() {
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Log Out?")
                        .setMessage("Are you sure to logout from app?")
                        .setCancelable(false)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut(); // logout from firebase

                                getGoogleSignInClient().signOut().addOnCompleteListener(new OnCompleteListener<Void>() { // logout from google-auth
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        dialog.dismiss();
                                        startActivity(new Intent(getActivity().getApplicationContext(), SplashActivity.class));
                                        getActivity().finish();
                                    }
                                });
                            }
                        }).show();
            }
        });
    }

    // Singleton pattern for GoogleSignInClient
    private static GoogleSignInClient signInClientInstance;

    private GoogleSignInClient getGoogleSignInClient() {
        if (signInClientInstance == null) {
            GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            signInClientInstance = GoogleSignIn.getClient(getContext(), signInOptions);
        }
        return signInClientInstance;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
