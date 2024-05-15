package com.example.blogapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.blogapplication.databinding.ActivityBlogDetailBinding;
import com.google.firebase.firestore.DocumentSnapshot;

public class BlogDetail extends AppCompatActivity {
    private ActivityBlogDetailBinding binding;
    private String id;
    private String title, desc;
    private int n_count;
    private BlogDetailViewModel blogDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlogDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        blogDetailViewModel = new ViewModelProvider(this).get(BlogDetailViewModel.class);
        showData();
    }

    private void showData() {
        id = getIntent().getStringExtra("id");
        blogDetailViewModel.loadBlog(id);

        blogDetailViewModel.getBlog().observe(this, value -> {
            if (value != null) {
                Glide.with(getApplicationContext()).load(value.getString("img")).into(binding.imageView3);
                binding.textView4.setText(Html.fromHtml("<font color='B7B7B7'>By </font> <font color='#000000'>" + value.getString("author") + "</font>"));
                binding.textView5.setText(value.getString("tittle"));
                binding.textView6.setText(value.getString("desc"));
                title = value.getString("tittle");
                desc = value.getString("desc");
                int i_count = Integer.parseInt(value.getString("share_count"));
                n_count = i_count + 1;
            }
        });

        binding.floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            String shareBody = desc;
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, title);
            intent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(intent, "Share Using"));

            blogDetailViewModel.updateShareCount(id, n_count);
        });

        binding.imageView4.setOnClickListener(v -> onBackPressed());
    }
}
