package com.example.menno_000.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Receive data from previous screen
        Intent intent = getIntent();

        // Open data from previous screen
        String title = intent.getStringExtra("title");
        String timestamp = intent.getStringExtra("timestamp");
        String content = intent.getStringExtra("content");
        String mood = intent.getStringExtra("mood");

        // Initialise Views
        TextView titleView  = findViewById(R.id.detail_title);
        TextView dateView  = findViewById(R.id.detail_date);
        TextView contentView  = findViewById(R.id.detail_content);
        ImageView moodView = findViewById(R.id.detail_image);

        // Set data in Views
        titleView.setText(title);
        dateView.setText(timestamp);
        contentView.setText(content);
        switch(mood) {
            case "happy":
                moodView.setImageResource(R.drawable.happy);
                break;
            case "neutral":
                moodView.setImageResource(R.drawable.neutral);
                break;
            case "tired":
                moodView.setImageResource(R.drawable.tired);
                break;
            case "sad":
                moodView.setImageResource(R.drawable.sad);
                break;
        }
    }
}
