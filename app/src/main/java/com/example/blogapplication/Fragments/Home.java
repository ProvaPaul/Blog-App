package com.example.blogapplication.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blogapplication.Adapter;
import com.example.blogapplication.BlogViewModel;
import com.example.blogapplication.Model;
import com.example.blogapplication.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class Home extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayList<Model> list;
    private Adapter adapter;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRv();
        setusearchview();
    }

    private void setusearchview() {
        binding.searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }

    private void filter(String newText) {
        ArrayList<Model> filtered_list = new ArrayList<>();
        for (Model item : list) {
            if (item.getTittle().toLowerCase().contains(newText)) {
                filtered_list.add(item);
            }
        }
        if (!filtered_list.isEmpty()) {
            adapter.filter_list(filtered_list);
        }
    }

    private void setupRv() {
        list = new ArrayList<>();
        adapter = new Adapter(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        binding.rvBlogs.setLayoutManager(linearLayoutManager);
        binding.rvBlogs.setAdapter(adapter);

        BlogViewModel viewModel = new ViewModelProvider(this).get(BlogViewModel.class);
        viewModel.getBlogs().observe(getViewLifecycleOwner(), models -> {
            list.clear();
            list.addAll(models);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
