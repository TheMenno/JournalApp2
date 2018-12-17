package com.example.menno_000.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    String title;
    String timestamp;
    String content;
    String mood;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Receive data from previous screen
        Intent intent = getIntent();

        // Open data from previous screen
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        mood = intent.getStringExtra("mood");
        id = intent.getIntExtra("id", -1);


        // Set listener on edit button
        FloatingActionButton button = findViewById(R.id.detail_editbutton);
        button.setOnClickListener(new DetailActivity.EditListener());

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

    // Finds the clicked mood and selects it
    public class EditListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            edit();
        }

    }

    public void edit() {

        // Go to the input screen and give the data
        Intent intent = new Intent(DetailActivity.this, InputActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("mood", mood);
        intent.putExtra("edit", "true");
        intent.putExtra("id", id);

        startActivity(intent);
    }
}
