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
    private ProfileViewModel profileViewModel;

    public Profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initvar();
        super.onViewCreated(view, savedInstanceState);
    }

    private void initvar() {
        profileViewModel.getAccount().observe(getViewLifecycleOwner(), account -> {
            if (account != null) {
                binding.uName.setText(account.getDisplayName());
                binding.uEmail.setText(account.getEmail());
                Glide.with(getContext()).load(account.getPhotoUrl()).into(binding.profileDp);
            }
        });
        profileViewModel.loadAccount(getContext());

        logoutuser();
    }

    private void logoutuser() {
        binding.btnLogout.setOnClickListener(v -> {
            GoogleSignInClient signInClient = GoogleSignInClientInstance.getInstance(getContext());

            new AlertDialog.Builder(getActivity())
                    .setTitle("Log Out?")
                    .setMessage("Are you sure to logout from app?")
                    .setCancelable(false)
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .setPositiveButton("Yes!", (dialog, which) -> {
                        profileViewModel.signOut(getContext(), signInClient, () -> {
                            dialog.dismiss();
                            startActivity(new Intent(getActivity().getApplicationContext(), SplashActivity.class));
                            getActivity().finish();
                        });
                    }).show();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
