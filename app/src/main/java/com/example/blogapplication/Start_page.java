package com.example.blogapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Start_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_pg);

        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the home page (MainActivity)
                startActivity(new Intent(Start_page.this, SplashActivity.class));
                finish(); // Finish the SplashActivity so it's not on the back stack
            }
        });
    }
}
